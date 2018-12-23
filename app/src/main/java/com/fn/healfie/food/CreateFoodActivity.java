package com.fn.healfie.food;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.CreateFoodActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.login.SaveNameActivity;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.model.FoodListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * from @zhaojian
 * content 創建食物
 */

public class CreateFoodActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    CreateFoodActivityBinding binding;
    ArrayList<CreateFoodBean> list;
    String path;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FoodListBean bean = JsonUtil.getBean(msg.obj.toString(), FoodListBean.class);
                    if (bean.getResultCode().equals("200")) {
                        ToastUtil.showToast(activity,"添加成功");
                        finish();
                    }else if (bean.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if(registerBean.getResultCode().equals("200")){
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(2);

                                }else{
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    sendData();
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
        initData();
        CreateFoodAdapter adapter = new CreateFoodAdapter(this, list, this, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
                sendData();
            }
        });
        binding.setAdapter(adapter);
    }

    private void sendData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("showLimit", "1");
        map.put("isOtherAdd", "0");
        map.put("src", "2");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue().equals("")) {
                ToastUtil.showToast(activity, "請輸入" + list.get(i).getKey());
                return;
            } else {
                switch (list.get(i).getKey()) {
                    case "image":
                        map.put("image", list.get(i).getValue());
                        break;
                    case "食物名":
                        map.put("name", list.get(i).getValue());
                        break;
                    case "預估熱量 :":
                        break;
                    case "進食日期 :":
                        map.put("eatDate", list.get(i).getValue());
                        break;
                    case "卡路里":
                        map.put("calorie", list.get(i).getValue());
                        break;
                    case "進食時間 :":
                        map.put("eatTime", list.get(i).getValue()+":00");
                        break;
                    case "脂肪總量":
                        map.put("fat", list.get(i).getValue());
                        break;
                    case "鈉":
                        map.put("sodium", list.get(i).getValue());
                        break;
                    case "碳水化合物":
                        map.put("carbohydrate", list.get(i).getValue());
                        break;
                }
            }
        }

        connect.postData(MyUrl.FOOD, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                Log.e( "error: ",json );
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new CreateFoodBean("image", path, "image"));
        list.add(new CreateFoodBean("食物名", "", "one_input"));
        list.add(new CreateFoodBean("預估熱量 :", "", "one_input"));
        list.add(new CreateFoodBean("進食日期 :", "", "one_select"));
        list.add(new CreateFoodBean("進食時間 :", "", "one_select"));
        list.add(new CreateFoodBean("查看權限", "一般聯繫人", "one_select"));
        list.add(new CreateFoodBean("營養成分（每份）", "  ", "one_select"));
        list.add(new CreateFoodBean("卡路里", "", "one_input"));
        list.add(new CreateFoodBean("脂肪總量", "", "one_input"));
        list.add(new CreateFoodBean("鈉", "", "one_input"));
        list.add(new CreateFoodBean("碳水化合物", "", "one_input"));
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
                finish();
                break;
        }
    }


}
