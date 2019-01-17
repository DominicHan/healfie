package com.fn.healfie.utils;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;

public class FileUtil {

    public static String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer,Base64.DEFAULT);
    }

}
