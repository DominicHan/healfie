package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.ListViewAdapter;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.FoodFragmentBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.FoodListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.HomeModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FoodFragment extends BaseFragment implements BaseOnClick {

    FoodFragmentBinding mBinding;
    private List<String> list = new ArrayList<String>();
    private List<String> listTag = new ArrayList<String>();
    int page = 1;
    int total = 0;
    int limit = 20;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FoodListBean bean = JsonUtil.getBean(msg.obj.toString(), FoodListBean.class);
                    if (bean.getResultCode().equals("200") && bean.getTotal() > 0) {
                        mBinding.lvFood.setVisibility(View.VISIBLE);
                        mBinding.rlNodata.setVisibility(View.GONE);
                    }else if(bean.getResultCode().equals("200")){
                        mBinding.lvFood.setVisibility(View.GONE);
                        mBinding.rlNodata.setVisibility(View.VISIBLE);
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
                    getData();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.food_fragment, container, false);
//        module = new HomeModule();
//        mBinding.setHome(module);
        mBinding.setVariable(BR.click, this);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_top:
                mBinding.lvFood.smoothScrollToPositionFromTop(0, 0);
                break;
            case R.id.iv_add:
                Intent intent = new Intent(getActivity().getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {
//        list.add("A");
//        listTag.add("A");
//        for (int i = 0; i < 3; i++) {
//            list.add("阿凡达" + i);
//        }
//        list.add("B");
//        listTag.add("B");
//        for (int i = 0; i < 3; i++) {
//            list.add("比特风暴" + i);
//        }
//        list.add("C");
//        listTag.add("C");
//        for (int i = 0; i < 30; i++) {
//            list.add("查理风云" + i);
//        }
//        ListViewAdapter adapter = new ListViewAdapter(getContext(), list, listTag);
//        mBinding.lvFood.setAdapter(adapter);
        getData();

    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("page", page + "");
        map.put("limit", limit + "");
        map.put("type", "1");
        connect.getData(MyUrl.RECORD, map, new ConnectBack() {
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
}
