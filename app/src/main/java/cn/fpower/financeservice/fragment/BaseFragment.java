package cn.fpower.financeservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.me.MeInfoActivity;

/**
 * @author liulilei
 *         fragment的基类
 */
public abstract class BaseFragment extends Fragment  implements View.OnClickListener {

    protected ViewGroup mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = onCreateView(inflater, savedInstanceState);
        } else if (mRoot.getParent() != null) {
            ((ViewGroup) mRoot.getParent()).removeView(mRoot);
        }
        ViewUtils.inject(this, mRoot);
        return mRoot;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRoot = null;
    }

    protected abstract ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    protected void initView() {
    }

    protected void initData() {
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public boolean isLogin(){
        if (!FSApplication.getInstance().isLogin()) {
            if (FSApplication.getInstance().getUserInfo() != null) {
                IntentUtils.startActivity(getActivity(), MeInfoActivity.class);
            }else{
                IntentUtils.startActivity(getActivity(), LoginCheckActivity.class);
            }
            return false;
        }
        return true;
    }
}
