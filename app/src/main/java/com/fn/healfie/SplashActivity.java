package com.fn.healfie;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.ActivityMainBinding;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * from @zhaojian
 * content 主activity 控制跳轉
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.StatusBarLightMode(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

//        if(!PermissionsUtil.hasPermission(this, Manifest.permission.CAMERA)){
//            PermissionsUtil.requestPermission(this, new PermissionListener() {
//                @Override
//                public void permissionGranted(@NonNull String[] permissions) {
//                    //用户授予了访问摄像头的权限
//                }
//
//
//                @Override
//                public void permissionDenied(@NonNull String[] permissions) {
//                    System.exit(0);
//                }
//            }, new String[]{Manifest.permission.CAMERA});
//        }else{
//            if(!PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                PermissionsUtil.requestPermission(this, new PermissionListener() {
//                    @Override
//                    public void permissionGranted(@NonNull String[] permissions) {
//                        //用户授予了访问摄像头的权限
//                    }
//
//
//                    @Override
//                    public void permissionDenied(@NonNull String[] permissions) {
//                        System.exit(0);
//                    }
//                }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
//            }else{
//
//            }
//        }
        getPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE);
    }

    public void getPermission(String name,String write,String phone){
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            String token = PrefeUtil.getString(SplashActivity.this, PrefeKey.TOKEN, "");
                            if (token.equals("")) {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000);//0.5秒后执行光标后移操作
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    System.exit(0);
                }
            }, new String[]{name,write,phone});
    }

}

