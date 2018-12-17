package com.fn.healfie.component.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.fn.healfie.R;
import com.fn.healfie.utils.DensityUtil;


public class CustomProgressDialog extends Dialog {
    MaterialProgressBar progress1;

    Context mContext;
    Activity activity;
    CustomProgressDialog dialog;

    public CustomProgressDialog(Context context, Activity activity) {
        super(context);
        this.mContext = context;
        this.activity = activity;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomProgressDialog show(CharSequence message) {

        dialog = new CustomProgressDialog(mContext, R.style.ProgressDialog);
        dialog.setContentView(R.layout.view_material_progress);

        progress1 = (MaterialProgressBar) dialog.findViewById(R.id.progress1);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.y = DensityUtil.dip2px(activity, 60);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.TOP);

        progress1.setColorSchemeResources(R.color.recyclerviewRefreshColor, R.color
                .recyclerviewRefreshColor, R.color.recyclerviewRefreshColor, R.color
                .recyclerviewRefreshColor);
        dialog.setCancelable(false);
        if (dialog != null) {
            dialog.show();
            dialog.setOnKeyListener(onKeyListener);
        }
        return dialog;
    }

    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismiss("");
                activity.finish();
            }
            return false;
        }
    };

    public CustomProgressDialog dismiss(CharSequence message) {
        if (dialog != null) {
            dialog.dismiss();
        }

        return dialog;

    }


}
