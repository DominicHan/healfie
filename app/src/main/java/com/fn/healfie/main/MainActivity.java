package com.fn.healfie.main;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.generated.callback.OnClickListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.fn.healfie.R;
import com.fn.healfie.app.MyApp;
import com.fn.healfie.component.bottombar.BottomBar;
import com.fn.healfie.component.dialog.ToLoginDialog;
import com.fn.healfie.databinding.MainActivityBinding;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.utils.StatusBarUtil;


/**
 * from @zhaojian
 * content 主activity 控制跳轉
 */
public class MainActivity extends AppCompatActivity implements ToLoginDialog.DialogClick, BottomBar.onBottomClick {

    private MyApp myApp;
    private ToLoginDialog toLoginDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        getSupportActionBar().hide();
        XLog.init(LogLevel.ERROR);
        myApp = (MyApp) this.getApplication();
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        /**
         * from @zhaojian
         * content  創建底部導航欄
         */
        binding.bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(HomeFragment.class,
                        "首頁",
                        R.mipmap.ic_home_normal,
                        R.mipmap.ic_home_selected)
                .addItem(ContactFragment.class,
                        "聯繫人",
                        R.mipmap.ic_contact_normal,
                        R.mipmap.ic_contact_selected)
                .addItem(MineFragment.class,
                        "我的",
                        R.mipmap.ic_me_normal,
                        R.mipmap.ic_me_selected)
                .build();
        binding.bottomBar.setTitleBeforeAndAfterColor("#d4d8dc", "#0ba7c5");
        binding.bottomBar.setBottomClick(this);
    }

    private void showGenderDialog() {
        if (toLoginDialog != null)
            getFragmentManager().beginTransaction().remove(toLoginDialog);
        toLoginDialog = new ToLoginDialog();
        toLoginDialog.setDialogClick(this);
        toLoginDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onSureClick() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.putExtra("from","main");
        startActivity(intent);
    }

    @Override
    public boolean onItemClick(int index) {
        if(myApp.isVisitor() && index != 0){
            showGenderDialog();
        }

        return myApp.isVisitor();
    }
}

