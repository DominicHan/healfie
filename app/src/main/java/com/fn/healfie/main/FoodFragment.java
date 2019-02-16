package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.CreateFoodAdapter;
import com.fn.healfie.adapter.FoodListAdapter;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.app.MyApp;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.databinding.FoodFragmentBinding;
import com.fn.healfie.food.FoodScendInfoActivity;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.interfaces.TabChangeLisen;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.FoodBean;
import com.fn.healfie.model.FoodListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.HomeModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.common.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;


public class FoodFragment extends BaseFragment implements BaseOnClick {

    MyApp myApp;
    FoodFragmentBinding mBinding;
    private List<FoodBean> list = new ArrayList<FoodBean>();
    int page = 1;
    int total = 0;
    int limit = 20;
    String date = "";
    private String memberId;
    private FoodListAdapter adapter;
    TabChangeLisen callBack;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    FoodListBean bean = JsonUtil.getBean(msg.obj.toString(), FoodListBean.class);
                    loge(msg.obj.toString()+"");
                    if (bean.getResultCode().equals("200") && bean.getTotal() > 0) {
                        mBinding.lvFood.setVisibility(View.VISIBLE);
                        mBinding.rlNodata.setVisibility(View.GONE);
                        changeData(msg.obj.toString());
                        callBack.tabChangeLisen("",bean.getStayAuditCount()+"");
                    }else if(bean.getResultCode().equals("200")){
                        mBinding.lvFood.setVisibility(View.GONE);
                        mBinding.rlNodata.setVisibility(View.VISIBLE);
                        callBack.tabChangeLisen("",bean.getStayAuditCount()+"");
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
                                    Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
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

    private void changeData(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            String key = null;
            String value = null;

            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if(key.equals("item")){
                    value = jsonObject.getString(key);
                    break;
                }
            }
            changeItem(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void changeItem(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            String key = null;
            String value = null;
            list.clear();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = jsonObject.getString(key);
                list.add(new FoodBean(-1,key));
                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(value).getAsJsonArray();

                Gson gson = new Gson();
                ArrayList<FoodBean> userBeanList = new ArrayList<>();

                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    FoodBean userBean = gson.fromJson(user, FoodBean.class);
                    list.add(userBean);
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.food_fragment, container, false);
//        module = new HomeModule();
//        mBinding.setHome(module);
        mBinding.setVariable(BR.click, this);
         adapter = new FoodListAdapter(context, list, new BaseOnClick() {
            @Override
            public void onSaveClick(int id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), FoodScendInfoActivity.class);
                intent.putExtra("foodId", id+"");
                startActivity(intent);
            }
        });
        mBinding.setAdapter(adapter);
        View view = mBinding.getRoot();
        Bundle bundle =  getArguments();
        if(bundle != null){
            memberId = bundle.getString("data","");
        }

        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(context);
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, 0);
        mBinding.ptr.setHeaderView(header);
        // mPtrFrame.setPinContent(true);//刷新时，保持内容不动，仅头部下移,默认,false
        mBinding.ptr.addPtrUIHandler(header);
        //mPtrFrame.setKeepHeaderWhenRefresh(true);//刷新时保持头部的显示，默认为true
        //mPtrFrame.disableWhenHorizontalMove(true);//如果是ViewPager，设置为true，会解决ViewPager滑动冲突问题。
        mBinding.ptr.setPtrHandler(new PtrHandler() {

            //需要加载数据时触发
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mBinding.ptr.refreshComplete();
                getData();

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
        myApp = (MyApp) getActivity().getApplication();
        return view;
    }

    public void changeTime(String time){
        if(!myApp.isVisitor()){
            date = time;
            getData();
        }
    }

    public void changeLisen(TabChangeLisen callBack){
         this.callBack = callBack;
    }

    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.iv_top:
                mBinding.lvFood.smoothScrollToPositionFromTop(0, 0);
                break;
            case R.id.iv_add:
                Intent intent = new Intent(getActivity().getApplicationContext(), CameraActivity.class);
                intent.putExtra(CameraActivity.From, "FoodFragment");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {
        if(!myApp.isVisitor()){
            getData();
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        map.put("page", page + "");
        map.put("limit", limit + "");
        if(!TextUtils.isEmpty(memberId)){
            map.put("memberId", memberId);
        }
        map.put("type", "1");
        if(!date.equals("")){
            map.put("date", date);
        }
        connect.getData(MyUrl.RECORD, map, new ConnectBack() {
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
}
