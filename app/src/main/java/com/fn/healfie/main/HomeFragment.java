package com.fn.healfie.main;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.component.pickdatetime.DatePickDialog;
import com.fn.healfie.component.pickdatetime.OnSureListener;
import com.fn.healfie.component.pickdatetime.bean.DateParams;
import com.fn.healfie.databinding.HomeFragmentBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.TabChangeLisen;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.module.HomeModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends BaseFragment implements BaseOnClick {
    HomeFragmentBinding mBinding;
    HomeModule module;
    FragmentManager childFragmentManager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    // xml-->view
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    FoodFragment chat;
    DrugsFragment friend;
    int selectposition = 0;
    String foodName = "";
    String foodId = "";
    String drugsName = "";
    String drugsId = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false, new LoginComponent(context));
        module = new HomeModule();
        module.setSelect(1);
        module.setFirst("first");
        module.setImageIdFirst(R.mipmap.ic_food_selected);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        module.setTime(simpleDateFormat.format(date));
        module.setLast("last");
        module.setImageIdLast(R.mipmap.ic_medicine_normal);
        mBinding.setHome(module);
        childFragmentManager = getChildFragmentManager();
        initFragmetList();
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(childFragmentManager,mFragmentList);
        initViewPager();
        mBinding.setVariable(BR.click, HomeFragment.this);
        View view = mBinding.getRoot();
        return view;
    }

    public void initViewPager() {
        mBinding.ViewPagerLayout.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mBinding.ViewPagerLayout.setAdapter(mViewPagerFragmentAdapter);
        mBinding.ViewPagerLayout.setCurrentItem(0);
    }

    public void initFragmetList() {
         chat = new FoodFragment();
         friend = new DrugsFragment();
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

    @Override
    protected void initData() {
        mBinding.ViewPagerLayout.setScrollble(false);
    }

    public void openYear(int first,int center, int last){
        try {
        Calendar todayCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR,1);
        loge(endCal.getTime().toString());
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-09-17 20:27:00");
        new DatePickDialog.Builder()
                .setTypes(first,center,last)
                .setCurrentDate(todayCal.getTime())
                .setStartDate(date)
                .setEndDate(endCal.getTime())
                .setOnSureListener(new OnSureListener() {
                    @Override
                    public void onSure(Date date) {
                        String message = new SimpleDateFormat("MM-dd").format(date);
                        module.setTime(message);
                        chat.changeTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        friend.changeTime(new SimpleDateFormat("yyyy-MM-dd").format(date));
                    }
                })
                .show(context);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.rl_food:
                if (module.getSelect() == 2) {
                    module.setSelect(1);
                    module.setImageIdLast(R.mipmap.ic_medicine_normal);
                    module.setImageIdFirst(R.mipmap.ic_food_selected);
                    mBinding.ViewPagerLayout.setCurrentItem(0);
                }
                break;
            case R.id.rl_medicine:
                if (module.getSelect() == 1) {
                    module.setSelect(2);
                    module.setImageIdLast(R.mipmap.ic_medicine_selected);
                    module.setImageIdFirst(R.mipmap.ic_food_normal);
                    mBinding.ViewPagerLayout.setCurrentItem(1);
                }
                break;
            case R.id.tv_time:
                openYear(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY);
                break;
            case R.id.iv_down:
                openYear(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY);
                break;
        }
    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG,"onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {
            selectposition = position;
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
