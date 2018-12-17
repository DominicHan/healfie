package com.fn.healfie;

import android.app.Activity;

import com.fn.healfie.component.loading.CustomProgressDialog;

/**
 * Created by Administrator on 2018/11/1.
 */

public class BaseActivity extends Activity {
    private CustomProgressDialog mCustomProgressDialog;

    public void showDialog() {
        if(mCustomProgressDialog==null){
            mCustomProgressDialog = new CustomProgressDialog(this, this);
        }
        mCustomProgressDialog.show("");
    }

    public void hideDialog() {
        if(mCustomProgressDialog==null){
            mCustomProgressDialog = new CustomProgressDialog(this, this);
        }
        mCustomProgressDialog.dismiss("");
    }
}
