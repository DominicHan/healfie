package com.fn.healfie.drugs.food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.utils.StatusBarUtil;


public class DrugsAlarmActivity extends BaseActivity {

    String path;
    String title;
    RoundImageView rv_image;
    TextView tv_alarm_drugs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.drugs_alarm_activity);
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        title = getIntent().getStringExtra("drugsTitle");
        initView();
    }

    private void initView() {
        rv_image = findViewById(R.id.rv_image);
        rv_image.changeRadius(5);
        Glide.with(this).load(path).into(rv_image);

        tv_alarm_drugs = findViewById(R.id.tv_alarm_drugs);
        tv_alarm_drugs.setText(title);

    }
}
