package com.fn.healfie.mine;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.google.zxing.util.CreateQrUtil;
import com.fn.healfie.utils.StatusBarUtil;

//我的二维码
public class MineQrActivity extends BaseActivity implements View.OnClickListener{

    private ImageView qrIv;
    private ImageView backIv;
    private Bitmap qrBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.mine_qr_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        qrIv = findViewById(R.id.qr_iv);

        initData();
    }

    private void initData(){
        String data = getIntent().getStringExtra("data");
        qrBitmap = CreateQrUtil.createQRImage(data,500,500);

        qrIv.setImageBitmap(qrBitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (qrBitmap != null && !qrBitmap.isRecycled()) {
            qrBitmap.recycle();
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }
}
