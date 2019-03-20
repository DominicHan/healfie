package com.fn.healfie.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fn.healfie.BR;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.app.MyApp;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.LoginActivityBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.main.MainActivity;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.LoginModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.MD5Util;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * from @zhaojian
 * content 登錄頁
 */

public class LoginActivity extends BaseActivity implements BaseOnClick {

    LoginActivityBinding binding;
    LoginModule module;
    Activity activity = this;
    MyApp myApp;
    String from = "";
    CallbackManager callbackManager;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity, new LoginComponent(this));
        module = new LoginModule();
        module.setImageId(R.mipmap.ic_notify_normal);
        module.setPwdShow(true);
        module.setName("");
        module.setPassword("");
        binding.setLogin(module);
        binding.setVariable(BR.click, this);
        myApp = (MyApp) this.getApplication();
        from = getIntent().getStringExtra("from");
        PrefeUtil.saveString(this,PrefeKey.TOKEN,"");
        PrefeUtil.saveString(this,PrefeKey.USERNAME,"");
        PrefeUtil.saveString(this,PrefeKey.USERPWD,"");

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (object != null) {
//                                    email = object.optString("email");
//                                    firstname = object.optString("first_name");
//                                    lastname = object.optString("last_name");
//                                    Log.e("log", "LoginActivity - email----" + email);
                                    String userName = object.optString("name");
//                                    Log.e("log", "LoginActivity - getLoginInfo::---" + object.toString());

                                    AccessToken accessToken = loginResult.getAccessToken();
                                    String fbuserId = accessToken.getUserId();
//                                    String token = accessToken.getToken();
//                                    Log.e("log", "LoginActivity - accessToken：：：" + accessToken);
//                                    Log.e("log", "LoginActivity - userid:::" + fbuserId);

                                    if (accessToken != null) {
                                        //如果登录成功，跳转到登录成功界面，拿到facebook返回的email/userid等值，在我们后台进行操作
                                        facebookLogin(fbuserId, userName);
                                    }
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale," +
                                "updated_time,timezone,age_range,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d("FacebookCallback", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d("FacebookCallback", "onError");
                    }
                });

    }

    /**
     * from @zhaojian
     * content 点击事件绑定
     */
    @Override
    public void onSaveClick(int id) {
        //Log.e(TAG, "1111111" + module.getName());
        switch (id) {
            case R.id.iv_see:
                if (module.getPwdShow()) {
                    module.setImageId(R.mipmap.ic_notify_pressed);
                    module.setPwdShow(false);
                } else {
                    module.setImageId(R.mipmap.ic_notify_normal);
                    module.setPwdShow(true);
                }
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.etPwd.setSelection(binding.etPwd.getText().toString().length());
                            }
                        });
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 500);//0.5秒后执行光标后移操作
                break;
            case R.id.tv_zc:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dl:
                sendLogin();
                break;
            case R.id.tv_wjmm:
                Intent intents = new Intent(LoginActivity.this, ResetPwdActivity.class);
                startActivity(intents);
                break;
            case R.id.tv_tyyx:
                myApp.setVisitor(true);
                Intent tyIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(tyIntent);
                break;
            case R.id.iv_zhdl:
                //Toast.makeText(getApplicationContext(), "測試", Toast.LENGTH_LONG).show();
                LoginManager.getInstance()
                        .logInWithReadPermissions(LoginActivity.this,
                                Arrays.asList("public_profile"));
                break;
        }
    }

    /**
     * from @zhaojian
     * content 獲取手機唯一標識符
     */
    private String getIMEI() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }

    /**
     * from @zhaojian
     * content  登录
     */
    private void sendLogin() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", module.getName());
        //map.put("password",MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
        map.put("password", module.getPassword());
        map.put("isFaceBookLogin", "0");
        map.put("mobileId", getIMEI());
        map.put("isAutoLogin", "2");
        map.put("mobileType", "2");
        map.put("registrationId", "1");
        connect.login(MyUrl.LOGIN, map, new ConnectLoginBack() {
            @Override
            public void success(String json, String header) {
                RegisterBean beans = JsonUtil.getBean(json, RegisterBean.class);
                if (beans.getResultCode().equals("200")) {
                    myApp.setVisitor(false);
                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, beans.getItem().getAuthorization());
                    PrefeUtil.saveString(activity, PrefeKey.USERNAME, module.getName());
                    PrefeUtil.saveString(activity, PrefeKey.USERPWD,  MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
                    if((from != null) && from.equals("main")){
                        finish();
                    }else{
                        Intent intents = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intents);
                        finish();
                    }

                } else if (beans.getResultCode().equals("40013") && header != null && !header.equals("")) {
                    myApp.setVisitor(false);
                    PrefeUtil.saveString(activity, PrefeKey.USERNAME, module.getName());
                    PrefeUtil.saveString(activity, PrefeKey.USERPWD, MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
                    if((from != null) && from.equals("main")){
                        finish();
                    }else{
                        Intent intent = new Intent(activity, SaveNameActivity.class);
                        intent.putExtra(PrefeKey.TOKEN, header);
                        startActivity(intent);
                    }
                } else {
                    ToastUtil.errorToast(activity, beans.getResultCode());
                }
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void facebookLogin(String facebookUserId, String facebookUserName) {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", module.getName());
        //map.put("password",MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
        map.put("password", module.getPassword());
        map.put("isFaceBookLogin", "1");
        map.put("mobileId", getIMEI());
        map.put("isAutoLogin", "2");
        map.put("mobileType", "2");
        map.put("registrationId", "1");
        map.put("otherOpenId", facebookUserId);
        map.put("name", facebookUserName);
        connect.login(MyUrl.LOGIN, map, new ConnectLoginBack() {
            @Override
            public void success(String json, String header) {
                RegisterBean beans = JsonUtil.getBean(json, RegisterBean.class);
                if (beans.getResultCode().equals("200")) {
                    myApp.setVisitor(false);
                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, beans.getItem().getAuthorization());
                    PrefeUtil.saveString(activity, PrefeKey.USERNAME, module.getName());
                    PrefeUtil.saveString(activity, PrefeKey.USERPWD,  MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
                    if((from != null) && from.equals("main")){
                        finish();
                    }else{
                        Intent intents = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intents);
                        finish();
                    }

                } else if (beans.getResultCode().equals("40013") && header != null && !header.equals("")) {
                    myApp.setVisitor(false);
                    PrefeUtil.saveString(activity, PrefeKey.USERNAME, module.getName());
                    PrefeUtil.saveString(activity, PrefeKey.USERPWD, MD5Util.MD5(MD5Util.MD5(module.getName()+ module.getPassword())));
                    if((from != null) && from.equals("main")){
                        finish();
                    }else{
                        Intent intent = new Intent(activity, SaveNameActivity.class);
                        intent.putExtra(PrefeKey.TOKEN, header);
                        startActivity(intent);
                    }
                } else {
                    ToastUtil.errorToast(activity, beans.getResultCode());
                }
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }
}
