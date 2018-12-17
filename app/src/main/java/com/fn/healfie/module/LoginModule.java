package com.fn.healfie.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.fn.healfie.BR;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/10/28.
 */

public class LoginModule extends BaseObservable {

    private String name;
    private String password;
    private int imageId;
    private boolean pwdShow;

    @Bindable
    public boolean getPwdShow() {
        return pwdShow;
    }

    public void setPwdShow(boolean pwdShow) {
        this.pwdShow = pwdShow;
        notifyPropertyChanged(BR.pwdShow);
    }

    @Bindable
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
        notifyPropertyChanged(BR.imageId);
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
        Log.e(TAG, "setName: --------------");
        notifyPropertyChanged(BR.name);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
