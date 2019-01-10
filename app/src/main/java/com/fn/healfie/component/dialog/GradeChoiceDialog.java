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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fn.healfie.R;

//用户权限选择
public class GradeChoiceDialog extends DialogFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RadioGroup radioGroup;
    private TextView cancelTv;
    private TextView finishTv;
    private int checkIndex = 1;
    private String checkHint = "一般聯繫人";
    private DialogClick dialogClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题
        getDialog().setCanceledOnTouchOutside(true);//点击边际可消失
        View view = inflater.inflate(R.layout.grade_choice_dialog,container);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        radioGroup = view.findViewById(R.id.grade_rg);
        cancelTv = view.findViewById(R.id.cancel_tv);
        cancelTv.setOnClickListener(this);
        finishTv = view.findViewById(R.id.finish_tv);
        finishTv.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    case R.id.star_rb:
                        checkIndex = 0;
                        checkHint = "星標聯繫人";
                        break;
                    case R.id.normal_rb:
                        checkIndex = 1;
                        checkHint = "一般聯繫人";
                        break;
                    case R.id.block_rb:
                        checkIndex = 2;
                        checkHint = "屏蔽聯繫人";
                        break;

                }
            }
        });
    }

    public void setDialogClick(DialogClick dialogClick){
        this.dialogClick = dialogClick;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cancel_tv){
            dismiss();
        }
        if(view.getId() == R.id.finish_tv){
            dialogClick.onFinishClick(checkIndex,checkHint);
            dismiss();
        }
    }

    public interface DialogClick {
        void onFinishClick(int index,String hint);
    }
}
