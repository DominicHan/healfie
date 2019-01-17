package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.utils.StatusBarUtil;

public class EditNameActivity extends BaseActivity implements View.OnClickListener{


    private Activity activity = this;

    private ImageView backIv;
    private TextView finishTv;
    private EditText remarkEt;
    private TextView titleTv;
    private TextView hintTv;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.edit_name_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        finishTv = findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        titleTv = findViewById(R.id.title_tv);
        remarkEt = findViewById(R.id.remark_et);

        hintTv = findViewById(R.id.hint_tv);

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
            intent.putExtra("type", type);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void initData(){
        String data = getIntent().getStringExtra("data");
        type = getIntent().getStringExtra("type");
        if(type.equals("0")){
            titleTv.setText("設置姓名");
            remarkEt.setHint("請輸入姓名");
            hintTv.setVisibility(View.GONE);
        }else if(type.equals("1")){
            titleTv.setText("設置會員名");
            remarkEt.setHint("請輸入會員名");
            hintTv.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(data)){
            remarkEt.setText(data);
        }
    }
}
