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
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.me.LoginActivity;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.me.MeStoreActivity;
import cn.fpower.financeservice.view.widget.MeSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

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
    }

    @Override
    protected void initData() {
        super.initData();
        //TODO
       //登陆不登陆状态
        layout_no_login_info.setVisibility(View.VISIBLE);
        layout_login_info.setVisibility(View.GONE);
        checkView.setIconText(R.mipmap.me_store,"店铺管理");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_check:
               startActivity(new Intent(getActivity(),MeStoreActivity.class));
                break;
            case R.id.fragment_me_up:
                ToastUtils.show(getActivity(),"fragment_me_up");
                break;
            case R.id.fragment_me_about:
                ToastUtils.show(getActivity(),"fragment_me_about");
                break;
            case R.id.loginin:
                startActivity(new Intent(getActivity(), LoginCheckActivity.class));
                break;
        }
    }
}
