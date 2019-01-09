package com.fn.healfie.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.fn.healfie.BR;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/10/28.
 */

public class SearchModule extends BaseObservable {

    private String name;

    private String have;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.e(TAG, "setName: --------------");
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getHave() {
        return have;
    }

    public void setHave(String have) {
        this.name = name;
        Log.e(TAG, "setName: --------------");
        notifyPropertyChanged(BR.have);
    }

}
