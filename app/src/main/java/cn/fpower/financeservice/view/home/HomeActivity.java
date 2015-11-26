package cn.fpower.financeservice.view.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.fragment.EnteringFragment;
import cn.fpower.financeservice.fragment.HomeFragment;
import cn.fpower.financeservice.fragment.MeFragment;
import cn.fpower.financeservice.fragment.ProgressFragment;
import cn.fpower.financeservice.view.BaseActivity;

/**
 * Created by ll on 2015/11/26.
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.home_rp)
    private RadioGroup homeRg;

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;

    private ProgressFragment progressFragment;

    private EnteringFragment enteringFragment;

    private MeFragment meFragment;

    private FragmentTransaction transaction;

    private Fragment currentFragment;

    private Fragment nowFragment;

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
                nowFragment = homeFragment;
                break;
            case R.id.activity_home_progress_rbt:
                nowFragment = progressFragment;
                break;
            case R.id.activity_home_entering_rbt:
                nowFragment = enteringFragment;
                break;
            case R.id.activity_home_me_rbt:
                nowFragment = meFragment;
                break;
        }
        transaction = fragmentManager.beginTransaction();
        transaction.hide(currentFragment);
        if (!nowFragment.isAdded()) {
            transaction.add(R.id.activity_home_fragment, nowFragment);
        }
        transaction.show(nowFragment);
        transaction.commit();
        currentFragment = nowFragment;
    }
}
