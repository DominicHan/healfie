package com.fn.healfie.food;

import android.app.Activity;
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
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * from @zhaojian
 * content 創建食物
 */

public class CreateFoodActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    CreateFoodActivityBinding binding;
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
        binding = DataBindingUtil.setContentView(this, R.layout.create_food_activity);
        binding.setVariable(BR.click, this);
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        CreateFoodAdapter adapter = new CreateFoodAdapter(this, initData(), this);
        binding.setAdapter(adapter);
    }

    private List<CreateFoodBean> initData() {
        ArrayList<CreateFoodBean> list = new ArrayList<>();
        list.add(new CreateFoodBean("image", path, "image"));
        list.add(new CreateFoodBean("食物名", "", "one_input"));
        list.add(new CreateFoodBean("預估熱量 :", "", "one_input"));
        list.add(new CreateFoodBean("進食日期 :", "", "one_select"));
        list.add(new CreateFoodBean("進食時間 :", "", "one_select"));
        list.add(new CreateFoodBean("查看權限", "一般聯繫人", "one_select"));
        list.add(new CreateFoodBean("營養成分（每份）", "", "one_select"));
        list.add(new CreateFoodBean("卡路里", "", "one_input"));
        list.add(new CreateFoodBean("脂肪總量", "", "one_input"));
        list.add(new CreateFoodBean("鈉", "", "one_input"));
        list.add(new CreateFoodBean("碳水化合物", "", "one_input"));
        list.add(new CreateFoodBean("確定", "", "button"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
        return list;
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