package com.fn.healfie.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.component.MyViewPager;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.event.DeleteContactEvent;
import com.fn.healfie.event.EditContactEvent;
import com.fn.healfie.interfaces.TabChangeLisen;
import com.fn.healfie.main.DrugsFragment;
import com.fn.healfie.main.FoodFragment;
import com.fn.healfie.main.HomeFragment;
import com.fn.healfie.model.MessageBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.google.gson.Gson;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ContactHomePageActivity extends FragmentActivity implements View.OnClickListener{


    Activity activity = this;

    private boolean isFirst = true;
    private String data;
    private MessageBean bean;

    private ImageView backIv;
    private ImageView editIv;
    private TextView nameTv;
    private TextView remarkTv;
    private RoundImageView headIv;

    private RelativeLayout foodRl;
    private LinearLayout foodLn;
    private ImageView foodIv;
    private TextView foodTv;
    private ImageView foodIndicatorIv;

    private RelativeLayout medicineRl;
    private LinearLayout medicineLn;
    private ImageView medicineIv;
    private TextView medicineTv;
    private ImageView medicineIndicatorIv;

    private MyViewPager myViewPager;
    FragmentManager childFragmentManager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    // xml-->view
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    FoodFragment chat;
    DrugsFragment friend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.contact_homepage_activity);

        EventBus.getDefault().register(this);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        editIv = findViewById(R.id.iv_edit);
        editIv.setOnClickListener(this);

        nameTv = findViewById(R.id.name_tv);
        remarkTv = findViewById(R.id.remark_tv);
        headIv = findViewById(R.id.head_iv);

        foodRl = findViewById(R.id.rl_food);
        foodRl.setOnClickListener(this);
        foodLn = findViewById(R.id.ln_food);
        foodIv = findViewById(R.id.iv_food);
        foodTv = findViewById(R.id.tv_food);
        foodIndicatorIv = findViewById(R.id.indicator_food_iv);

        medicineRl = findViewById(R.id.rl_medicine);
        medicineRl.setOnClickListener(this);
        medicineLn = findViewById(R.id.ln_medicine);
        medicineIv = findViewById(R.id.iv_medicine);
        medicineTv = findViewById(R.id.tv_medicine);
        medicineIndicatorIv = findViewById(R.id.indicator_medicine_iv);

        myViewPager = findViewById(R.id.vpLayout);

        isFirst = true;
        foodIv.setImageResource(R.mipmap.ic_food_selected);
        medicineIv.setImageResource(R.mipmap.ic_medicine_normal);
        foodTv.setTextColor(getResources().getColor(R.color.buttonBlue));
        medicineTv.setTextColor(getResources().getColor(R.color.textGray9));
        foodIndicatorIv.setVisibility(View.VISIBLE);
        medicineIndicatorIv.setVisibility(View.INVISIBLE);

        initData();
    }


    @Subscribe
    public void refreshData(EditContactEvent event) {
        nameTv.setText(event.getMsg().getName());
        remarkTv.setText("備註："+event.getMsg().getRemark());
    }

    @Subscribe
    public void deleteData(DeleteContactEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initData(){
        data = getIntent().getStringExtra("data");
        bean =  JsonUtil.getBean(data,MessageBean.class);

        nameTv.setText(bean.getName());
        remarkTv.setText("備註："+bean.getRemark());

        Glide.with(activity).load(getUrl(bean.getHeadImageObject(),bean.getHeadImageBucket())).into(headIv);
        childFragmentManager = getSupportFragmentManager();
        initFragmetList();
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(childFragmentManager,mFragmentList);
        myViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        myViewPager.setAdapter(mViewPagerFragmentAdapter);
        myViewPager.setCurrentItem(0);
    }

    public void initFragmetList() {
        Bundle bundle = new Bundle();
        bundle.putString("data",bean.getMemberId()+"");
        chat = new FoodFragment();
        chat.setArguments(bundle);
        friend = new DrugsFragment();
        friend.setArguments(bundle);
        mFragmentList.add(chat);
        mFragmentList.add(friend);
        chat.changeLisen(new TabChangeLisen() {
            @Override
            public void tabChangeLisen(String name, String id) {

            }
        });
        friend.changeLisen(new TabChangeLisen() {
            @Override
            public void tabChangeLisen(String name, String id) {

            }
        });
    }

    public String getUrl(String img, String bucket) {
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(activity, PrefeKey.TOKEN, "");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }

        if(view.getId() == R.id.iv_edit){
            Intent intents = new Intent(this, DeleteContactActivity.class);
            Gson gson = new Gson();
            intents.putExtra("data", gson.toJson(bean));
            startActivity(intents);
        }

        if(view.getId() == R.id.rl_food){
            if(!isFirst){
                isFirst = true;
                foodIv.setImageResource(R.mipmap.ic_food_selected);
                medicineIv.setImageResource(R.mipmap.ic_medicine_normal);
                foodTv.setTextColor(getResources().getColor(R.color.buttonBlue));
                medicineTv.setTextColor(getResources().getColor(R.color.textGray9));
                foodIndicatorIv.setVisibility(View.VISIBLE);
                medicineIndicatorIv.setVisibility(View.INVISIBLE);
            }
        }

        if(view.getId() == R.id.rl_medicine){
            if(isFirst){
                isFirst = false;
                foodIv.setImageResource(R.mipmap.ic_food_normal);
                medicineIv.setImageResource(R.mipmap.ic_medicine_selected);
                foodTv.setTextColor(getResources().getColor(R.color.textGray9));
                medicineTv.setTextColor(getResources().getColor(R.color.buttonBlue));
                foodIndicatorIv.setVisibility(View.INVISIBLE);
                medicineIndicatorIv.setVisibility(View.VISIBLE);
            }
        }

    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG,"onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {
            //selectposition = position;
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
