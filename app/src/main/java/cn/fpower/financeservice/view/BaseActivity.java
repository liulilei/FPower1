package cn.fpower.financeservice.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.DialogUtils;
import cn.fpower.financeservice.view.home.HomeActivity;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (!(this instanceof HomeActivity)) {
            overridePendingTransition(R.anim.in_push_right_to_left, R.anim.in_stable);
        }
        setContentView(initLayout());
        ViewUtils.inject(this);
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initLogin() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (DialogUtils.isDialogShowing()) {
                DialogUtils.dismissProgessDirectly();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
        if (!(this instanceof HomeActivity)) {
            overridePendingTransition(R.anim.in_stable, R.anim.out_push_left_to_right);
        }
    }
}
