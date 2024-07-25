package com.example.bananasplit.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.camera.core.ImageProxy;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Random;
/**
 * Utility class for image-related operations.
 * Provides methods to generate profile images in the Style of Googles Circle and initial.
 * currently regenerates them on every call with no consistent colors
 * Retrieves drawable resources from URIs.
 * @author Arpad Horvath
 */
public class ImageUtils {

    public static void setProfileImage(ImageView imageView, String name) {
        final int size = 100;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(generateRandomColor());

        // Draw the circular background
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, circlePaint);

        // Draw the initials
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        String initial = getInitial(name);
        float xPos = size / 2f;
        float yPos = (size / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f); //chatgpt prompt: "how to center a letter in a circle when painting in android"
        canvas.drawText(initial, xPos, yPos, textPaint);

        imageView.setImageDrawable(new BitmapDrawable(imageView.getResources(), bitmap));
    }
    /**
     * Retrieves a Drawable from the given URI.
     * <a href="https://stackoverflow.com/questions/74832184/how-to-transform-uri-to-integer-for-drawable">...</a>
     * @param activity The activity context used to access the content resolver.
     * @param uri      The URI of the drawable resource.
     * @return A Drawable object corresponding to the URI, or null if the URI is not found.
     */

    public static Drawable getDrawableFromUri(Activity activity, Uri uri) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Generates a random color.
     *
     * @return The generated random color.
     */
    private static int generateRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    /**
     * Retrieves the initial character from the provided name.
     * Defaults to "A" if the name is null or empty.
     *
     * @param name The name from which to derive the initial.
     * @return The initial character of the name, or "A" if the name is null or empty.
     */
    private static String getInitial(String name) {
        return (name != null && !name.isEmpty()) ? String.valueOf(name.charAt(0)).toUpperCase() : "A";
    }

    /**
     * Converts an ImageProxy object from CameraX to a Bitmap.
     * chatgpt prompt: "Converts an ImageProxy object from CameraX to a Bitmap."
     * @param image The ImageProxy object to convert.
     * @return The converted Bitmap object.
     */
    public static Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        buffer.rewind();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        // Convert bytes to bitmap (NV21 format)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
