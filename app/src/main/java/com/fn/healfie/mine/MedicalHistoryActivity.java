package com.fn.healfie.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.utils.StatusBarUtil;

public class MedicalHistoryActivity extends BaseActivity implements View.OnClickListener{

    private ImageView backIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.medical_history_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }
}
