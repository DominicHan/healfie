package com.fn.healfie.contact;

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
import com.fn.healfie.databinding.AddContactActivityBinding;
import com.fn.healfie.databinding.UnidentifiedFoodActivityBinding;
import com.fn.healfie.food.CreateFoodActivity;
import com.fn.healfie.food.SearchFoodActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.utils.StatusBarUtil;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 食物圖片未識別
 */

public class AddContactActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    AddContactActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.add_contact_activity);
        binding.setVariable(BR.click, this);
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
            case R.id.rl_add_name:
                Intent intents = new Intent(AddContactActivity.this, SearchNameActivity.class);
                startActivity(intents);
                break;
        }
    }


}
