package cn.fpower.financeservice.view;

import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.LocationUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.view.home.HomeActivity;

/**
 * Created by ll on 2015/12/1.
 */
public class SplashActivity extends BaseActivity {

    private UserInfo userInfo;

    private Gson gson;

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
    protected void initData() {
        String mobile = SpUtils.getString(this, Constants.MOBLEE, "");
        String passWd = SpUtils.getString(this, Constants.PASSWD, "");
        LocationUtils.getCNBylocation(this);
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(passWd)) {
            FinanceManagerControl.getFinanceServiceManager().login(this, mobile, passWd, false, UserInfo.class,
                    new ManagerDataListener() {
                        @Override
                        public void onSuccess(Object data) {
                            userInfo = (UserInfo) data;
                            FSApplication.getInstance().setUserInfo(userInfo);
                            FSApplication.getInstance().setIsLogin(true);
                            IntentUtils.startActivity(SplashActivity.this, HomeActivity.class);
                            finish();
                        }

                        @Override
                        public void onError(String error) {
                            if (error.contains("code")) {
                                try {
                                    JSONObject jsonObject = new JSONObject(error);
                                    if (jsonObject.has("code")) {
                                        int code = jsonObject.getInt("code");
                                        if (code == 201) {
                                            gson = new Gson();
                                            userInfo = gson.fromJson(error, UserInfo.class);
                                            FSApplication.getInstance().setUserInfo(userInfo);
                                            FSApplication.getInstance().setIsLogin(true);
                                            IntentUtils.startActivity(SplashActivity.this, HomeActivity.class);
                                            finish();
                                        } else {
                                            IntentUtils.startActivity(SplashActivity.this, HomeActivity.class);
                                            finish();

                                        }
                                    }
                                } catch (JSONException e) {
                                    IntentUtils.startActivity(SplashActivity.this, HomeActivity.class);
                                    finish();
                                    e.printStackTrace();
                                }
                            } else {
                                IntentUtils.startActivity(SplashActivity.this, HomeActivity.class);
                                finish();
                            }
                        }
                    });
        } else {
            handler.sendEmptyMessageDelayed(0, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {

    }
}
