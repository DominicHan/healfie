package com.fn.healfie.drugs.food;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.CreateDrugsAdapter;
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.databinding.CreateDrugsActivityBinding;
import com.fn.healfie.databinding.CreateFoodActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;

/**
 * from @zhaojian
 * content 創建食物
 */

public class CreateDrugsActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    ArrayList<CreateFoodBean> list;
    String path;
    CreateDrugsActivityBinding binding;
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
        binding= DataBindingUtil.setContentView(this, R.layout.create_drugs_activity);
        binding.setVariable(BR.click, this);
        path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        initData();
        CreateDrugsAdapter adapter = new CreateDrugsAdapter(this, list, this, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
                for (int i = 0;i<list.size();i++){
                    if(list.get(i).getValue().equals("")){
                        ToastUtil.showToast(activity,"請輸入"+list.get(i).getKey());
                        return;
                    }

                }
                ToastUtil.showToast(activity,"添加成功");
                finish();
            }
        });
        binding.setAdapter(adapter);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new CreateFoodBean("image", path, "image"));
        list.add(new CreateFoodBean("中文名 :", "", "one_input"));
        list.add(new CreateFoodBean("英文名 :", "", "one_input"));
        list.add(new CreateFoodBean("服藥方式 :", "", "one_input"));
        list.add(new CreateFoodBean("服藥劑量 :", "", "one_input"));
        list.add(new CreateFoodBean("服藥日期 :", "", "one_select"));
        list.add(new CreateFoodBean("服藥時間 :", "", "one_select"));
        list.add(new CreateFoodBean("查看權限", "一般聯繫人", "one_select"));
        list.add(new CreateFoodBean("確定", "  ", "button"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_back:

//                finish();
                break;
        }
    }


}
