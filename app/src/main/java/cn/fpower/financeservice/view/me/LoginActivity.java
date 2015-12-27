package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.view_mobile)
    private ClearEditText view_pwd;

    @ViewInject(R.id.loginin)
    private Button loginin;

    @ViewInject(R.id.des)
    private TextView des;

    private UserInfo userInfo;

    private Gson gson;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        des.setVisibility(View.GONE);
        back.setOnClickListener(this);
        title.setText("登录");
        view_pwd.setHint(R.string.input_pwd);
        loginin.setText(R.string.confirm);
        loginin.setOnClickListener(this);
        view_pwd.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    }


    @Override
    protected void initData() {
        super.initData();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mobile = b.getString("mobile");
        }
    }

    String mobile;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.loginin:
                String passwd = view_pwd.getText().toString();
                if (TextUtils.isEmpty(passwd)) {
                    ToastUtils.show(this, R.string.input_pwd);
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().login(this, mobile, passwd, true,
                        UserInfo.class, new ManagerDataListener() {

                            @Override
                            public void onSuccess(Object data) {
                                userInfo = (UserInfo) data;
                                FSApplication.getInstance().setUserInfo(userInfo);
                                FSApplication.getInstance().setIsLogin(true);
                                SpUtils.putString(LoginActivity.this, Constants.MOBLEE, userInfo.getData().getMobile());
                                SpUtils.putString(LoginActivity.this, Constants.PASSWD, view_pwd.getText().toString());
                                IntentUtils.startActivity(LoginActivity.this, HomeActivity.class);
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
                                                //  //必须完善信息才算登陆
                                                //FSApplication.getInstance().setIsLogin(true);
                                                SpUtils.putString(LoginActivity.this, Constants.MOBLEE, userInfo.getData().getMobile());
                                                SpUtils.putString(LoginActivity.this, Constants.PASSWD, view_pwd.getText().toString());
                                                IntentUtils.startActivity(act, HomeActivity.class);
                                                finish();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                break;
        }
    }
}
