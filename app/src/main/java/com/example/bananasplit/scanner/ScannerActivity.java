package com.example.bananasplit.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.view.PreviewView;

import com.example.bananasplit.R;
import com.example.bananasplit.util.CameraProvider;
import com.example.bananasplit.util.ImageUtils;
import com.example.bananasplit.util.text.TextRecognizer;
import com.example.bananasplit.util.text.TextUtils;
import com.google.cloud.language.v1.Entity;
import com.google.mlkit.vision.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.bananasplit.util.text.TextAnalysisProvider;

/**
 * Activity for scanning receipts and extracting text from them.
 * The extracted text is then analyzed using the Google Cloud Natural Language API.
 *
 * @author Arpad Horvath
 */
public class ScannerActivity extends AppCompatActivity {
    private PreviewView previewView;
    private Handler mainHandler;
    private CameraProvider cameraProvider;
    private TextRecognizer textRecognizer;
    private TextAnalysisProvider textAnalysisProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        extractUIComponents();
        mainHandler = new Handler(Looper.getMainLooper());
        textAnalysisProvider = new TextAnalysisProvider(this);

        setupTextExtraction();
        setupCamera();


    }

    /**
     * Extracts the UI components from the layout.
     */
    private void extractUIComponents() {
        previewView = findViewById(R.id.previewView);
    }

    /**
     * Sets up the camera functionality and the capture button.
     */
    private void setupCamera() {
        cameraProvider = new CameraProvider(previewView, new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy image) {
                Toast.makeText(getApplicationContext(), getString(R.string.capture_successful), Toast.LENGTH_LONG).show();
                processImage(image);
                image.close();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(getApplicationContext(), getString(R.string.capture_failed) + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(view -> cameraProvider.takePhoto());
    }

    /**
     * Sets up the text extraction functionality.
     */
    private void setupTextExtraction() {
        textRecognizer = new TextRecognizer(new TextRecognizer.TextRecognizerCallback() {
            @Override
            public void onTextRecognized(Text result) {
                processTextBlock(result);
            }

            @Override
            public void onTextRecognitionFailed(Exception e) {
                Toast.makeText(ScannerActivity.this, getString(R.string.failed_to_recognize_text), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Processes the image using the text recognizer.
     *
     * @param image The image to process.
     */
    private void processImage(ImageProxy image) {
        Bitmap bitmap = ImageUtils.imageProxyToBitmap(image);
        textRecognizer.processImage(bitmap, image.getImageInfo().getRotationDegrees());
    }

    /**
     * Converts the text block to a string and delegates the text analysis to the Google Cloud Natural Language API.
     *
     * @param result The text block to process.
     */
    private void processTextBlock(Text result) {
        String resultText = result.getText();
        analyzeTextWithGoogleCloud(resultText);
    }

    /**
     * Analyzes the text using the Google Cloud Natural Language API.
     *
     * @param text The text to analyze.
     */
    private void analyzeTextWithGoogleCloud(String text) {
        String cleanedText = text.replaceAll("\\r\\n|\\r|\\n", " ").trim(); //apparently it performs better on single line text. yet to find out why
        textAnalysisProvider.analyzeText(cleanedText, new TextAnalysisProvider.TextAnalysisCallback() {
            @Override
            public void onAnalysisSuccess(List<Entity> entities) {
                List<ScanEntry> scanEntries = processEntities(entities);
                mainHandler.post(() -> launchCreateExpenseWithScanEntries(scanEntries));
            }

            @Override
            public void onAnalysisFailure(Exception e) {
                Log.e("ScannerActivity", "Failed to analyze text", e);
                mainHandler.post(() -> Toast.makeText(ScannerActivity.this,getString(R.string.failed_to_analyze_text) , Toast.LENGTH_SHORT).show());
            }
        });
    }


    /**
     * Processes the entities extracted from the text.
     * Filters out consumer goods and prices and matches them together.
     *
     * @param entities The entities to process.
     * @return The list of scan entries.
     */
    private List<ScanEntry> processEntities(List<Entity> entities) {

        List<Entity> consumerGoodsEntries = new ArrayList<>();
        LinkedList<Entity> numberEntries = new LinkedList<>();

        for (Entity entity : entities) {
            String name = entity.getName();
            if (entity.getType().toString().equals("CONSUMER_GOOD")) {
                consumerGoodsEntries.add(entity);
            } else if (entity.getType().toString().equals("NUMBER")) {
                // Process NUMBER entities and filter out integers (they are likely to be quantities, addresses, etc.) not perfect but a start ¯\_(ツ)_/¯
                if (TextUtils.isValidDecimalNumber(name)) {
                    numberEntries.add(entity);
                }
            }
        }

        return matchGoodsWithPrices(consumerGoodsEntries, numberEntries);
    }

    /**
     * Matches consumer goods with prices.
     * Currently ignores quantities and unit prices.
     * @param consumerGoodsEntries The list of consumer goods entities.
     * @param numberEntries        The list of number entities.
     * @return The list of scan entries.
     */
    private static List<ScanEntry> matchGoodsWithPrices(List<Entity> consumerGoodsEntries, LinkedList<Entity> numberEntries) {
        List<ScanEntry> entries = new ArrayList<>();
        for (Entity entity : consumerGoodsEntries) {

            String name = entity.getName();
            int quantity = 1;
            float unitPrice = 1.0f; // TODO: refine entity processing to allow quantity and unit price extraction
            double totalPrice = TextUtils.extractNumberValue(numberEntries.removeFirst());
            entries.add(new ScanEntry(name, quantity, unitPrice, totalPrice));
        }
        return entries;
    }

    /**
     * Forwards ScanEntries to the AddExpenseFromScannerActivity.
     * @param scanEntries
     */
    private void launchCreateExpenseWithScanEntries(List<ScanEntry> scanEntries) {
        Intent intent = new Intent(this, AddExpenseFromScannerActivity.class);
        intent.putParcelableArrayListExtra("scanEntries", new ArrayList<>(scanEntries));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textAnalysisProvider.shutdown();
    }
}
