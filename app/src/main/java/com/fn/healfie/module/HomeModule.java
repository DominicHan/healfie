package com.fn.healfie.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.fn.healfie.BR;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/10/28.
 */

public class HomeModule extends BaseObservable {

    private String time;
    private int select;
    private int imageIdFirst;
    private int imageIdLast;

    private String first;

    private String last;
    @Bindable
    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
        notifyPropertyChanged(BR.first);
    }
    @Bindable
    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
        notifyPropertyChanged(BR.last);
    }

    @Bindable
    public int getImageIdFirst() {
        return imageIdFirst;
    }

    public void setImageIdFirst(int imageIdFirst) {
        this.imageIdFirst = imageIdFirst;
        notifyPropertyChanged(BR.imageIdFirst);
    }
    @Bindable
    public int getImageIdLast() {
        return imageIdLast;
    }

    public void setImageIdLast(int imageIdLast) {
        this.imageIdLast = imageIdLast;
        notifyPropertyChanged(BR.imageIdLast);
    }

    @Bindable
    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
        notifyPropertyChanged(BR.select);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

}
