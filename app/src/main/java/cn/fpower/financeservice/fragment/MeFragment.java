package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.me.NormalCheckListActivity;
import cn.fpower.financeservice.view.me.MeInfoActivity;
import cn.fpower.financeservice.view.me.PromotionResultActivity;
import cn.fpower.financeservice.view.widget.MeSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class MeFragment extends BaseFragment{

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.layout_no_login_info)
    private RelativeLayout layout_no_login_info;

    @ViewInject(R.id.layout_login_info)
    private RelativeLayout layout_login_info;

    @ViewInject(R.id.fragment_me_check)
    private MeSettingView checkView;

    @ViewInject(R.id.fragment_me_up)
    private MeSettingView modView;

    @ViewInject(R.id.fragment_me_about)
    private MeSettingView usView;

    @ViewInject(R.id.loginin)
    private Button login;

    @ViewInject(R.id.loginout)
    private Button loginout;


    @ViewInject(R.id.username)
    private TextView username;

    @ViewInject(R.id.userage)
    private TextView userage;

    @ViewInject(R.id.useraddr)
    private TextView useraddr;

    @ViewInject(R.id.img_login)
    private ImageView img_login;


    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_me, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.me));
        checkView.setOnClickListener(this);
        modView.setOnClickListener(this);
        usView.setOnClickListener(this);
        login.setOnClickListener(this);
        loginout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //TODO
        //登陆不登陆状态
    }

    @Override
    public void onStart() {
        super.onStart();
        setForUserInfo();
    }

    private int userRight = 1;

    private void setForUserInfo() {
        if (FSApplication.getInstance().isLogin()) {
            layout_login_info.setVisibility(View.VISIBLE);
            layout_no_login_info.setVisibility(View.GONE);
            username.setText(FSApplication.getInstance().getUserInfo().getData().getUsername());
            userage.setText(FSApplication.getInstance().getUserInfo().getData().getBirthday());
            useraddr.setText(FSApplication.getInstance().getUserInfo().getData().getProvince_id()+"");
            ImageUtils.displayImageRoundImg(R.mipmap.ad1, NetApi.URL+FSApplication.getInstance().getUserInfo().getData().getFace(), img_login);
        } else {
            layout_no_login_info.setVisibility(View.VISIBLE);
            layout_login_info.setVisibility(View.GONE);
        }
        //店铺列表 是推广员角色登录 我的业绩点击就是店铺列表
        switch (userRight) {
            case 1:
                checkView.setIconText(R.mipmap.me_store, "我的审核");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_check:
                startActivity(new Intent(getActivity(), NormalCheckListActivity.class));
                break;
            case R.id.fragment_me_up:
                startActivity(new Intent(getActivity(), MeInfoActivity.class));
                break;
            case R.id.fragment_me_about:
                 startActivity(new Intent(getActivity(), PromotionResultActivity.class));
                break;
            case R.id.loginin:
                startActivity(new Intent(getActivity(), LoginCheckActivity.class));
                break;
            case R.id.loginout:
                FSApplication.getInstance().setIsLogin(false);
                FSApplication.getInstance().setUserInfo(null);
                SpUtils.putString(getActivity(), Constants.MOBLEE, "");
                SpUtils.putString(getActivity(), Constants.PASSWD, "");
                setForUserInfo();
                ToastUtils.show(getActivity(), "退出登录成功");
                break;
        }
    }
}
