package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.contact.AddContactActivity;
import com.fn.healfie.databinding.ContactFragmentBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.module.HomeModule;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;


public class ContactFragment extends BaseFragment implements BaseOnClick {

    ContactFragmentBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false, new LoginComponent(context));
//        module = new HomeModule();
//        module.setSelect(1);
//        module.setFirst("first");
//        module.setImageIdFirst(R.mipmap.ic_food_selected);
//        module.setTime("7月25日");
//        module.setLast("last");
//        module.setImageIdLast(R.mipmap.ic_medicine_normal);
//        mBinding.setHome(module);
        mBinding.setVariable(BR.click, ContactFragment.this);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.rl_add:
                Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
                break;
        }
    }

//    private void getData() {
//        showDialog();
//        MyConnect connect = new MyConnect();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
////        map.put("page", page + "");
////        map.put("limit", limit + "");
//        map.put("type", "1");
//        connect.getData(MyUrl.RECORD, map, new ConnectBack() {
//            @Override
//            public void success(String json) {
//                Message msg = new Message();
//                msg.what = 1;
//                msg.obj = json;
////                myHandler.sendMessage(msg);
//                hideDialog();
//            }
//
//            @Override
//            public void error(String json) {
//                hideDialog();
//                ToastUtil.errorToast(activity, "-1022");
//            }
//        });
//    }
}
