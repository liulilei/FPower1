package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 找回密码
 */
public class ForgetMobileActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.view_mobile)
    private ClearEditText view_mobile;

    @ViewInject(R.id.next)
    private Button next;

    @Override
    protected int initLayout() {
        return R.layout.activity_forget_mobile;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText("忘记密码");
        next.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.next:
                String mobile = view_mobile.getText().toString();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.show(this, R.string.input_mobile);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("mobile", mobile);
                intent.setClass(this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
