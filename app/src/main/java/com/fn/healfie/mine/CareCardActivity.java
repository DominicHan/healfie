package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.print.PrinterId;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.HashMap;

public class CareCardActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private ImageView editIv;

    private TextView nameTv;
    private TextView sexTv;
    private TextView birthTv;
    private TextView heightTv;
    private TextView weightTv;
    private TextView bloodTv;
    private LinearLayout allergyLl;
    private LinearLayout sicknessLl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.care_card_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        editIv = findViewById(R.id.iv_edit);
        editIv.setOnClickListener(this);

        nameTv = findViewById(R.id.name_tv);
        sexTv = findViewById(R.id.sex_tv);
        birthTv = findViewById(R.id.birth_tv);
        heightTv = findViewById(R.id.height_tv);
        weightTv = findViewById(R.id.weight_tv);
        bloodTv = findViewById(R.id.blood_type_tv);
        allergyLl = findViewById(R.id.allergy_ll);
        sicknessLl = findViewById(R.id.sickness_ll);

        //getData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.iv_edit){
            Intent intent = new Intent(this,EditCareActivity.class);
            startActivity(intent);
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.MEDICALCARD, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                //myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

}
