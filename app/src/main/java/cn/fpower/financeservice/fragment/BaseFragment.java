package cn.fpower.financeservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

/**
 * @author liulilei
 *         fragment的基类
 */
public abstract class BaseFragment extends Fragment {

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
}
