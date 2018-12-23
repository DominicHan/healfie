package com.fn.healfie.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.component.RoundImageView;
import com.fn.healfie.component.pickdatetime.DatePickDialog;
import com.fn.healfie.component.pickdatetime.OnSureListener;
import com.fn.healfie.component.pickdatetime.bean.DateParams;
import com.fn.healfie.databinding.CreateFoodButtonItemBinding;
import com.fn.healfie.databinding.CreateFoodLittleinputItemBinding;
import com.fn.healfie.databinding.CreateFoodMoreinputItemBinding;
import com.fn.healfie.databinding.CreateFoodOneinputItemBinding;
import com.fn.healfie.databinding.CreateFoodSelectItemBinding;
import com.fn.healfie.food.CreateFoodActivity;
import com.fn.healfie.food.FoodScendInfoActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.model.CreateFoodBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class FoodScendInfoAdapter extends BaseAdapter {
    private List<CreateFoodBean> listTag = null;
    private Context context;
    private FoodScendInfoActivity activity;
    private BaseOnClick back;

    public FoodScendInfoAdapter(Context context, List<CreateFoodBean> objects, FoodScendInfoActivity activity, BaseOnClick back) {
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
        } else if (position == 2 || position == 3 || position == 4) {
            return 2;
        } else if (position == 7 || position == 8 || position == 9 || position == 10) {
            return 3;
        } else if (position == 6 || position == 5) {
            return 4;
        } else {
            return 5;
        }
    }

    public void openDate(int first, int last, final CreateFoodBean bean){
        Calendar todayCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, 6);

        new DatePickDialog.Builder()
                .setTypes(first,last)
                .setCurrentDate(todayCal.getTime())
                .setStartDate(startCal.getTime())
                .setEndDate(endCal.getTime())
                .setOnSureListener(new OnSureListener() {
                    @Override
                    public void onSure(Date date) {
                        String message = new SimpleDateFormat("HH:mm").format(date);
                        bean.setValue(message);
                    }
                })
                .show(context);
    }

    public void openYear(int first,int center, int last, final CreateFoodBean bean){
        Calendar todayCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, 6);

        new DatePickDialog.Builder()
                .setTypes(first,center,last)
                .setCurrentDate(todayCal.getTime())
                .setStartDate(startCal.getTime())
                .setEndDate(endCal.getTime())
                .setOnSureListener(new OnSureListener() {
                    @Override
                    public void onSure(Date date) {
                        String message = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        bean.setValue(message);
                    }
                })
                .show(context);
    }

    @Override
    public int getViewTypeCount() {
        return 6;
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
        CreateFoodOneinputItemBinding createfoodoneinputitembinding;
        CreateFoodMoreinputItemBinding createfoodmoreinputitembinding;
        CreateFoodSelectItemBinding createfoodselectitembinding;
        CreateFoodLittleinputItemBinding createfoodlittleinputitembinding;
        CreateFoodButtonItemBinding createfoodbuttonitembinding;
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
                createfoodoneinputitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.create_food_oneinput_item, parent, false);
                convertView = createfoodoneinputitembinding.getRoot();
            } else {
                createfoodoneinputitembinding = DataBindingUtil.getBinding(convertView);
            }
            createfoodoneinputitembinding.setVariable(BR.food, getItem(position));
        } else if (type == 2) {
            if (convertView == null) {
                createfoodmoreinputitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.create_food_moreinput_item, parent, false);
                convertView = createfoodmoreinputitembinding.getRoot();
            } else {
                createfoodmoreinputitembinding = DataBindingUtil.getBinding(convertView);
            }
            if (listTag.get(position).getKey().equals("預估熱量 :")) {
                createfoodmoreinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapetop);
                createfoodmoreinputitembinding.ivBottom.setVisibility(View.VISIBLE);
                createfoodmoreinputitembinding.rlBack.setVisibility(View.GONE);
            } else if (listTag.get(position).getKey().equals("進食日期 :")) {
                createfoodmoreinputitembinding.rlInput.setBackgroundColor(context.getResources().getColor(R.color.white));
                createfoodmoreinputitembinding.ivBottom.setVisibility(View.VISIBLE);
                createfoodmoreinputitembinding.rlBack.setVisibility(View.VISIBLE);
            } else {
                createfoodmoreinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapebottom);
                createfoodmoreinputitembinding.ivBottom.setVisibility(View.GONE);
                createfoodmoreinputitembinding.rlBack.setVisibility(View.VISIBLE);
            }
            createfoodmoreinputitembinding.setVariable(BR.food, getItem(position));
            createfoodmoreinputitembinding.setVariable(BR.click, new OnClick(listTag.get(position)));
        } else if (type == 4) {
            if (convertView == null) {
                createfoodselectitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.create_food_select_item, parent, false);
                convertView = createfoodselectitembinding.getRoot();
            } else {
                createfoodselectitembinding = DataBindingUtil.getBinding(convertView);
            }
            if (listTag.get(position).getKey().equals("查看權限")) {
                createfoodselectitembinding.ivRight.setImageResource(R.mipmap.ic_back1_normal);
                createfoodselectitembinding.rlAll.setPadding(context.getResources().getDimensionPixelSize(R.dimen.ten), context.getResources().getDimensionPixelSize(R.dimen.ten), context.getResources().getDimensionPixelSize(R.dimen.ten), context.getResources().getDimensionPixelSize(R.dimen.ten));
            } else {
                createfoodselectitembinding.ivRight.setImageResource(R.mipmap.ic_back2_normal);
                createfoodselectitembinding.rlAll.setPadding(context.getResources().getDimensionPixelSize(R.dimen.ten), 0, context.getResources().getDimensionPixelSize(R.dimen.ten), context.getResources().getDimensionPixelSize(R.dimen.five));
            }
            createfoodselectitembinding.setVariable(BR.food, getItem(position));
            createfoodselectitembinding.setVariable(BR.click, new OnClick(listTag.get(position)));
        } else if (type == 3) {
            if (convertView == null) {
                createfoodlittleinputitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.create_food_littleinput_item, parent, false);
                convertView = createfoodlittleinputitembinding.getRoot();
            } else {
                createfoodlittleinputitembinding = DataBindingUtil.getBinding(convertView);
            }
            if (listTag.get(position).getKey().equals("卡路里")) {
                createfoodlittleinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapetop);
                createfoodlittleinputitembinding.ivBottom.setVisibility(View.VISIBLE);
            } else if (listTag.get(position).getKey().equals("碳水化合物")) {
                createfoodlittleinputitembinding.rlInput.setBackgroundResource(R.drawable.white_shapebottom);
                createfoodlittleinputitembinding.ivBottom.setVisibility(View.GONE);
            } else {
                createfoodlittleinputitembinding.rlInput.setBackgroundColor(context.getResources().getColor(R.color.white));
                createfoodlittleinputitembinding.ivBottom.setVisibility(View.VISIBLE);
            }
            SpannableString ss = new SpannableString("點擊輸入");//定义hint的值
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan(13, true);//设置字体大小 true表示单位是sp
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            createfoodlittleinputitembinding.etInput.setHint(new SpannedString(ss));
            createfoodlittleinputitembinding.setVariable(BR.food, getItem(position));
        } else {
            if (convertView == null) {
                createfoodbuttonitembinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.create_food_button_item, parent, false);
                convertView = createfoodbuttonitembinding.getRoot();
            } else {
                createfoodbuttonitembinding = DataBindingUtil.getBinding(convertView);
            }
            createfoodbuttonitembinding.setVariable(BR.click, new OnClick(listTag.get(position)));
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
            Log.e("111111111111", "onSaveClick: "+bean.getKey());
            if(bean.getKey().equals("進食日期 :")){
                openYear(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY,bean);
            }
            if(bean.getKey().equals("進食時間 :")){
                openDate(DateParams.TYPE_HOUR, DateParams.TYPE_MINUTE,bean);
            }
            if(bean.getKey().equals("確定")){
                back.onSaveClick(1);
            }
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