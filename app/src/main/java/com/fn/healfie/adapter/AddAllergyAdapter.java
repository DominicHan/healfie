package com.fn.healfie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fn.healfie.R;
import com.fn.healfie.model.AllergyBean;

import java.util.List;

public class AddAllergyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<AllergyBean> mDatas;
    private Context context;

    public AddAllergyAdapter(Context context, List<AllergyBean> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public AllergyBean getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.add_allergy_item, parent, false);
        }

        LinearLayout itemLl = convertView.findViewById(R.id.delete_ll);
        TextView nameTv = convertView.findViewById(R.id.name_tv);

        final  AllergyBean bean = mDatas.get(position);

        nameTv.setText(bean.getName());

        return convertView;
    }


}
