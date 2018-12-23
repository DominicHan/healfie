package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.contact.AddContactActivity;
import com.fn.healfie.databinding.ContactFragmentBinding;
import com.fn.healfie.databinding.MineFragmentBinding;
import com.fn.healfie.interfaces.BaseOnClick;


public class MineFragment extends BaseFragment {

    MineFragmentBinding mBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.mine_fragment, container, false, new LoginComponent(context));
//        module = new HomeModule();
//        module.setSelect(1);
//        module.setFirst("first");
//        module.setImageIdFirst(R.mipmap.ic_food_selected);
//        module.setTime("7月25日");
//        module.setLast("last");
//        module.setImageIdLast(R.mipmap.ic_medicine_normal);
//        mBinding.setHome(module);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    protected void initData() {

    }



}