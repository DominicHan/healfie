package com.fn.healfie.contact;

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
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.databinding.SearchFoodActivityBinding;
import com.fn.healfie.databinding.SearchNameActivityBinding;
import com.fn.healfie.food.CreateFoodActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.module.SearchModule;
import com.fn.healfie.utils.StatusBarUtil;

/**
 * from @zhaojian
 * content 創建食物
 */

public class SearchNameActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    SearchNameActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.search_name_activity);
        binding.setVariable(BR.click, this);
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
        }
    }


}
