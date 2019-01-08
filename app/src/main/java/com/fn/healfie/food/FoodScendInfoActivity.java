package com.fn.healfie.food;

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
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.adapter.FoodScendInfoAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.CreateFoodActivityBinding;
import com.fn.healfie.databinding.FoodScendInfoActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.model.FoodInfoBean;
import com.fn.healfie.model.FoodListBean;
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

public class FoodScendInfoActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    FoodScendInfoActivityBinding binding;
    ArrayList<CreateFoodBean> list;
    String id;
    String infoJson;
    PopupWindow popupWindow;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FoodInfoBean bean = JsonUtil.getBean(msg.obj.toString(), FoodInfoBean.class);
                    if (bean.getResultCode().equals("200")) {
                        infoJson = msg.obj.toString();
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
                case 3:
                    BaseBean beans = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    if (beans.getResultCode().equals("200")) {
                        ToastUtil.showToast(activity,"刪除成功");
                        popupWindow.dismiss();
                        finish();
                    } else if (beans.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if (registerBean.getResultCode().equals("200")) {
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(4);

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
                        ToastUtil.errorToast(activity, beans.getResultCode());
                    }
                    break;
                case 4:
                    deleteData();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.food_scend_info_activity);
        binding.setVariable(BR.click, this);
        id = getIntent().getStringExtra("foodId");
        initData();
        FoodScendInfoAdapter adapter = new FoodScendInfoAdapter(this, list, this, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
            }
        });

        binding.setAdapter(adapter);
        View inflate = LayoutInflater.from(this).inflate(R.layout.editor_pop_window, null);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        Button btn_quxiao = inflate.findViewById(R.id.btn_quxiao);
        Button btn_shanchu = inflate.findViewById(R.id.btn_shanchu);
        Button btn_bianji = inflate.findViewById(R.id.btn_bianji);
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        btn_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteData();
            }
        });
        btn_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CreateFoodActivity.class);
                intent.putExtra(CameraActivity.CAMERA_PATH_VALUE1, "");
                intent.putExtra("data", infoJson);
                intent.putExtra("from", "info");
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
    }

    private void deleteData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("id", id);
        connect.deleteData(MyUrl.FOOD + "/" + id, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 3;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                Log.e("error: ", json);
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void getFood() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("id", id);

        connect.getData(MyUrl.FOOD + "/" + id, map, new ConnectBack() {
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
                Log.e("error: ", json);
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new CreateFoodBean("image", "", "image"));
        list.add(new CreateFoodBean("食物名", "", "one_input"));
        list.add(new CreateFoodBean("進食日期 :", "", "one_select"));
        list.add(new CreateFoodBean("進食時間 :", "", "one_select"));
        list.add(new CreateFoodBean("查看權限", "一般聯繫人", "one_select"));
        list.add(new CreateFoodBean("營養成分", "  ", "one_select"));
        list.add(new CreateFoodBean("卡路里", "", "one_input"));
        list.add(new CreateFoodBean("脂肪總量", "", "one_input"));
        list.add(new CreateFoodBean("鈉", "", "one_input"));
        list.add(new CreateFoodBean("碳水化合物", "", "one_input"));
        list.add(new CreateFoodBean("", "", "white"));
        getFood();
    }

    private void changeList(FoodInfoBean bean) {
        FoodInfoBean.ItemBean item = bean.getItem();
        list.get(0).setValue(getUrl(item.getImageObject(), item.getBucket()));
        list.get(1).setValue(item.getCalorie() + "KJ");
        list.get(1).setKey(item.getName());
        list.get(2).setValue(item.getEatDate());
        list.get(3).setValue(item.getEatTime());
        list.get(6).setValue(item.getCalorie() + "KJ");
        list.get(7).setValue(item.getFat() + "KJ");
        list.get(8).setValue(item.getSodium() + "KJ");
        list.get(9).setValue(item.getCarbohydrate() + "KJ");
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
            case R.id.iv_setting:
                popupWindow.showAtLocation(binding.ivBack, 0, 0, 0);
                break;
        }
    }


}
