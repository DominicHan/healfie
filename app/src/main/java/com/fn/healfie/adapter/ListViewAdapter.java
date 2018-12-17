package com.fn.healfie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fn.healfie.R;
import com.fn.healfie.model.BaseBean;
import com.fn.healfie.model.FoodBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class ListViewAdapter extends ArrayAdapter<String> {
    private List<String> listTag = null;

    public ListViewAdapter(Context context, List<String> objects, List<String> tags) {
        super(context, 0, objects);
        this.listTag = tags;
    }

    @Override
    public int getItemViewType(int position) {
        if (listTag.contains(getItem(position))) {
            return 0;
        } else {
            return 1;
        }
//        if (homeItemList != null && position < homeItemList.size()) {
//            return homeItemList.get(position).getItemType().getValue();
//        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public boolean isEnabled(int position) {
        if (listTag.contains(getItem(position))) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpecialHolder holder;
        TimeHolder timeHolder;
        int type = getItemViewType(position);
        if (type == 0) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_time_item, null);
                timeHolder = new TimeHolder(convertView);
                convertView.setTag(timeHolder);
            } else {
                timeHolder = (TimeHolder) convertView.getTag();
            }
            timeHolder.refreshUI(new FoodBean());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_list_item, null);
                holder = new SpecialHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (SpecialHolder) convertView.getTag();
            }
            holder.refreshUI(new FoodBean());
        }
        return convertView;
    }

    class TimeHolder {

        public TimeHolder(View convertView) {
            if (convertView != null) {
            }
        }

        public void refreshUI(FoodBean homeItem) {

        }
    }

    class SpecialHolder {
        ImageView rv_image;

        public SpecialHolder(View convertView) {
            if (convertView != null) {
                rv_image = convertView.findViewById(R.id.rv_image);
            }
        }

        public void refreshUI(FoodBean homeItem) {
            Glide.with(getContext()).load("http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg").into(rv_image);
//            Picasso.with(getContext()).load("http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg").into(rv_image);
        }
    }


}