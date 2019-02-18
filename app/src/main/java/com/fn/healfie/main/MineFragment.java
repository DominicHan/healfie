package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.contact.AddContactActivity;
import com.fn.healfie.databinding.ContactFragmentBinding;
import com.fn.healfie.databinding.MineFragmentBinding;
import com.fn.healfie.event.EditContactEvent;
import com.fn.healfie.event.EditMemberEvent;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.mine.CareCardActivity;
import com.fn.healfie.mine.CompanyProfileActivity;
import com.fn.healfie.mine.FeedBackActivity;
import com.fn.healfie.mine.HealthReportActivity;
import com.fn.healfie.mine.MedicalHistoryActivity;
import com.fn.healfie.mine.MineInfoActivity;
import com.fn.healfie.mine.SafetyCheckActivity;
import com.fn.healfie.model.FoodListBean;
import com.fn.healfie.model.MineBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.SaveNameModule;
import com.fn.healfie.news.NewsListActivity;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;


public class MineFragment extends BaseFragment implements BaseOnClick {

    MineFragmentBinding mBinding;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MineBean bean = JsonUtil.getBean(msg.obj.toString(), MineBean.class);
                    if (bean.getResultCode().equals("200")) {
                        mBinding.tvName.setText(bean.getItem().getName());
                        Glide.with(getActivity()).load(getUrl(bean.getItem().getImageObject(),bean.getItem().getBucket())).into(mBinding.ivTx);
                    } else if (bean.getResultCode().equals("-10010")) {
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
                                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
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

    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(getActivity(), PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(getActivity(), PrefeKey.TOKEN, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.mine_fragment, container, false, new LoginComponent(context));
        mBinding.setVariable(BR.click, this);
        SaveNameModule module = new SaveNameModule();
        module.setName("");
        mBinding.setSave(module);
        View view = mBinding.getRoot();
        mBinding.ivTx.changeRadius(100);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshData(EditMemberEvent event) {
        sendData();
    }


    @Override
    protected void initData() {
        sendData();
    }

    public void sendData() {
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


    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.tv_logout:
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.rl_jkf:
                Intent intents = new Intent(getActivity().getApplicationContext(), MineInfoActivity.class);
                startActivity(intents);
                break;
            case R.id.rl_fwsm:
                Intent fujsIn = new Intent(getActivity().getApplicationContext(), CompanyProfileActivity.class);
                startActivity(fujsIn);
                break;
            case R.id.rl_ylk:
                Intent ylkIn = new Intent(getActivity().getApplicationContext(), CareCardActivity.class);
                startActivity(ylkIn);
                break;
            case R.id.rl_feedback:
                Intent feedback = new Intent(getActivity().getApplicationContext(), FeedBackActivity.class);
                startActivity(feedback);
                break;
            case R.id.rl_safety:
                Intent safety = new Intent(getActivity().getApplicationContext(), SafetyCheckActivity.class);
                startActivity(safety);
                break;
            case R.id.rl_fyls:
                Intent fylsIn = new Intent(getActivity().getApplicationContext(), MedicalHistoryActivity.class);
                startActivity(fylsIn);
                break;
            case R.id.news_iv:
                Intent news = new Intent(getActivity().getApplicationContext(), NewsListActivity.class);
                startActivity(news);
                break;
            case R.id.health_num_btn:
                Intent report = new Intent(getActivity().getApplicationContext(), HealthReportActivity.class);
                startActivity(report);
                break;
        }
    }
}