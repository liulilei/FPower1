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
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.me.AboutUsActivity;
import cn.fpower.financeservice.view.me.AchievementActivity;
import cn.fpower.financeservice.view.me.EditPassActivity;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.me.MeInfoActivity;
import cn.fpower.financeservice.view.me.NormalCheckListActivity;
import cn.fpower.financeservice.view.me.ShopActivity;
import cn.fpower.financeservice.view.me.ShopListActivity;
import cn.fpower.financeservice.view.widget.MeSettingView;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * Created by ll on 2015/11/26.
 */
public class MeFragment extends BaseFragment {

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

    private ProvinceData provinceData;
    private MyAlertDialog dialog;

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
        layout_login_info.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        provinceData = FSApplication.getInstance().getProvinceData();
    }

    @Override
    public void onStart() {
        super.onStart();
        setForUserInfo();
    }

    private void setForUserInfo() {
        if (FSApplication.getInstance().getUserInfo() != null) {
            loginout.setVisibility(View.VISIBLE);
            layout_login_info.setVisibility(View.VISIBLE);
            layout_no_login_info.setVisibility(View.GONE);
            username.setText(FSApplication.getInstance().getUserInfo().getData().getUsername());
            userage.setText(MappingManager.getSex(FSApplication.getInstance().getUserInfo().getData().getSex()));
            String addr = provinceData.getProvinceAddress(
                    FSApplication.getInstance().getUserInfo().getData().getProvince_id(),
                    FSApplication.getInstance().getUserInfo().getData().getCity_id(),
                    FSApplication.getInstance().getUserInfo().getData().getDistrict_id());
            useraddr.setText(addr);
            ImageUtils.displayImageRoundImg(R.mipmap.moren, NetApi.URL_HTTP + FSApplication.getInstance().getUserInfo().getData().getFace(), img_login);
            switch (FSApplication.getInstance().getUserInfo().getData().getTissue_id()) {
                case Constants.Right.NORMAL:
                    checkView.setIconText(R.mipmap.me_store, "我的审核");
                    break;
                case Constants.Right.PROMOTER:
                    checkView.setIconText(R.mipmap.me_store, "我的业绩");
                    break;
                case Constants.Right.SHOP:
                    checkView.setIconText(R.mipmap.me_store, "我的店铺");
                    break;
                case Constants.Right.EMP:
                    checkView.setIconText(R.mipmap.me_store, "我的业绩");
                    break;
            }
        } else {
            layout_no_login_info.setVisibility(View.VISIBLE);
            layout_login_info.setVisibility(View.GONE);
            loginout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_check:
                if (!isLogin()) {
                    return;
                }
                switch (FSApplication.getInstance().getUserInfo().getData().getTissue_id()) {
                    case Constants.Right.NORMAL:
                        startActivity(new Intent(getActivity(), NormalCheckListActivity.class));
                        break;
                    case Constants.Right.PROMOTER:
                        startActivity(new Intent(getActivity(), ShopListActivity.class));
                        break;
                    case Constants.Right.SHOP:
                        startActivity(new Intent(getActivity(), ShopActivity.class));
                        break;
                    case Constants.Right.EMP:
                        startActivity(new Intent(getActivity(), AchievementActivity.class));
                        break;
                }
                break;
            case R.id.fragment_me_up:
                if (!isLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), EditPassActivity.class));
                break;
            case R.id.fragment_me_about:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.loginin:
                startActivity(new Intent(getActivity(), LoginCheckActivity.class));
                break;
            case R.id.layout_login_info:
                if (!isLogin()) {
                    return;
                }
                startActivity(new Intent(getActivity(), MeInfoActivity.class));
                break;
            case R.id.loginout:
                if (dialog == null) {
                    dialog = new MyAlertDialog(getActivity()).setTitle("提示").setMsg("您是否要退出登录")
                            .setRightButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FSApplication.getInstance().setLogincode(0);
                                    FSApplication.getInstance().setUserInfo(null);
                                    SpUtils.putString(getActivity(), Constants.MOBLEE, "");
                                    SpUtils.putString(getActivity(), Constants.PASSWD, "");
                                    setForUserInfo();
                                    ToastUtils.show(getActivity(), "退出登录成功");
                                }
                            }).setLeftButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                }
                dialog.show();
                break;
        }
    }
}
