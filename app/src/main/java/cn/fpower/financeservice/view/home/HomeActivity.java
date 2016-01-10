package cn.fpower.financeservice.view.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.fragment.EnteringFragment;
import cn.fpower.financeservice.fragment.HomeFragment;
import cn.fpower.financeservice.fragment.MeFragment;
import cn.fpower.financeservice.fragment.ProgressFragment;
import cn.fpower.financeservice.fragment.PromoterEnteringFragment;
import cn.fpower.financeservice.manager.netmanager.DownListener;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.Version;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.me.MeInfoActivity;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * 18310705012 123456
 */
public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.home_rp)
    private RadioGroup homeRg;

    @ViewInject(R.id.activity_home_entering_rbt)
    private RadioButton enteringRbt;

    @ViewInject(R.id.activity_home_home_rbt)
    private RadioButton homeRbt;


    @ViewInject(R.id.activity_home_me_rbt)
    private RadioButton meRbt;


    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;

    private ProgressFragment progressFragment;

    private EnteringFragment enteringFragment;

    private PromoterEnteringFragment promoterEnteringFragment;

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
        promoterEnteringFragment = new PromoterEnteringFragment();
        meFragment = new MeFragment();
        fragmentManager.beginTransaction().add(R.id.activity_home_fragment, homeFragment).commit();
        currentFragment = homeFragment;
        homeRg.setOnCheckedChangeListener(this);
        checkVersion();
    }


    private void checkVersion() {
        FinanceManagerControl.getFinanceServiceManager().version(this, Version.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                final Version info = (Version) data;
                if (FSApplication.APP_VERSION_CODE > info.getData().version) {

                    new MyAlertDialog(HomeActivity.this).setTitle("提示").setMsg(info.getData().message)
                            .setRightButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateApk(info.getData().url);
                                }
                            }).setLeftButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (FSApplication.getInstance().getLogincode() == 201) {
                                startActivity(new Intent(act, MeInfoActivity.class));
                            }
                        }
                    }).show();

                }
            }

            @Override
            public void onError(String error) {
                if (FSApplication.getInstance().getLogincode() == 201) {
                    startActivity(new Intent(act, MeInfoActivity.class));
                }
            }
        });
    }

    ProgressDialog mProgressDialog;

    void updateApk(String url) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("下载更新");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMax(100);
        mProgressDialog.show();
        FinanceManagerControl.getFinanceServiceManager().update(this, url, Constants.APK_PATH, new DownListener() {
            @Override
            public void onSuccess(File result) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                //安装
                IntentUtils.installAPK(HomeActivity.this, result);
            }

            @Override
            public void onLoading(long total, long current, int process) {
                mProgressDialog.setProgress(process);
            }

            @Override
            public void onError(String error) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (FSApplication.getInstance().getLogincode() == 201) {
                    startActivity(new Intent(act, MeInfoActivity.class));
                }
            }
        });
    }

    private boolean isProgressFragmentFirst = true;
    private boolean isEnteringFragmentFirst = true;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_home_home_rbt:
                showFragment(homeFragment);
                break;
            case R.id.activity_home_progress_rbt:
                if (!isLogin()) {
                    if (currentFragment == homeFragment) {
                        homeRbt.setChecked(true);
                    } else {
                        meRbt.setChecked(true);
                    }
                    return;
                }
                if (isProgressFragmentFirst) {
                    isProgressFragmentFirst = false;
                } else {
                    progressFragment.initList();
                }
                showFragment(progressFragment);
                break;
            case R.id.activity_home_entering_rbt:
                if (!isLogin()) {
                    if (currentFragment == homeFragment) {
                        homeRbt.setChecked(true);
                    } else {
                        meRbt.setChecked(true);
                    }
                    return;
                }
                switch (FSApplication.getInstance().getUserInfo().getData().getTissue_id()) {
                    case Constants.Right.PROMOTER:
                        showFragment(promoterEnteringFragment);
                        break;
                    default:
                        if (isEnteringFragmentFirst) {
                            isEnteringFragmentFirst = false;
                        } else {
                            enteringFragment.initQudao();
                        }
                        showFragment(enteringFragment);
                        break;
                }
                break;
            case R.id.activity_home_me_rbt:
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