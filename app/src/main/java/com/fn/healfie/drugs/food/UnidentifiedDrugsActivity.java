package com.fn.healfie.drugs.food;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.databinding.UnidentifiedDrugsActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.utils.StatusBarUtil;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 食物圖片未識別
 */

public class UnidentifiedDrugsActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    UnidentifiedDrugsActivityBinding binding;
    String path;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.unidentified_drugs_activity);
        binding.setVariable(BR.click, this);
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        Log.e(TAG, "onCreate: " + path);
        Log.e(TAG, "onCreate: " + Environment.DIRECTORY_PICTURES);
        binding.rvImage.changeRadius(5);
        File file = new File(path);
        Glide.with(this).load(file).into(binding.rvImage);
    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_zjsw:
                Intent intents = new Intent(UnidentifiedDrugsActivity.this, CreateDrugsActivity.class);
                intents.putExtra(CameraActivity.CAMERA_PATH_VALUE1, path);
                startActivity(intents);
                break;
            case R.id.btn_ssgd:
                Intent intent = new Intent(UnidentifiedDrugsActivity.this, SearchDrugsActivity.class);
                intent.putExtra(CameraActivity.CAMERA_PATH_VALUE1, path);
                startActivity(intent);
                break;
        }
    }


}
