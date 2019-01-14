package com.fn.healfie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fn.healfie.R;
import com.fn.healfie.model.NewsBean;
import com.fn.healfie.news.NewsDetailActivity;
import com.fn.healfie.utils.TimesUtils;
import com.google.gson.Gson;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<NewsBean> mDatas;
    private Context context;

    public NewsAdapter(Context context, List<NewsBean> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context = context;
    }

    public int getCount() {
        return mDatas.size();
    }

    @Override
    public NewsBean getItem(int i) {
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
            convertView = mInflater.inflate(R.layout.news_list_item, parent, false);
        }
        TextView titleTv = convertView.findViewById(R.id.title_tv);
        TextView contentTv = convertView.findViewById(R.id.content_tv);
        TextView dateTv = convertView.findViewById(R.id.date_tv);
        TextView moreTv = convertView.findViewById(R.id.more_tv);

        final NewsBean bean = mDatas.get(position);
        titleTv.setText(bean.getTitle());
        contentTv.setText(bean.getContent());
        dateTv.setText(TimesUtils.getTime(bean.getCreateTime()));
        moreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                Gson gson = new Gson();
                intent.putExtra("data",gson.toJson(bean));
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
