package com.fn.healfie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.FoodScendInfoBottomItemBinding;
import com.fn.healfie.databinding.FoodScendInfoLittleItemBinding;
import com.fn.healfie.databinding.FoodScendInfoNameItemBinding;
import com.fn.healfie.databinding.FoodScendInfoQuanxianItemBinding;
import com.fn.healfie.databinding.FoodScendInfoTimeItemBinding;
import com.fn.healfie.databinding.FoodScendInfoYingyangItemBinding;
import com.fn.healfie.databinding.MineInfoContentItemBinding;
import com.fn.healfie.databinding.MineInfoHeadItemBinding;
import com.fn.healfie.databinding.MineInfoZxingItemBinding;
import com.fn.healfie.food.FoodScendInfoActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;
import com.fn.healfie.utils.PrefeUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class MineInfoAdapter extends BaseAdapter {
    private List<CreateFoodBean> listTag = null;
    private Context context;
    private BaseOnClick back;

    public MineInfoAdapter(Context context, List<CreateFoodBean> objects, BaseOnClick back) {
        this.listTag = objects;
        this.context = context;
        this.back = back;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1||position == 2|| position == 3) {
            return 1;
        } else  {
            return 2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
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
        int type = getItemViewType(position);
        MineInfoHeadItemBinding mineinfoheaditembinding;
        MineInfoContentItemBinding mineinfocontentitembinding;
        MineInfoZxingItemBinding mineinfozxingitembinding;

        if (type == 0) {
            if (convertView == null) {
               mineinfoheaditembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.mine_info_head_item, parent, false);
                convertView = mineinfoheaditembinding.getRoot();
            } else {
                mineinfoheaditembinding = DataBindingUtil.getBinding(convertView);
            }
            mineinfoheaditembinding.setVariable(BR.food, getItem(position));
            mineinfoheaditembinding.ivTx.changeRadius(100);
            mineinfoheaditembinding.setVariable(BR.click, new OnClick(position));
            Glide.with(context).load(listTag.get(position).getValue()).into(mineinfoheaditembinding.ivTx);
        } else if (type == 1) {
            if (convertView == null) {
                mineinfocontentitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.mine_info_content_item, parent, false);
                convertView = mineinfocontentitembinding.getRoot();
            } else {
                mineinfocontentitembinding = DataBindingUtil.getBinding(convertView);
            }
            mineinfocontentitembinding.setVariable(BR.click, new OnClick(position));
            mineinfocontentitembinding.setVariable(BR.food, getItem(position));
        } else {
            if (convertView == null) {
               mineinfozxingitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.mine_info_zxing_item, parent, false);
                convertView = mineinfozxingitembinding.getRoot();
            } else {
                mineinfozxingitembinding = DataBindingUtil.getBinding(convertView);
            }
            mineinfozxingitembinding.setVariable(BR.click, new OnClick(position));
            mineinfozxingitembinding.setVariable(BR.food, getItem(position));
        }
        return convertView;
    }




    class OnClick implements BaseOnClick {
        int bean;

        OnClick(int bean) {
            this.bean = bean;
        }

        @Override
        public void onSaveClick(int id) {
            back.onSaveClick(bean);
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