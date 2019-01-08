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

import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;

import com.fn.healfie.databinding.FoodListItemBinding;
import com.fn.healfie.databinding.FoodTimeItemBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.FoodBean;
import com.fn.healfie.utils.PrefeUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class FoodListAdapter extends BaseAdapter {
    private List<FoodBean> list = null;
    private Context context;
    private BaseOnClick back;

    public FoodListAdapter(Context context, List<FoodBean> list, BaseOnClick back) {
        this.list = list;
        this.context = context;
        this.back = back;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getId() < 0) {
            return 0;
        } else {
            return 1;
        }
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodTimeItemBinding binding;
        FoodListItemBinding bindingList;
        int type = getItemViewType(position);
        if (type == 0) {
            if (convertView == null) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_time_item, parent, false);
                convertView = binding.getRoot();
            } else {
                binding = DataBindingUtil.getBinding(convertView);
            }
            binding.tvTime.setText(list.get(position).getName());

        } else {
            if (convertView == null) {
                bindingList = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.food_list_item, parent, false);
                convertView = bindingList.getRoot();
            } else {
                bindingList = DataBindingUtil.getBinding(convertView);
            }
            Glide.with(context).load(getUrl(list.get(position).getImageObject(),list.get(position).getBucket())).into(bindingList.rvImage);
            bindingList.setVariable(BR.click, new OnClick(list.get(position)));
            bindingList.tvKj.setText(list.get(position).getCalorie()+"KJ");
            bindingList.tvTime.setText(list.get(position).getEatTime());
            bindingList.tvTitle.setText(list.get(position).getName());
        }
        return convertView;
    }

    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, "");
    }

    class OnClick implements BaseOnClick {
        FoodBean bean;

        OnClick(FoodBean bean) {
            this.bean = bean;
        }

        @Override
        public void onSaveClick(int id) {
            back.onSaveClick(bean.getId());
        }
    }


}