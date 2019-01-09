package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.utils.StatusBarUtil;

public class EditRemarkActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private TextView finishTv;
    private EditText remarkEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.edit_remark_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        remarkEt = findViewById(R.id.remark_et);

        initData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }

        if(view.getId() == R.id.finish_tv){
            Intent intent = new Intent();
            intent.putExtra("result", remarkEt.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void initData(){
        String data = getIntent().getStringExtra("data");
        remarkEt.setText(data);
    }


}
