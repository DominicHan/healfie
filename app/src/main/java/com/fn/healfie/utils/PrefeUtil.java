package com.fn.healfie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefeUtil {
    private static SharedPreferences sharedPreferences;


    /**
     * 存储字符串
     *
     * @param context 上下文
     * @param key     标识
     * @param value   内容
     */
    public static void saveString(Context context, String key, String value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }


    /**
     * 获取字符串
     *
     * @param context  上下文
     * @param key      标识
     * @param defValue 默认值
     * @return 内容
     */
    public static String getString(Context context, String key, String defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * 存储bool值
     *
     * @param context 上下文
     * @param key     标识
     * @param value   内容
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取bool值
     *
     * @param context  上下文
     * @param key      标识
     * @param defValue 默认值
     * @return 内容
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 存储数字
     *
     * @param context 上下文
     * @param key     标识
     * @param value   内容
     */
    public static void saveInt(Context context, String key, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 获取数字
     *
     * @param context  上下文
     * @param key      标识
     * @param defValue 默认值
     * @return 内容
     */
    public static int getInt(Context context, String key,
                             int defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("config",
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, defValue);
    }
}
