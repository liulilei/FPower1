package cn.fpower.financeservice.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.utils.CountDownUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 登陆
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    String mobile;
    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.code)
    private ClearEditText code;

    @ViewInject(R.id.pass)
    private ClearEditText pass;

    @ViewInject(R.id.confirm)
    private Button confirm;

    @ViewInject(R.id.send)
    private Button send;

    /** 倒计时 **/
    private MyTimerUtil timer;

    private class MyTimerUtil extends CountDownUtils {

        public MyTimerUtil(long totalTime, long downValue) {
            super(totalTime, downValue);
        }
        @Override
        public void onTick(long remain) {
            send.setEnabled(false);
            send.setText("剩余"+ remain / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            send.setText(R.string.send_code);
            send.setEnabled(true);
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_reg;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText(getString(R.string.register));
        confirm.setOnClickListener(this);
        send.setOnClickListener(this);
        timer = new MyTimerUtil(60000, 1000);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mobile = b.getString("mobile");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.confirm:
                String verify=code.getText().toString().trim();
                if(TextUtils.isEmpty(verify)){
                    ToastUtils.show(this,R.string.code);
                    return;
                }
                if (verify.length()<6) {
                    ToastUtils.show(this, R.string.verify_code);
                    return;
                }
                String passwd=pass.getText().toString();
                if(TextUtils.isEmpty(passwd)){
                    ToastUtils.show(this,R.string.input_pwd);
                    return;
                }
                if(passwd.length() < 6){
                    ToastUtils.show(this,R.string.verify_pass);
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().register(this, mobile,verify, passwd, true, new ManagerStringListener() {

                    @Override
                    public void onSuccess(String result) {
                        Intent intent = new Intent();
                        intent.putExtra("mobile", mobile);
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
            case R.id.send:
                FinanceManagerControl.getFinanceServiceManager().get_code(this, mobile, 1, true, new ManagerStringListener() {

                    @Override
                    public void onSuccess(String result) {
                        timer.start();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
        }
    }
}
