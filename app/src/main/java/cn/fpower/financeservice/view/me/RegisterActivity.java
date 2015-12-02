package cn.fpower.financeservice.view.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

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
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

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

    @Override
    protected int initLayout() {
        return R.layout.activity_reg;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.register));
        confirm.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    String mobile;

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
            case R.id.confirm:
                break;
            case R.id.send:
                FinanceManagerControl.getFinanceServiceManager().get_code(this, mobile, 1, true, new ManagerStringListener() {

                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
                break;
        }
    }
}
