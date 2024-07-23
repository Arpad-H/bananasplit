package com.example.bananasplit.util;

import android.util.Log;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;


import java.util.concurrent.ExecutionException;

/**
 * Utility class for camera-related operations.
 * Provides methods to start the camera and take a photo.
 * @author Arpad Horvath
 */
public class CameraProvider {

    private final PreviewView previewView;
    private final ImageCapture.OnImageCapturedCallback captureCallback;
    private ImageCapture imageCapture;

    /**
     * Creates a new CameraProvider instance.
     * @param previewView The PreviewView used to display the camera feed.
     * @param captureCallback The callback to be executed when a photo is taken.
     */
    public CameraProvider(PreviewView previewView, ImageCapture.OnImageCapturedCallback captureCallback) {
        this.previewView = previewView;
        this.captureCallback = captureCallback;
        startCamera();
    }

    /**
     * Starts the camera and binds it to the given PreviewView.
     */
    private void startCamera() {
        ProcessCameraProvider.getInstance(previewView.getContext())
                .addListener(() -> {
                    try {
                        ProcessCameraProvider cameraProvider = ProcessCameraProvider.getInstance(previewView.getContext()).get();
                        Preview preview = new Preview.Builder().build();
                        preview.setSurfaceProvider(previewView.getSurfaceProvider());

                        imageCapture = new ImageCapture.Builder().build();
                        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                        cameraProvider.unbindAll();
                        cameraProvider.bindToLifecycle((LifecycleOwner) previewView.getContext(), cameraSelector, preview, imageCapture);

                    } catch (ExecutionException | InterruptedException e) {
                        Log.e("CameraManager", "Failed to start camera", e);
                    }
                }, ContextCompat.getMainExecutor(previewView.getContext()));
    }

    /**
     * Takes a photo using the camera.
     */
    public void takePhoto() {
        imageCapture.takePicture(ContextCompat.getMainExecutor(previewView.getContext()), captureCallback);
    }
}
