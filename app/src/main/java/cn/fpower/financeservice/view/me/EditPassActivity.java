package cn.fpower.financeservice.view.me;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 修改密码
 */
public class EditPassActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.view_old_pass)
    private ClearEditText view_old_pass;

    @ViewInject(R.id.view_new_pass)
    private ClearEditText view_new_pass;

    @ViewInject(R.id.submit)
    private Button submit;

    private UserInfo userInfo;

    @Override
    protected int initLayout() {
        return R.layout.activity_edit_pass;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText("修改密码");
        submit.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        super.initData();
        if (FSApplication.getInstance().getUserInfo() != null) {
            userId = FSApplication.getInstance().getUserInfo().getData().getId() + "";
        }
    }

    String userId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.submit:
                String old_pass = view_old_pass.getText().toString();
                if (TextUtils.isEmpty(old_pass)) {
                    ToastUtils.show(this, R.string.input_old_pwd);
                    return;
                }
                String new_pass = view_new_pass.getText().toString();
                if (TextUtils.isEmpty(new_pass)) {
                    ToastUtils.show(this, R.string.input_new_pwd);
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().user_info_edit_passwd(this, userId, new_pass, old_pass, true,
                        new ManagerStringListener() {

                            @Override
                            public void onSuccess(String data) {
                                SpUtils.putString(act, Constants.PASSWD, view_new_pass.getText().toString());
                                finish();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                break;
        }
    }
}
