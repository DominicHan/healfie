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
import android.widget.LinearLayout;

import com.fn.healfie.R;

//个人--性别选择
public class GenderChoiceDialog extends DialogFragment implements View.OnClickListener {

    private LinearLayout maleLl;
    private LinearLayout femaleLl;
    private LinearLayout cancelLl;
    private DialogClick dialogClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        getDialog().setCanceledOnTouchOutside(true);//点击边际可消失
        View view = inflater.inflate(R.layout.gender_choice_dialog,container);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        maleLl = view.findViewById(R.id.male_ll);
        femaleLl = view.findViewById(R.id.female_ll);
        cancelLl = view.findViewById(R.id.cancel_ll);
        maleLl.setOnClickListener(this);
        femaleLl.setOnClickListener(this);
        cancelLl.setOnClickListener(this);
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
        if(view.getId() == R.id.cancel_ll){
            dismiss();
        }
        if(view.getId() == R.id.male_ll){
            dialogClick.onGenderClick(1,"男");
            dismiss();
        }
        if(view.getId() == R.id.female_ll){
            dialogClick.onGenderClick(0,"女");
            dismiss();
        }
    }

    public interface DialogClick {
        void onGenderClick(int index,String gender);
    }
}
