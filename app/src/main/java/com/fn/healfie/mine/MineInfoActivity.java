package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.FoodScendInfoAdapter;
import com.fn.healfie.adapter.MineInfoAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.FoodScendInfoActivityBinding;
import com.fn.healfie.databinding.MineInfoActivityBinding;
import com.fn.healfie.food.CreateFoodActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.model.FoodInfoBean;
import com.fn.healfie.model.MineBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * from @zhaojian
 * content 創建食物
 */

public class MineInfoActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    MineInfoActivityBinding binding;
    ArrayList<CreateFoodBean> list;
    String id;
    PopupWindow popupWindow;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MineBean bean = JsonUtil.getBean(msg.obj.toString(), MineBean.class);
                    if (bean.getResultCode().equals("200")) {
                        changeList(bean);
                    } else if (bean.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if (registerBean.getResultCode().equals("200")) {
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(2);

                                } else {
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
                    getFood();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.mine_info_activity);
        binding.setVariable(BR.click, this);
        id = getIntent().getStringExtra("foodId");
        initData();
        MineInfoAdapter adapter = new MineInfoAdapter(this, list, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
            }
        });

        binding.setAdapter(adapter);
        View inflate = LayoutInflater.from(this).inflate(R.layout.editor_pop_window, null);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
    }


    private void getFood() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.MEMBERINFO, map, new ConnectBack() {
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
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new CreateFoodBean("頭像", "", "image"));
        list.add(new CreateFoodBean("姓名", "", "one_input"));
        list.add(new CreateFoodBean("性別", "設置性別", "one_select"));
        list.add(new CreateFoodBean("會員名", "設置會員名", "one_select"));
        list.add(new CreateFoodBean("我的二維碼", "一般聯繫人", "one_select"));
        getFood();
    }

    private void changeList(MineBean bean) {
        MineBean.ItemBean item = bean.getItem();
        list.get(0).setValue(getUrl(item.getImageObject(), item.getBucket()));
        list.get(1).setValue(item.getName());
        if(item.getSex()!=null){
            list.get(2).setValue(item.getSex().toString());
        }
        if(item.getUserName()!=null){
            list.get(3).setValue(item.getUserName().toString());
        }
    }

    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(getApplicationContext(), PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(getApplicationContext(), PrefeKey.TOKEN, "");
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
