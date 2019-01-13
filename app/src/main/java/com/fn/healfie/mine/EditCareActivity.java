package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.utils.StatusBarUtil;

public class EditCareActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private TextView finishTv;
    private RelativeLayout allergyRl;
    private TextView allergyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.edit_care_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        allergyRl = findViewById(R.id.allergy_rl);
        allergyRl.setOnClickListener(this);
        allergyTv = findViewById(R.id.allergy_tv);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.finish_tv){

        }
        if(view.getId() == R.id.allergy_rl){
            Intent intent = new Intent(activity,AddAllergyActivity.class);
            startActivity(intent);
        }

    }
}
