package com.fn.healfie.drugs.food;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.CreateDrugsAdapter;
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.CreateDrugsActivityBinding;
import com.fn.healfie.databinding.CreateFoodActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.model.DrugsInfoBean;
import com.fn.healfie.model.FoodBackBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * from @zhaojian
 * content 創建食物
 */

public class CreateDrugsActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    ArrayList<CreateFoodBean> list;
    String path;
    String from="";
    CreateDrugsActivityBinding binding;
    PopupWindow popupWindow;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    DrugsInfoBean bean = JsonUtil.getBean(msg.obj.toString(), DrugsInfoBean.class);
                    if (bean.getResultCode().equals("200")) {
                        if (from.equals("info")) {
                            ToastUtil.showToast(activity, "編輯成功");
                        } else {
                            ToastUtil.showToast(activity, "添加成功");
                        }
                        finish();
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
                    if (from.equals("info")) {
//                        changeData();
                    } else {
                        sendData();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public String getShowLimit(String name){
        if(name.equals("星標聯繫人")){
            return "1";
        }else{
            return "2";
        }
    }

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
                if(id==1){
                    for (int i = 0;i<list.size();i++){
                        if(list.get(i).getValue().equals("")){
                            ToastUtil.showToast(activity,"請輸入"+list.get(i).getKey().split(":"));
                            return;
                        }

                    }
                    sendData();
//                ToastUtil.showToast(activity,"添加成功");
//                finish();
                }else{
                    popupWindow.showAtLocation(binding.ivBack, 0, 0, 0);
                }
            }
        });
        binding.setAdapter(adapter);
        View inflate = LayoutInflater.from(this).inflate(R.layout.select_pop_window, null);
        popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        Button btn_yblxr = inflate.findViewById(R.id.btn_yblxr);
        Button btn_xblxr = inflate.findViewById(R.id.btn_xblxr);
        btn_yblxr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(7).setValue("一般聯繫人");
                popupWindow.dismiss();
            }
        });
        btn_xblxr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(7).setValue("星標聯繫人");
                popupWindow.dismiss();
            }
        });
    }

    private void sendData() {
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("isOtherAdd", "0");
        map.put("takeUnit", "粒");
        map.put("takeMode", "1");
        map.put("src", "2");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue().equals("")) {
                ToastUtil.showToast(activity, "請輸入" + list.get(i).getKey());
                return;
            } else {
                switch (list.get(i).getKey()) {
                    case "image":
                        Bitmap bm = BitmapFactory.decodeFile(path);
                        Bitmap mSrcBitmap = Bitmap.createScaledBitmap(bm, 720, 1280, true);
                        bm.recycle();
                        bm = null;
                        map.put("image", bitmapToBase64(mSrcBitmap));
                        break;
                    case "中文名 :":
                        map.put("cnName", list.get(i).getValue());
                        break;
                    case "英文名 :":
                        map.put("enName", list.get(i).getValue());
                        break;
                    case "服藥方式 :":
                        map.put("eatDate", list.get(i).getValue());
                        break;
                    case "服藥劑量 :":
                        map.put("takeDose", list.get(i).getValue());
                        break;
                    case "服藥日期 :":
                        map.put("eatDate", list.get(i).getValue());
                        break;
                    case "服藥時間 :":
                        map.put("eatTime", list.get(i).getValue()+":00");
                        break;
                    case "查看權限":
                        map.put("showLimit", getShowLimit(list.get(i).getValue()));
                        break;
                }
            }
        }
        showDialog();
        connect.postData(MyUrl.DRUGS, map, new ConnectBack() {
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

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

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
                finish();
                break;
        }
    }


}
