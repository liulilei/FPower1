package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 登陆
 */
public class LoginCheckActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.view_mobile)
    private ClearEditText view_mobile;

    @ViewInject(R.id.loginin)
    private Button loginin;


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setVisibility(View.GONE);
        title.setText("登录");
        loginin.setOnClickListener(this);
    }


    String mobile;

    private void jump(Class clz) {
        Intent intent = new Intent();
        intent.putExtra("mobile", mobile);
        intent.setClass(this, clz);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginin:
                //登录注册
                // 先是填一个手机号码判断注册还是没注册
                // 注册了的号码显示登录界面 手机号码和密码 还有忘记密码
                // 没注册过的就显示注册界面
                mobile = view_mobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.show(this, "请输入正确的手机号");
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().check_mobile_sole(this, mobile, true, new ManagerStringListener() {
                    @Override
                    public void onSuccess(String data) {
                        jump(LoginActivity.class);
                    }

                    @Override
                    public void onError(String error) {
                        if (error.contains("message")) {
                            try {
                                JSONObject jsonObject = new JSONObject(error);
                                if (jsonObject.has("message")) {
                                    String message = jsonObject.getString("message");
                                    if (message.contains("可以注册")) {
                                        jump(RegisterActivity.class);
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
