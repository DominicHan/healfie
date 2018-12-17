package com.fn.healfie.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.fn.healfie.BR;

/**
 * Created by Administrator on 2018/11/11.
 */

public class CreateFoodBean extends BaseObservable {
    private String key;
    private String value;
    private String type;

    public CreateFoodBean(String key, String value, String type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Bindable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        notifyPropertyChanged(BR.key);
    }

    @Bindable
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
}
