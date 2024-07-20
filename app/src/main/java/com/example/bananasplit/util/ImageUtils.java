package com.example.bananasplit.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

public class ImageUtils {

    public static void setProfileImage(ImageView imageView, String name) {
        int size = 100; // size of the image
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        // Generate random color
        Random random = new Random();
        paint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

        // Draw initials
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        String initial = name != null && !name.isEmpty() ? String.valueOf(name.charAt(0)).toUpperCase() : "A";
        float xPos = size / 2f;
        float yPos = (size / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2f);
        canvas.drawText(initial, xPos, yPos, textPaint);

        imageView.setImageDrawable(new BitmapDrawable(imageView.getResources(), bitmap));
    }

    public static Drawable getDrawableFromUri(Activity activity, Uri uri) {
        try {
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            return Drawable.createFromStream(inputStream, uri.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
