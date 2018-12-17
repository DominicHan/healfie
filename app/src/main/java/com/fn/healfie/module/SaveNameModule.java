package com.fn.healfie.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.fn.healfie.BR;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/10/28.
 */

public class SaveNameModule extends BaseObservable {

    private String name;
    private String imageUrl;


    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Log.e(TAG, "setName: --------------");
        notifyPropertyChanged(BR.name);
    }
}
