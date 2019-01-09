package com.fn.healfie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fn.healfie.R;

import com.fn.healfie.databinding.SearchDrugsListItemBinding;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.DrugsBean;
import com.fn.healfie.model.DrugsSearchBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class SearchDrugsListAdapter extends BaseAdapter {
    private List<DrugsSearchBean.ItemBean> list = null;
    private Context context;
    private BaseOnClick back;

    public SearchDrugsListAdapter(Context context, List<DrugsSearchBean.ItemBean> list, BaseOnClick back) {
        this.list = list;
        this.context = context;
        this.back = back;
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
        SearchDrugsListItemBinding bindingList;
            if (convertView == null) {
                 bindingList = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_drugs_list_item, parent, false);
                convertView = bindingList.getRoot();
            } else {
                bindingList = DataBindingUtil.getBinding(convertView);
            }
            bindingList.tvTime.setText(list.get(position).getName());
            bindingList.tvKj.setText(list.get(position).getTakeMode());
//            Glide.with(context).load(getUrl(list.get(position).getImageObject(),list.get(position).getBucket())).into(bindingList.rvImage);
//            bindingList.setVariable(BR.click, new OnClick(list.get(position)));
//            bindingList.tvKj.setText(list.get(position).getTakeDose()+list.get(position).getTakeUnit());
//            bindingList.tvTime.setText(list.get(position).getEatTime());
//            bindingList.tvTitle.setText(list.get(position).getEnName());
        return convertView;
    }

    class OnClick implements BaseOnClick {
        DrugsBean bean;

        OnClick(DrugsBean bean) {
            this.bean = bean;
        }

        @Override
        public void onSaveClick(int id) {
            back.onSaveClick(bean.getId());
        }
    }


}