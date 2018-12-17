package com.fn.healfie.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/11/1.
 */

public class ToastUtil {
    public static void showToast(final Activity activity, final String message) {
        if ("main".equals(Thread.currentThread().getName())) {
            Log.e("ToastUtil", "在主线程");
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("ToastUtil", "不在主线程");
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public static void errorToast(final Activity activity, final String code) {
        final String message;
        if(code.equals("40015")){
            message = "該會員已設定過用戶名，不能再次設定";
        }else if(code.equals("40014")){
            message = "未找到服藥記錄資訊";
        }else if(code.equals("40013")){
            message = "會員資訊不完整，請補充";
        }else if(code.equals("40012")){
            message = "未找食物記錄資訊";
        }else if(code.equals("40011")){
            message = "未找到會員資訊";
        }else if(code.equals("40009")){
            message = "請先獲取驗證碼！";
        }else if(code.equals("40010")){
            message = "該手機號已注册，請使用密碼登入";
        }else if(code.equals("40008")){
            message = "驗證碼有誤，請重試！";
        }else if(code.equals("40007")){
            message = "驗證碼已失效，請重新獲取！";
        }else if(code.equals("40006")){
            message = "驗證碼獲取頻率過快，請稍後重試";
        }else if(code.equals("40005")){
            message = "頭像上傳失敗";
        }else if(code.equals("40004")){
            message = "facebook id不存在";
        }else if(code.equals("40003")){
            message = "用戶名或密碼錯誤";
        }else if(code.equals("40016")){
            message = "該用戶名已存在，請更換";
        }else{
            message = "系統出錯啦，請聯繫管理員";
        }
        if ("main".equals(Thread.currentThread().getName())) {
            Log.e("ToastUtil", "在主线程");
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
