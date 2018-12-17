package com.fn.healfie.main;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.fn.healfie.R;
import com.fn.healfie.databinding.MainActivityBinding;
import com.fn.healfie.utils.StatusBarUtil;

/**
 * from @zhaojian
 * content 主activity 控制跳轉
 */
public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        getSupportActionBar().hide();
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        /**
         * from @zhaojian
         * content  創建底部導航欄
         */
        binding.bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(HomeFragment.class,
                        "首頁",
                        R.mipmap.ic_home_normal,
                        R.mipmap.ic_home_selected)
                .addItem(ContactFragment.class,
                        "聯繫人",
                        R.mipmap.ic_contact_normal,
                        R.mipmap.ic_contact_selected)
                .addItem(MineFragment.class,
                        "我的",
                        R.mipmap.ic_me_normal,
                        R.mipmap.ic_me_selected)
                .build();
        binding.bottomBar.setTitleBeforeAndAfterColor("#d4d8dc", "#0ba7c5");
    }

}

