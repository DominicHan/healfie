package com.fn.healfie.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.AddAllergyAdapter;
import com.fn.healfie.model.AllergyBean;
import com.fn.healfie.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class AddSicknessActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;
    private TextView addTv;

    private PtrFrameLayout ptrClassicFrameLayout;
    private ListView allergyLv;
    private List<AllergyBean> mData;
    private AddAllergyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.add_sickness_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        addTv = findViewById(R.id.add_tv);
        addTv.setOnClickListener(this);

        allergyLv = findViewById(R.id.allergy_lv);
        ptrClassicFrameLayout = findViewById(R.id.ptr);

        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(activity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptrClassicFrameLayout.setHeaderView(header);
        // mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        ptrClassicFrameLayout.addPtrUIHandler(header);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //getData();

            }

            /**
             * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                System.out.println("MainActivity.checkCanDoRefresh");
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                // return true;
            }
        });

        initData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
        if(view.getId() == R.id.add_tv){
            Intent intent = new Intent(activity, SearchSicknessActivity.class);
            startActivity(intent);
        }
    }

    private void initData(){
        mData = new ArrayList<>();
        mAdapter = new AddAllergyAdapter(activity,mData);
        allergyLv.setAdapter(mAdapter);

        getData();
    }

    private void getData(){
        AllergyBean bean1 = new AllergyBean();
        bean1.setName("花生");
        mData.add(bean1);

        AllergyBean bean2 = new AllergyBean();
        bean2.setName("海鲜");
        mData.add(bean2);

        mAdapter.notifyDataSetChanged();
    }
}
