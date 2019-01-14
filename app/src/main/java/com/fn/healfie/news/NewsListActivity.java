package com.fn.healfie.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.fn.healfie.BaseActivity;
import com.fn.healfie.R;
import com.fn.healfie.adapter.AddAllergyAdapter;
import com.fn.healfie.adapter.NewsAdapter;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.ContactListBean;
import com.fn.healfie.model.NewsBean;
import com.fn.healfie.model.NewsListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.StatusBarUtil;
import com.fn.healfie.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class NewsListActivity extends BaseActivity implements View.OnClickListener{

    private Activity activity = this;

    private ImageView backIv;

    private PtrFrameLayout ptrClassicFrameLayout;
    private ListView newsLv;
    private List<NewsBean> mData;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
        setContentView(R.layout.news_list_activity);

        backIv = findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);

        newsLv = findViewById(R.id.news_lv);
        ptrClassicFrameLayout = findViewById(R.id.ptr);

        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(activity);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        ptrClassicFrameLayout.setHeaderView(header);
        // mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        ptrClassicFrameLayout.addPtrUIHandler(header);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //getData();

            }

            /**
             * 检查是否可以执行下来刷新，比如列表为空或者列表第一项在最上面时。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                System.out.println("MainActivity.checkCanDoRefresh");
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                // return true;
            }
        });

        initData();
    }

    private void initData(){
        mData = new ArrayList<>();
        mAdapter = new NewsAdapter(activity,mData);
        newsLv.setAdapter(mAdapter);

        getData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_back){
            finish();
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.MESSAGELIST, map, new ConnectBack() {
            @Override
            public void success(String json) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = json;
                myHandler.sendMessage(msg);
                hideDialog();
            }

            @Override
            public void error(String json) {
                hideDialog();
                ToastUtil.errorToast(activity, "-1022");
            }
        });
    }

    private void changeData(NewsListBean bean){
        mData.clear();

        if(bean.getItem() != null && bean.getItem().size() > 0){
            mData.addAll(bean.getItem());
        }

        mAdapter.notifyDataSetChanged();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    NewsListBean bean = JsonUtil.getBean(msg.obj.toString(), NewsListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200")) {
                        changeData(bean);
                    } else if (bean.getResultCode().equals("-10010")) {
                        showDialog();
                        sendLogin(new ConnectLoginBack() {
                            @Override
                            public void success(String json, String header) {
                                hideDialog();
                                RegisterBean registerBean = JsonUtil.getBean(json, RegisterBean.class);
                                if(registerBean.getResultCode().equals("200")){
                                    PrefeUtil.saveString(activity, PrefeKey.TOKEN, registerBean.getItem().getAuthorization());
                                    myHandler.sendEmptyMessage(2);

                                }else{
                                    Intent intent = new Intent(activity, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        ToastUtil.errorToast(activity, bean.getResultCode());
                    }
                    break;
                case 2:
                    getData();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
