package com.fn.healfie.component.dialog;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.fn.healfie.R;
import com.wx.wheelview.widget.WheelView;

import java.util.List;

/**
 * author: sail
 * date :  2019/2/17
 * desc :  WheelSelectDialog
 */
public class WheelSelectDialog extends DialogFragment implements View.OnClickListener {

    private TextView cancelTv;
    private TextView sureTv;
    private WheelView wheelView;
    private DialogClick dialogClick;
    private List<String> data;
    private int selectItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        getDialog().setCanceledOnTouchOutside(true);//点击边际可消失
        View view = inflater.inflate(R.layout.wheel_select_dialog,container);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelTv = view.findViewById(R.id.cancel_tv);
        sureTv = view.findViewById(R.id.sure_tv);

        cancelTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);

        wheelView = view.findViewById(R.id.wheelView);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0ba7c5");
        wheelView.setStyle(style);
        data = getArguments().getStringArrayList("data");
        wheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(view.getContext()));
        wheelView.setSkin(WheelView.Skin.None);
        wheelView.setWheelData(data);
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                selectItem = position;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setDialogClick(DialogClick dialogClick){
        this.dialogClick = dialogClick;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cancel_tv){
            dismiss();
        }
        if(view.getId() == R.id.sure_tv){
            dialogClick.onSureClick(selectItem,data.get(selectItem));
            dismiss();
        }
    }

    public interface DialogClick {
        void onSureClick(int index, String item);
    }

}
