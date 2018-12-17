package com.fn.healfie.adapter;

import android.databinding.BindingAdapter;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/10/29.
 */

public abstract class LoginAbstract {
    @BindingAdapter("android:src")
    public abstract void setSrc(ImageView imageView, int id);

    @BindingAdapter({"username","userpwd"})
    public abstract void changeButtonBack(Button button, String username,String userpwd);

    @BindingAdapter({"username","userpwd","usercode"})
    public abstract void changeButtonBack(Button button, String username,String userpwd,String usercode);

    @BindingAdapter({"image_url"})
    public abstract void settingUrl(ImageView imageView, String url);

    @BindingAdapter({"username"})
    public abstract void changeButtonBack(Button button, String username);

    @BindingAdapter({"select","order"})
    public abstract void lineShow(ImageView imageView, int select,String order);

}
