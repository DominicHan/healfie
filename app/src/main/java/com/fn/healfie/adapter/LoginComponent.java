package com.fn.healfie.adapter;

import android.content.Context;

/**
 * Created by Administrator on 2018/10/29.
 */

public class LoginComponent implements android.databinding.DataBindingComponent {
    Context context;
    public LoginComponent(Context context){
        this.context = context;
    }

    @Override
    public LoginAbstract getLoginAbstract() {
        return new LoginAdapter(context);
    }
}
