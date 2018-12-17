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
import com.fn.healfie.databinding.HomeFragmentBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.module.HomeModule;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements BaseOnClick {
    HomeFragmentBinding mBinding;
    HomeModule module;
    FragmentManager childFragmentManager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    // xml-->view
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false, new LoginComponent(context));
        module = new HomeModule();
        module.setSelect(1);
        module.setFirst("first");
        module.setImageIdFirst(R.mipmap.ic_food_selected);
        module.setTime("7月25日");
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
        Fragment chat = new FoodFragment();
        Fragment friend = new DrugsFragment();
        mFragmentList.add(chat);
        mFragmentList.add(friend);
    }

    @Override
    protected void initData() {
        mBinding.ViewPagerLayout.setScrollble(false);
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
        }
    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG,"onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
