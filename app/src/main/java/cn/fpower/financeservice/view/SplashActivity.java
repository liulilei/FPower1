package cn.fpower.financeservice.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.view.home.HomeActivity;

/**
 * Created by ll on 2015/12/1.
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
