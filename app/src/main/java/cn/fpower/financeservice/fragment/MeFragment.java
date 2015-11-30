package cn.fpower.financeservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.widget.MeSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

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
        checkView.setOnClickListener(this);
        modView.setOnClickListener(this);
        usView.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_check:
                ToastUtils.show(getActivity(),"fragment_me_check");
                break;
            case R.id.fragment_me_up:
                ToastUtils.show(getActivity(),"fragment_me_up");
                break;
            case R.id.fragment_me_about:
                ToastUtils.show(getActivity(),"fragment_me_about");
                break;
            case R.id.loginin:
                ToastUtils.show(getActivity(),"loginin");
                break;
        }
    }
}
