package com.fn.healfie.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fn.healfie.BR;
import com.fn.healfie.R;
import com.fn.healfie.adapter.ContactListAdapter;
import com.fn.healfie.adapter.LoginComponent;
import com.fn.healfie.adapter.MessageListAdapter;
import com.fn.healfie.adapter.ViewPagerFragmentAdapter;
import com.fn.healfie.component.camera.CameraActivity;
import com.fn.healfie.connect.MyConnect;
import com.fn.healfie.consts.MyUrl;
import com.fn.healfie.consts.PrefeKey;
import com.fn.healfie.contact.AddContactActivity;
import com.fn.healfie.contact.MessageActivity;
import com.fn.healfie.databinding.ContactFragmentBinding;
import com.fn.healfie.event.DeleteContactEvent;
import com.fn.healfie.event.EditContactEvent;
import com.fn.healfie.interfaces.BaseOnClick;
import com.fn.healfie.interfaces.ConnectBack;
import com.fn.healfie.interfaces.ConnectLoginBack;
import com.fn.healfie.login.LoginActivity;
import com.fn.healfie.model.ContactListBean;
import com.fn.healfie.model.MessageBean;
import com.fn.healfie.model.MessageListBean;
import com.fn.healfie.model.RegisterBean;
import com.fn.healfie.module.HomeModule;
import com.fn.healfie.utils.JsonUtil;
import com.fn.healfie.utils.PrefeUtil;
import com.fn.healfie.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;


public class ContactFragment extends BaseFragment implements BaseOnClick {

    ContactFragmentBinding mBinding;

    private PtrFrameLayout ptrClassicFrameLayout;
    private ListView messageLv;
    private List<MessageBean> mData;
    private ContactListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false, new LoginComponent(context));
//        module = new HomeModule();
//        module.setSelect(1);
//        module.setFirst("first");
//        module.setImageIdFirst(R.mipmap.ic_food_selected);
//        module.setTime("7月25日");
//        module.setLast("last");
//        module.setImageIdLast(R.mipmap.ic_medicine_normal);
//        mBinding.setHome(module);
        mBinding.setVariable(BR.click, ContactFragment.this);
        View view = mBinding.getRoot();

        messageLv = view.findViewById(R.id.lv_message);
        ptrClassicFrameLayout = view.findViewById(R.id.ptr);

        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(context);
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

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void refreshData(EditContactEvent event) {
        getData();
    }

    @Subscribe
    public void deleteData(DeleteContactEvent event) {
        getData();
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mAdapter = new ContactListAdapter(getActivity(),mData);
        messageLv.setAdapter(mAdapter);

        getData();
    }

    @Override
    public void onSaveClick(int id) {
        switch (id) {
            case R.id.rl_add:
                Intent intent = new Intent(getActivity().getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_message:
                Intent intent2 = new Intent(getActivity().getApplicationContext(), MessageActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void getData() {
        showDialog();
        MyConnect connect = new MyConnect();
        HashMap<String, String> map = new HashMap<>();
        map.put("authorization", PrefeUtil.getString(activity, PrefeKey.TOKEN, ""));
        connect.getData(MyUrl.FRIENDINFO, map, new ConnectBack() {
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

    private void changeData(ContactListBean bean){
        mData.clear();

        if(bean.getItem().getList() != null && bean.getItem().getList().size() > 0){
            mData.addAll(bean.getItem().getList());
        }

        mAdapter.notifyDataSetChanged();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ContactListBean bean = JsonUtil.getBean(msg.obj.toString(), ContactListBean.class);
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
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void error(String json) {
                                hideDialog();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
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
