package com.fn.healfie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.contact.AuditContactActivity;
import com.fn.healfie.model.MessageBean;
import com.fn.healfie.utils.PrefeUtil;
import com.google.gson.Gson;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MessageBean> mDatas;
    private Context context;

    public MessageListAdapter(Context context, List<MessageBean> datas){
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public MessageBean getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int type = getItemViewType(position);
        if(type == 0 || type == 2){
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.message_hint_item, parent, false);
            }
            TextView hintTv = convertView.findViewById(R.id.message_title_tv);
            if(type == 2){
                hintTv.setText("待處理申請");
            }else if(type == 0){
                hintTv.setText("已處理申請");
            }

        }else if(type == 1 || type == 3){
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.message_list_item, parent, false);
            }

            LinearLayout itemLl = convertView.findViewById(R.id.message_ll);
            RoundImageView avatarIv = convertView.findViewById(R.id.avatar_iv);
            TextView nameTv = convertView.findViewById(R.id.name_tv);
            TextView descTv = convertView.findViewById(R.id.remark_tv);
            TextView statusTv = convertView.findViewById(R.id.status_tv);
            TextView createTv = convertView.findViewById(R.id.crate_time_tv);

            final MessageBean bean = mDatas.get(position);

            Glide.with(context).load(getUrl(bean.getHeadImageObject(),bean.getHeadImageBucket())).into(avatarIv);
            nameTv.setText(bean.getName());
            descTv.setText(bean.getRemark());
            createTv.setText(bean.getCreateTime()+"");
            if(type == 1){
                statusTv.setText("同意");
                statusTv.setTextColor(Color.parseColor("#FFFFFF"));
                statusTv.setBackground(context.getResources().getDrawable(R.drawable.shape));
            }
            if(type == 3){
                statusTv.setText(bean.getAuditStatus() == 1 ? "已同意":"已拒絕");
                statusTv.setTextColor(Color.parseColor("#666666"));
                statusTv.setBackground(null);
            }

            itemLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(type == 1){
                        Intent intents = new Intent(context, AuditContactActivity.class);
                        Gson gson = new Gson();
                        intents.putExtra("data", gson.toJson(bean));
                        context.startActivity(intents);
                    }
                }
            });


        }
        return convertView;
    }


    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, "");
    }

}
