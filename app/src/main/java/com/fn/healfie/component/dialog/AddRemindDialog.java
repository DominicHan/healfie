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

/**
 * author: sail
 * date :  2019/2/16
 * desc :  ToLoginDialog
 */
public class AddRemindDialog extends DialogFragment implements View.OnClickListener {

    private TextView cancelTv;
    private TextView sureTv;
    private TextView tvTitle;
    private DialogClick dialogClick;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        getDialog().setCanceledOnTouchOutside(true);//点击边际可消失
        View view = inflater.inflate(R.layout.add_remind_dialog,container);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelTv = view.findViewById(R.id.cancel_tv);
        sureTv = view.findViewById(R.id.sure_tv);
        tvTitle = view.findViewById(R.id.tv_title);

        cancelTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
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

    public void setDialogTitle(String title){
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cancel_tv){
            dialogClick.onAddRemindCancel();
            dismiss();
        }
        if(view.getId() == R.id.sure_tv){
            dialogClick.onAddRemindClick();
            dismiss();
        }
    }

    public interface DialogClick {
        void onAddRemindClick();
        void onAddRemindCancel();
    }
}
