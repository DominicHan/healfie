package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.FoodScendInfoAdapter;
import com.fn.healfie.adapter.MineInfoAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.component.dialog.GenderChoiceDialog;
import com.fn.healfie.component.imageselector.utils.ImageSelector;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.FoodScendInfoActivityBinding;
import com.fn.healfie.databinding.MineInfoActivityBinding;
import com.fn.healfie.event.EditMemberEvent;
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
import com.fn.healfie.utils.FileUtil;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

/**
 * from @zhaojian
 * content 創建食物
 */

public class MineInfoActivity extends BaseActivity implements BaseOnClick,GenderChoiceDialog.DialogClick {

    Activity activity = this;
    MineInfoActivityBinding binding;
    ArrayList<CreateFoodBean> list;
    String id;
    PopupWindow popupWindow;
    private GenderChoiceDialog genderChoiceDialog;
    final int REQUEST_NAME = 0x01;
    private final int REQUEST_IMAGE = 0x0003;
    MineInfoAdapter adapter;

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
        adapter = new MineInfoAdapter(this, list, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
                if(id == 2){
                    showGenderDialog();
                }
                if(id == 1){
                    Intent intent = new Intent(activity,EditNameActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("data",list.get(1).getValue());
                    activity.startActivityForResult(intent,REQUEST_NAME);
                }
                if(id == 3){
                    if(list.get(3).getValue().equals("設置會員名")){
                        Intent intent = new Intent(activity,EditNameActivity.class);
                        intent.putExtra("type","1");
                        intent.putExtra("data",list.get(3).getValue());
                        activity.startActivityForResult(intent,REQUEST_NAME);
                    }
                }
                if(id == 4){
                    Intent intent = new Intent(activity,MineQrActivity.class);
                    intent.putExtra("data",list.get(3).getValue());
                    activity.startActivityForResult(intent,REQUEST_NAME);
                }
                if(id == 0){
                    ImageSelector.builder()
                            .useCamera(true) // 设置是否使用拍照
                            .setSingle(true)  //设置是否单选
                            .setViewImage(true) //是否点击放大图片查看,，默认为true
                            .start(activity, REQUEST_IMAGE); // 打开相册
                }
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
            list.get(2).setValue((int)Float.parseFloat(item.getSex().toString())==1?"男":"女");
        }
        if(item.getUserName()!=null){
            list.get(3).setValue(item.getUserName().toString());
        }
        adapter.notifyDataSetChanged();

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

    private void showGenderDialog() {
        if (genderChoiceDialog != null)
            getFragmentManager().beginTransaction().remove(genderChoiceDialog);
        genderChoiceDialog = new GenderChoiceDialog();
        genderChoiceDialog.setDialogClick(this);
        genderChoiceDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onGenderClick(int index, String gender) {
        list.get(2).setValue(gender);
        HashMap<String, String> map = new HashMap<>();
        map.put("sex",index+"");
        editPersonInfo(map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_NAME){
                String type = data.getExtras().getString("type");
                String result = data.getExtras().getString("result");
                HashMap<String, String> map = new HashMap<>();
                if(type.equals("0")){
                    if(TextUtils.isEmpty(result)){
                        ToastUtil.showToast(this,"姓名不能為空");
                        return;
                    }
                    list.get(1).setValue(result);
                    map.put("name",result);
                }else if(type.equals("1")){
                    if(TextUtils.isEmpty(result)){
                        ToastUtil.showToast(this,"會員名不能為空不能為空");
                        return;
                    }
                    list.get(3).setValue(result);
                    map.put("userName",result);
                }
                editPersonInfo(map);
            }else if(requestCode == REQUEST_IMAGE){
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelector.SELECT_RESULT);
                if(!TextUtils.isEmpty(images.get(0))){
                    File file = new File(images.get(0));
                    Uri uri = Uri.fromFile(file);
                    if(file.exists()){
                        list.get(0).setValue(uri.toString());
                        adapter.notifyDataSetChanged();
                        try{
                            String imgBase64 = FileUtil.encodeBase64File(new Compressor(this).compressToFile(file));
                            HashMap<String, String> map = new HashMap<>();
                            map.put("image",imgBase64);
                            editPersonInfo(map);
                        }catch (Exception ex){

                        }

                    }
                }
            }
        }
    }

    private void editPersonInfo(HashMap<String, String> info){
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.putAll(info);
        connect.putData(MyUrl.MEMBERINFO, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 3;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
                EventBus.getDefault().post(new EditMemberEvent());
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

}
