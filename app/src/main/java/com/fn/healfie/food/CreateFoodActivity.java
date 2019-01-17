package com.fn.healfie.food;

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
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.model.FoodBackBean;
import com.fn.healfie.model.FoodInfoBean;
import com.fn.healfie.model.FoodListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    String from ="";
    PopupWindow popupWindow;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FoodBackBean bean = JsonUtil.getBean(msg.obj.toString(), FoodBackBean.class);
                    if (bean.getResultCode().equals("200")) {
                        if(from.equals("info")){
                            ToastUtil.showToast(activity,"編輯成功");
                        }else{
                            ToastUtil.showToast(activity,"添加成功");
                        }
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
                    if(from.equals("info")){
                        changeData();
                    }else{
                        sendData();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    FoodInfoBean foodinfobean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.create_food_activity);
        binding.setVariable(BR.click, this);
        from = getIntent().getStringExtra("from");
        if(from.equals("info")){
           String jsons = getIntent().getStringExtra("data");
           foodinfobean = JsonUtil.getBean(jsons, FoodInfoBean.class);
        }else{
            path = getIntent().getStringExtra(CameraActivity.CAMERA_PATH_VALUE1);
        }
        initData();
        CreateFoodAdapter adapter = new CreateFoodAdapter(this, list, this, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
                if(id==1){
                    if(from.equals("info")){
                        changeData();
                    }else{
                        sendData();
                    }
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
                list.get(5).setValue("一般聯繫人");
                popupWindow.dismiss();
            }
        });
        btn_xblxr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(5).setValue("星標聯繫人");
                popupWindow.dismiss();
            }
        });
    }

    private void sendData() {
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("isOtherAdd", "0");
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
                    case "查看權限":
                        map.put("showLimit", getShowLimit(list.get(i).getValue()));
                        break;
                }
            }
        }
        showDialog();
        connect.postData(MyUrl.FOOD, map, new ConnectBack() {
            @Override
            public void success(String json) {
                loge(json);
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

    public String getShowLimit(String name){
        if(name.equals("星標聯繫人")){
            return "1";
        }else{
            return "2";
        }
    }

    private void changeData() {

        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("id", foodinfobean.getItem().getId()+"");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue().equals("")&&!list.get(i).getKey().equals("預估熱量 :")) {
                ToastUtil.showToast(activity, "請輸入" + list.get(i).getKey());
                return;
            } else {
                switch (list.get(i).getKey()) {
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
                        map.put("eatTime", list.get(i).getValue());
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
                    case "查看權限":
                        map.put("showLimit", getShowLimit(list.get(i).getValue()));
                        break;
                }
            }
        }
        showDialog();
        connect.putData(MyUrl.FOOD+"/"+foodinfobean.getItem().getId(), map, new ConnectBack() {
            @Override
            public void success(String json) {
                loge(json);
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
        if(from.equals("info")){
            list.add(new CreateFoodBean("image", getUrl(foodinfobean.getItem().getImageObject(),
                    foodinfobean.getItem().getBucket()), "image"));
            list.add(new CreateFoodBean("食物名", foodinfobean.getItem().getName(), "one_input"));
            list.add(new CreateFoodBean("預估熱量 :", "", "one_input"));
            list.add(new CreateFoodBean("進食日期 :", foodinfobean.getItem().getEatDate(), "one_select"));
            list.add(new CreateFoodBean("進食時間 :", foodinfobean.getItem().getEatTime(), "one_select"));
            list.add(new CreateFoodBean("查看權限", "一般聯繫人", "one_select"));
            list.add(new CreateFoodBean("營養成分（每份）", "  ", "one_select"));
            list.add(new CreateFoodBean("卡路里", foodinfobean.getItem().getCalorie()+"", "one_input"));
            list.add(new CreateFoodBean("脂肪總量", foodinfobean.getItem().getFat()+"", "one_input"));
            list.add(new CreateFoodBean("鈉", foodinfobean.getItem().getSodium()+"", "one_input"));
            list.add(new CreateFoodBean("碳水化合物", foodinfobean.getItem().getCarbohydrate()
                    +"", "one_input"));
            list.add(new CreateFoodBean("確定", "  ", "button"));
        }else{
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
        }
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
//        list.add(new CreateFoodBean("image",path,"image"));
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
