package com.fn.healfie.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.model.NewsBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.TimesUtils;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private TextView titleTv;
    private TextView contentTv;
    private TextView dateTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.news_detail_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        titleTv = findViewById(R.id.title_tv);
        contentTv = findViewById(R.id.content_tv);
        dateTv = findViewById(R.id.date_tv);

        initData();
    }

    private void initData(){
        String json = getIntent().getStringExtra("data");
        if(!TextUtils.isEmpty(json)){
            NewsBean bean = JsonUtil.getBean(json, NewsBean.class);
            titleTv.setText(bean.getTitle());
            contentTv.setText(bean.getContent());
            dateTv.setText(TimesUtils.getTime(bean.getCreateTime()));
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }
}
