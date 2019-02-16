package com.fn.healfie.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.component.camera.CameraTitleActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.RegisterActivityBinding;
import com.fn.healfie.databinding.SaveNameActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.RegisterModule;
import com.fn.healfie.module.SaveNameModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 登錄頁
 */

public class SaveNameActivity extends BaseActivity implements BaseOnClick {

    Activity activity = this;
    SaveNameActivityBinding binding;
    SaveNameModule module;
    String token = "";
    String path="";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    BaseBean bean = JsonUtil.getBean(msg.obj.toString(), BaseBean.class);
                    if (bean.getResultCode().equals("200")) {
                        PrefeUtil.saveString(activity, PrefeKey.TOKEN, token);
                        Intent intents = new Intent(SaveNameActivity.this, MainActivity.class);
                        startActivity(intents);
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:

                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.save_name_activity, new LoginComponent(this));
        module = new SaveNameModule();
        module.setName("");
        binding.setSave(module);
        binding.setVariable(BR.click, this);
        token = getIntent().getStringExtra(PrefeKey.TOKEN);
        binding.ivTx.changeRadius(100);
    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.btn_tj:
                this.sendData();
                break;
            case R.id.iv_tx:
                Intent intent = new Intent(this, CameraTitleActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (data != null) {
                    path =  data.getStringExtra("path");
                    File file = new File(path);
                    Glide.with(this).load(file).into(binding.ivTx);
                }
//        if (requestCode == PROCESS) {
//            if (resultCode == RESULT_OK) {
//                Intent intent = new Intent();
//                if (data != null) {
//                    intent.putExtra(CAMERA_RETURN_PATH,
//                            data.getStringExtra(CAMERA_PATH_VALUE2));
//                }
//                setResult(RESULT_OK, intent);
//                finish();
//            } else {
//                if (data != null) {
//                    File dir = new File(data.getStringExtra(CAMERA_PATH_VALUE2));
//                    if (dir != null) {
//                        dir.delete();
//                    }
//                }
//            }
//        }
    }

    /**
     * from @zhaojian
     * content 提交信息
     */
    public void sendData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", token);
        map.put("name", module.getName());
        if(!path.equals("")){
            Bitmap bm = BitmapFactory.decodeFile(path);
            Bitmap mSrcBitmap = Bitmap.createScaledBitmap(bm, 400, 400, true);
            bm.recycle();
            bm = null;
            map.put("image", bitmapToBase64(mSrcBitmap));
        }
        connect.putData(MyUrl.MEMBERINFO, map, new ConnectBack() {
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
}
