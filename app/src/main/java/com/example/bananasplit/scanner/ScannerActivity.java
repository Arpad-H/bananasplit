package com.example.bananasplit.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.example.bananasplit.R;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScannerActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        executorService = Executors.newSingleThreadExecutor();
        previewView = findViewById(R.id.previewView);
        mainHandler = new Handler(Looper.getMainLooper());
        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(view -> takePhoto());
        cameraExecutor = Executors.newSingleThreadExecutor();
        startCamera();


    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(previewView.getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (ExecutionException | InterruptedException e) {
                //TODO Handle errors
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                processImage(image);
                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                // Handle any errors during the image capture
                Toast.makeText(getApplicationContext(), "Capture failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processImage(ImageProxy image) {
        Bitmap bitmap = imageProxyToBitmap(image);
        InputImage inputImage = InputImage.fromBitmap(bitmap, image.getImageInfo().getRotationDegrees());

        com.google.mlkit.vision.text.TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(inputImage)
                .addOnSuccessListener(this::processTextBlock)
                .addOnFailureListener(e -> {
                    // Task failed with an exception
                    Log.e("MLKit", "Text recognition failed", e);
                });


        // Build the image request
//        Image img = Image.newBuilder().setContent(imgBytes).build();
//        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
//        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
//                .addFeatures(feat)
//                .setImage(img)
//                .build();
//
//        // Perform OCR
//        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
//            TextAnnotation response = client.annotateImage(request).getFullTextAnnotation();
//            System.out.println("Text: " + response.getText());
//        }
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        buffer.rewind();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        // Convert bytes to bitmap (NV21 format)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void processTextBlock(Text result) {
        String resultText = result.getText();
        analyzeTextWithGoogleCloud(resultText);
    }

    private void analyzeTextWithGoogleCloud(String text) {
        String cleanedText = text.replaceAll("\\r\\n|\\r|\\n", " ").trim();
        executorService.execute(() -> {
            InputStream credentialsStream = null;
            try {
                // Load the service account key JSON file from the assets directory
                credentialsStream = getAssets().open("civil-oarlock-430219-t8-1dd96ae6ae58.json");

                // Set up the credentials
                GoogleCredentials credentials = ServiceAccountCredentials.fromStream(credentialsStream);
                LanguageServiceSettings settings = LanguageServiceSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
                try (LanguageServiceClient languageServiceClient = LanguageServiceClient.create(settings)) {
                    Document doc = Document.newBuilder().setContent(cleanedText).setType(Document.Type.PLAIN_TEXT).setLanguage("de").build();
                    AnalyzeEntitiesResponse response = languageServiceClient.analyzeEntities(doc);

                    List<Entity> entities = response.getEntitiesList();
                    List<ScanEntry> scanEntries = processEntities(entities);
                    // Process entities on the main thread
                    mainHandler.post(() -> updateUIWithScanResults(scanEntries));
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error on the main thread
                mainHandler.post(() -> {
                    Toast.makeText(ScannerActivity.this, "Failed to analyze text", Toast.LENGTH_SHORT).show();
                });
            } finally {
                if (credentialsStream != null) {
                    try {
                        credentialsStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Utility method to check if a number is a valid decimal number
    private boolean isValidDecimalNumber(String text) {
        // Check if the text is a positive decimal number (not an integer)
        return text.matches("\\d+([.,]\\d+)?") && !text.matches("\\d+");
    }


    // Utility method to extract numerical value from a NUMBER entity
    private double extractNumberValue(Entity entity) {
        String text = entity.getName().replace(',', '.'); // Replace comma with dot for parsing
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private List<ScanEntry> processEntities(List<Entity> entities) {
        List<ScanEntry> entries = new ArrayList<>();

        List<Entity> consumerGoodsEntries = new ArrayList<>();
        LinkedList<Entity> numberEntries = new LinkedList<>();

        for (Entity entity : entities) {
            String name = entity.getName();
            if (entity.getType().toString().equals("CONSUMER_GOOD")) {
                // Process CONSUMER_GOOD entities
                consumerGoodsEntries.add(entity);
            } else if (entity.getType().toString().equals("NUMBER")) {
                // Process NUMBER entities and filter out integers
                if (isValidDecimalNumber(name)) {
                    numberEntries.add(entity);
                }
            }
        }

        for (Entity entity : consumerGoodsEntries) {
            String name = entity.getName();
            // Logic to extract quantities and prices from the entity or related mentions
            int quantity = 1; // Placeholder
            float unitPrice = 1.0f; // Placeholder TODO: refine entity processing
            double totalPrice = extractNumberValue(numberEntries.removeFirst());
            entries.add(new ScanEntry(name, quantity, unitPrice, totalPrice));
        }

        return entries;
    }


    private void updateUIWithScanResults(List<ScanEntry> scanEntries) {
        // Example: Update UI elements or RecyclerViews with the processed results
        // For instance:
        // consumerGoodsRecyclerView.setAdapter(new ScanEntryAdapter(consumerGoods));
        // numbersRecyclerView.setAdapter(new ScanEntryAdapter(numbers));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
