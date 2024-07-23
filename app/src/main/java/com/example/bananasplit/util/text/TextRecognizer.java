package com.example.bananasplit.util.text;

import android.graphics.Bitmap;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

/**
 * Utility class for text recognition operations.
 * Provides methods to process images and recognize text.
 * @author Arpad Horvath
 */
public class TextRecognizer {

    private final TextRecognizerCallback callback;

    /**
     * Callback interface for text recognition operations.
     */
    public interface TextRecognizerCallback {
        void onTextRecognized(Text result);
        void onTextRecognitionFailed(Exception e);
    }

    /**
     * Creates a new TextRecognizer instance with the given callback.
     * @param callback The callback to be invoked when text recognition is complete.
     */
    public TextRecognizer(TextRecognizerCallback callback) {
        this.callback = callback;
    }

    /**
     * Processes the given image and recognizes text.
     * @param bitmap The image to be processed.
     * @param rotationDegrees The rotation of the image in degrees.
     */
    public void processImage(Bitmap bitmap, int rotationDegrees) {
        InputImage inputImage = InputImage.fromBitmap(bitmap, rotationDegrees);

        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                .process(inputImage)
                .addOnSuccessListener(callback::onTextRecognized)
                .addOnFailureListener(callback::onTextRecognitionFailed);
    }
}
