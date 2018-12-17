package com.fn.healfie;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.ActivityMainBinding;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;

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

}

