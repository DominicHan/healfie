package com.fn.healfie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.databinding.FoodScendInfoBottomItemBinding;
import com.fn.healfie.databinding.FoodScendInfoLittleItemBinding;
import com.fn.healfie.databinding.FoodScendInfoNameItemBinding;
import com.fn.healfie.databinding.FoodScendInfoQuanxianItemBinding;
import com.fn.healfie.databinding.FoodScendInfoTimeItemBinding;
import com.fn.healfie.databinding.FoodScendInfoYingyangItemBinding;
import com.fn.healfie.drugs.food.DrugsScendInfoActivity;
import com.fn.healfie.food.FoodScendInfoActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DrugsScendInfoAdapter extends BaseAdapter {
    private List<CreateFoodBean> listTag = null;
    private Context context;
    private DrugsScendInfoActivity activity;
    private BaseOnClick back;

    public DrugsScendInfoAdapter(Context context, List<CreateFoodBean> objects, DrugsScendInfoActivity activity, BaseOnClick back) {
        this.listTag = objects;
        this.context = context;
        this.activity = activity;
        this.back = back;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2 || position == 3|| position == 4|| position == 5 ) {
            return 2;
        } else if (position == 6) {
            return 3;
        } else{
            return 4;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }


    @Override
    public int getCount() {
        return listTag.size();
    }

    @Override
    public Object getItem(int i) {
        return listTag.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder imageholder;
        FoodScendInfoNameItemBinding foodscendinfonameitembinding;
        FoodScendInfoTimeItemBinding createfoodmoreinputitembinding;
        FoodScendInfoQuanxianItemBinding createfoodselectitembinding;
        FoodScendInfoLittleItemBinding createfoodlittleinputitembinding;
        FoodScendInfoBottomItemBinding createfoodbuttonitembinding;
        FoodScendInfoYingyangItemBinding foodscendinfoyingyangitembinding;
        int type = getItemViewType(position);
        if (type == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.create_food_image_item, null);
                imageholder = new ImageHolder(convertView);
                convertView.setTag(imageholder);
            } else {
                imageholder = (ImageHolder) convertView.getTag();
            }
            imageholder.refreshUi(listTag.get(position));
        } else if (type == 1) {
            if (convertView == null) {
                foodscendinfonameitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_scend_info_name_item, parent, false);
                convertView = foodscendinfonameitembinding.getRoot();
            } else {
                foodscendinfonameitembinding = DataBindingUtil.getBinding(convertView);
            }
            foodscendinfonameitembinding.ivHot.setVisibility(View.GONE);
            foodscendinfonameitembinding.setVariable(BR.food, getItem(position));
        } else if (type == 2) {
            if (convertView == null) {
               createfoodmoreinputitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_scend_info_time_item, parent, false);
                convertView = createfoodmoreinputitembinding.getRoot();
            } else {
                createfoodmoreinputitembinding = DataBindingUtil.getBinding(convertView);
            }
            createfoodmoreinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shape5);
            createfoodmoreinputitembinding.ivBottom.setVisibility(View.VISIBLE);
            if (listTag.get(position).getKey().equals("服藥方式 :")) {
                createfoodmoreinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapetop);
                createfoodmoreinputitembinding.ivBottom.setVisibility(View.VISIBLE);
            } else if (listTag.get(position).getKey().equals("服藥時間 :")) {
                createfoodmoreinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapebottom);
                createfoodmoreinputitembinding.ivBottom.setVisibility(View.GONE);
            }
            createfoodmoreinputitembinding.setVariable(BR.food, getItem(position));
        } else if (type == 3) {
            if (convertView == null) {
               createfoodselectitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_scend_info_quanxian_item, parent, false);
                convertView = createfoodselectitembinding.getRoot();
            } else {
                createfoodselectitembinding = DataBindingUtil.getBinding(convertView);
            }
            createfoodselectitembinding.setVariable(BR.food, getItem(position));
        }else {
            if (convertView == null) {
               createfoodbuttonitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_scend_info_bottom_item, parent, false);
                convertView = createfoodbuttonitembinding.getRoot();
            } else {
                createfoodbuttonitembinding = DataBindingUtil.getBinding(convertView);
            }

        }
        return convertView;
    }

    class OnClick implements BaseOnClick {
        CreateFoodBean bean;

        OnClick(CreateFoodBean bean) {
            this.bean = bean;
        }

        @Override
        public void onSaveClick(int id) {

        }
    }

    class ImageHolder {
        RoundImageView rv_image;

        public ImageHolder(View convertView) {
            if (convertView != null) {
                rv_image = convertView.findViewById(R.id.rv_image);
                rv_image.changeRadius(5);
            }
        }

        public void refreshUi(CreateFoodBean bean) {
            Glide.with(context).load(bean.getValue()).into(rv_image);
        }
    }


}