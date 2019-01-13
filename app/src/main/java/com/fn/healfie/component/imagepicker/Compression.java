package com.fn.healfie.component.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;

public class Compression {

    File compressImage(final Activity activity, final JSONObject options, final String originalImagePath, final BitmapFactory.Options bitmapOptions) throws Exception {
        Integer maxWidth = options.has("compressImageMaxWidth")? options.getInt("compressImageMaxWidth") : null;
        Integer maxHeight = options.has("compressImageMaxHeight") ? options.getInt("compressImageMaxHeight") : null;
        Double quality = options.has("compressImageQuality") ? options.getDouble("compressImageQuality") : null;

        Boolean isLossLess = (quality == null || quality == 1.0);
        Boolean useOriginalWidth = (maxWidth == null || maxWidth >= bitmapOptions.outWidth);
        Boolean useOriginalHeight = (maxHeight == null || maxHeight >= bitmapOptions.outHeight);

        List knownMimes = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/gif", "image/tiff");
        Boolean isKnownMimeType = (bitmapOptions.outMimeType != null && knownMimes.contains(bitmapOptions.outMimeType.toLowerCase()));

        if (isLossLess && useOriginalWidth && useOriginalHeight && isKnownMimeType) {
            Log.d("image-crop-picker", "Skipping image compression");
            return new File(originalImagePath);
        }

        Log.d("image-crop-picker", "Image compression activated");
        Compressor compressor = new Compressor(activity)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath());

        if (quality == null) {
            Log.d("image-crop-picker", "Compressing image with quality 100");
            compressor.setQuality(100);
        } else {
            Log.d("image-crop-picker", "Compressing image with quality " + (quality * 100));
            compressor.setQuality((int) (quality * 100));
        }

        if (maxWidth != null) {
            Log.d("image-crop-picker", "Compressing image with max width " + maxWidth);
            compressor.setMaxWidth(maxWidth);
        }

        if (maxHeight != null) {
            Log.d("image-crop-picker", "Compressing image with max height " + maxHeight);
            compressor.setMaxHeight(maxHeight);
        }

        File image = new File(originalImagePath);

        String[] paths = image.getName().split("\\.(?=[^\\.]+$)");
        String compressedFileName = paths[0] + "-compressed";
        
        if(paths.length > 1)
            compressedFileName += "." + paths[1];
        
        return compressor
                .compressToFile(image, compressedFileName);
    }

    synchronized void compressVideo(final Activity activity, final JSONObject options, final String originalVideo, final String compressedVideo) {
        // todo: video compression
        // failed attempt 1: ffmpeg => slow and licensing issues
    }

    public static File compressImage(Context context, int maxHeight, int maxWidth, String originalImagePath) throws IOException {
        Compressor compressor = new Compressor(context)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath());
        File image = new File(originalImagePath);

        return  compressor
                .compressToFile(image);
    }

}
