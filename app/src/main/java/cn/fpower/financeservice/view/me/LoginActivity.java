package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.os.Bundle;
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
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
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

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        des.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        title.setText("登录");
        view_pwd.setHint(R.string.input_pwd);
        loginin.setText(R.string.confirm);
        loginin.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        super.initData();
        Bundle b=getIntent().getExtras();
        if(b!=null) {
            mobile=b.getString("mobile");
        }
    }

    String mobile;

    private void jump(Class clz) {
        Intent intent = new Intent();
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginin:
                String pwd = view_pwd.getText().toString();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.show(this, R.string.input_pwd);
                    return;
                }

                break;
        }
    }
}
