package com.fn.healfie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fn.healfie.R;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/10/29.
 */

public class LoginAdapter extends LoginAbstract {
    Context context;

    public LoginAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void setSrc(ImageView imageView, int id) {
        Log.e("xwz--->", "ImageAdapter:  " + "setText: ");
        imageView.setImageResource(id);
    }

    @Override
    public void changeButtonBack(Button button, String username, String userpwd) {
        Log.e(TAG, "changeButtonBack: " + username + "---------" + userpwd);
        if (username.equals("") || userpwd.equals("") || userpwd.length() < 6) {
            button.setBackgroundResource(R.drawable.gray_shape);
            button.setTextColor(context.getResources().getColor(R.color.buttonTextGray));
            button.setEnabled(false);
        } else {
            button.setBackgroundResource(R.drawable.shape);
            button.setTextColor(context.getResources().getColor(R.color.white));
            button.setEnabled(true);
        }
    }

    @Override
    public void changeButtonBack(Button button, String username, String userpwd, String usercode) {
        if (userpwd.length() < 6 || usercode.length() < 4 || username.length() < 11) {
            button.setBackgroundResource(R.drawable.gray_shape);
            button.setTextColor(context.getResources().getColor(R.color.buttonTextGray));
            button.setEnabled(false);
        } else {
            button.setBackgroundResource(R.drawable.shape);
            button.setTextColor(context.getResources().getColor(R.color.white));
            button.setEnabled(true);
        }
    }

    @Override
    public void settingUrl(ImageView imageView, String url) {
        if (url == null || url.equals("")) {
            imageView.setImageResource(R.mipmap.img_chathead_default);
        } else {
            Picasso.with(context).load(url).placeholder(R.mipmap.img_chathead_default).into(imageView);
        }
    }

    @Override
    public void changeButtonBack(Button button, String username) {
        if (username.equals("")) {
            button.setBackgroundResource(R.drawable.gray_shape);
            button.setTextColor(context.getResources().getColor(R.color.buttonTextGray));
            button.setEnabled(false);
        } else {
            button.setBackgroundResource(R.drawable.shape);
            button.setTextColor(context.getResources().getColor(R.color.white));
            button.setEnabled(true);
        }
    }

    @Override
    public void lineShow(ImageView imageView, int select,String order) {
        if(order.equals("first")){
            if(select==1){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.INVISIBLE);
            }
        }else{
            if(select==1){
                imageView.setVisibility(View.INVISIBLE);
            }else{
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

}
