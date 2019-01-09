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
import com.fn.healfie.contact.ContactHomePageActivity;
import com.fn.healfie.model.MessageBean;
import com.fn.healfie.utils.PrefeUtil;
import com.google.gson.Gson;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<MessageBean> mDatas;
    private Context context;

    public ContactListAdapter(Context context, List<MessageBean> datas){
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
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.contact_list_item, parent, false);
        }

        LinearLayout itemLl = convertView.findViewById(R.id.message_ll);
        RoundImageView avatarIv = convertView.findViewById(R.id.avatar_iv);
        TextView nameTv = convertView.findViewById(R.id.name_tv);
        TextView descTv = convertView.findViewById(R.id.remark_tv);
        TextView statusTv = convertView.findViewById(R.id.status_tv);

        final MessageBean bean = mDatas.get(position);

        Glide.with(context).load(getUrl(bean.getHeadImageObject(),bean.getHeadImageBucket())).into(avatarIv);
        nameTv.setText(bean.getName());
        descTv.setText("備註："+bean.getRemark());
        if(bean.getShowLimit() == 1){
            statusTv.setText("星標");
            statusTv.setTextColor(Color.parseColor("#FFFFFF"));
            statusTv.setBackground(context.getResources().getDrawable(R.drawable.red_status_shape));
        }else if(bean.getShowLimit() == 2){
            statusTv.setText("一般");
            statusTv.setTextColor(Color.parseColor("#FFFFFF"));
            statusTv.setBackground(context.getResources().getDrawable(R.drawable.yellow_status_shape));
        }else if(bean.getShowLimit() == 3){
            statusTv.setText("屏蔽");
            statusTv.setTextColor(Color.parseColor("#999999"));
            statusTv.setBackground(context.getResources().getDrawable(R.drawable.gray_status_shape));
        }


        itemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(context, ContactHomePageActivity.class);
                Gson gson = new Gson();
                intents.putExtra("data", gson.toJson(bean));
                context.startActivity(intents);
            }
        });
        return convertView;
    }


    public String getUrl(String img, String bucket) {
        Log.e("getUrl: ", MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, ""));
        return MyUrl.SHOWIMAGE + "?bucket=" + bucket + "&imageObject=" + img + "&authorization=" + PrefeUtil.getString(context, PrefeKey.TOKEN, "");
    }

}
