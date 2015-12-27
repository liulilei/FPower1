package cn.fpower.financeservice.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.utils.DialogUtils;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.me.MeInfoActivity;

public abstract class BaseActivity extends FragmentActivity  implements View.OnClickListener{

    protected BaseActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (!(this instanceof HomeActivity || this instanceof SplashActivity)) {
            overridePendingTransition(R.anim.in_push_right_to_left, R.anim.in_stable);
        }
        setContentView(initLayout());
        act=this;
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
    protected void onResume() {
        super.onResume();

        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
        if (!(this instanceof HomeActivity || this instanceof SplashActivity)){
            overridePendingTransition(R.anim.in_stable, R.anim.out_push_left_to_right);
        }
    }

    public boolean isLogin(){
        if (!FSApplication.getInstance().isLogin()) {
            if (FSApplication.getInstance().getUserInfo() != null) {
                IntentUtils.startActivity(act, MeInfoActivity.class);
            }else{
                IntentUtils.startActivity(this, LoginCheckActivity.class);
            }
            return false;
        }
        return true;
    }

}
