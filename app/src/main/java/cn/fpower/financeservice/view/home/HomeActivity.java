package cn.fpower.financeservice.view.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.fragment.EnteringFragment;
import cn.fpower.financeservice.fragment.HomeFragment;
import cn.fpower.financeservice.fragment.MeFragment;
import cn.fpower.financeservice.fragment.ProgressFragment;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.me.LoginActivity;
import cn.fpower.financeservice.view.me.LoginCheckActivity;

/**
 * Created by ll on 2015/11/26.
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.home_rp)
    private RadioGroup homeRg;

    @ViewInject(R.id.activity_home_entering_rbt)
    private RadioButton enteringRbt;

    @ViewInject(R.id.activity_home_home_rbt)
    private RadioButton homeRbt;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;

    private ProgressFragment progressFragment;

    private EnteringFragment enteringFragment;

    private MeFragment meFragment;

    private FragmentTransaction transaction;

    private Fragment currentFragment;

    private Fragment nowFragment;

    private long mExitTime;

    @Override
    protected int initLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        progressFragment = new ProgressFragment();
        enteringFragment = new EnteringFragment();
        meFragment = new MeFragment();
        fragmentManager.beginTransaction().add(R.id.activity_home_fragment, homeFragment).commit();
        currentFragment = homeFragment;

        homeRg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_home_home_rbt:
                showFragment(homeFragment);
                break;
            case R.id.activity_home_progress_rbt:
                if (!FSApplication.getInstance().isLogin()) {
                    IntentUtils.startActivity(this, LoginCheckActivity.class);
                    homeRbt.setChecked(true);
                    return;
                }
                showFragment(progressFragment);
                break;
            case R.id.activity_home_entering_rbt:
                if (!FSApplication.getInstance().isLogin()) {
                    IntentUtils.startActivity(this, LoginCheckActivity.class);
                    homeRbt.setChecked(true);
                    return;
                }
                showFragment(enteringFragment);
                break;
            case R.id.activity_home_me_rbt:
                /*if (!FSApplication.getInstance().isLogin()) {
                    IntentUtils.startActivity(this, LoginCheckActivity.class);
                    homeRbt.setChecked(true);
                    return;
                }*/
                showFragment(meFragment);
                break;
        }
    }

    public void showFragment(Fragment fragment) {
        nowFragment = fragment;
        transaction = fragmentManager.beginTransaction();
        transaction.hide(currentFragment);
        if (!nowFragment.isAdded()) {
            transaction.add(R.id.activity_home_fragment, nowFragment);
        }
        transaction.show(nowFragment);
        transaction.commit();
        currentFragment = nowFragment;
    }

    public void selectEnteringFragment() {
        enteringRbt.setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.show(this, "再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
