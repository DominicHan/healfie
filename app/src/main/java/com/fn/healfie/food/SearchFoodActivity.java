package com.fn.healfie.food;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.databinding.CreateFoodActivityBinding;
import com.fn.healfie.databinding.SearchFoodActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.module.SearchModule;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;

/**
 * from @zhaojian
 * content 創建食物
 */

public class SearchFoodActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    SearchFoodActivityBinding binding;
    SearchModule module;
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
        binding = DataBindingUtil.setContentView(this, R.layout.search_food_activity);
        binding.setVariable(BR.click, this);
        module = new SearchModule();
        module.setName("");
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        binding.setSearch(module);
        initData();
    }

    private void initData() {

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
                Intent intent = new Intent(activity, CreateFoodActivity.class);
                intent.putExtra(CameraActivity.CAMERA_PATH_VALUE1, path);
                startActivity(intent);
                break;
        }
    }


}
