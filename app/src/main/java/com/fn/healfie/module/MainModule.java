package com.fn.healfie.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.fn.healfie.BR;

/**
 * Created by Administrator on 2018/10/28.
 */

public class MainModule extends BaseObservable {
    private String name;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
