package com.example.bananasplit.scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        previewView = findViewById(R.id.previewView);

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
//        List<String> lines = new ArrayList<>();
//        for (Text.TextBlock block : result.getTextBlocks()) {
//            for (Text.Line line : block.getLines()) {
//                lines.add(line.getText());
//            }
//        }

        parseReceipt(resultText);
    }
    private static List<ScanEntry> parseReceipt(String text) {
    List<ScanEntry> entries = new ArrayList<>();

    // Pattern to match prices and quantities
    Pattern priceQuantityPattern = Pattern.compile("^(\\d+\\.\\d{2})$|^(\\d+)$", Pattern.MULTILINE);
    Matcher pqMatcher = priceQuantityPattern.matcher(text);

    // List to store the matched prices and quantities
    List<String> pqMatches = new ArrayList<>();

        while(pqMatcher.find())

    {
        if (pqMatcher.group(1) != null) {
            pqMatches.add(pqMatcher.group(1)); // price
        } else if (pqMatcher.group(2) != null) {
            pqMatches.add(pqMatcher.group(2)); // quantity
        }
    }

    // Pattern to match item names
    Pattern itemNamePattern = Pattern.compile("^[A-Za-z ]+$", Pattern.MULTILINE);
    Matcher itemNameMatcher = itemNamePattern.matcher(text);

    // List to store the matched item names
    List<String> itemNames = new ArrayList<>();

        while(itemNameMatcher.find())

    {
        itemNames.add(itemNameMatcher.group().trim());
    }

    // Combine the extracted information into ScanEntry objects
        for(
    int i = 0; i<itemNames.size();i++)

    {
        String name = itemNames.get(i);
        int quantityIndex = i * 3 + 1;
        int unitPriceIndex = i * 3 + 2;
        int totalPriceIndex = i * 3;

        if (quantityIndex < pqMatches.size() && unitPriceIndex < pqMatches.size() && totalPriceIndex < pqMatches.size()) {
            int quantity = Integer.parseInt(pqMatches.get(quantityIndex));
            double unitPrice = Double.parseDouble(pqMatches.get(unitPriceIndex));
            double totalPrice = Double.parseDouble(pqMatches.get(totalPriceIndex));
            entries.add(new ScanEntry(name, quantity, unitPrice, totalPrice));
        }
    }

        return entries;

}

//    private List<ScanEntry> parseWithStanfordNLP(String text) {
//        // Setup Stanford NLP pipeline
//        Properties props = PropertiesUtils.asProperties(
//                "annotators", "tokenize,ssplit,pos,lemma,ner",
//                "ner.useSUTime", "false"
//        );
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//
//        // Annotate the text
//        Annotation document = new Annotation(text);
//        pipeline.annotate(document);
//
//        List<ScanEntry> entries = new ArrayList<>();
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences) {
//            List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
//            String itemName = "";
//            int quantity = 0;
//            double amount = 0.0;
//
//            for (CoreLabel token : tokens) {
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//
//                if (ner.equals("QUANTITY")) {
//                    quantity = Integer.parseInt(word);
//                } else if (ner.equals("MONEY")) {
//                    amount = Double.parseDouble(word.replaceAll("[^\\d.]", ""));
//                } else {
//                    itemName += word + " ";
//                }
//            }
//
//            itemName = itemName.trim();
//            if (!itemName.isEmpty() && quantity > 0 && amount > 0) {
//                entries.add(new ScanEntry(itemName, quantity, amount));
//            }
//        }
//
//        return entries;
//    }

@Override
protected void onDestroy() {
    super.onDestroy();
    cameraExecutor.shutdown();
}
}
