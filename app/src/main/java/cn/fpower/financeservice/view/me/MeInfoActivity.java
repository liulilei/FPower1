package cn.fpower.financeservice.view.me;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;

/**
 * 登陆
 */
public class MeInfoActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.submit)
    private Button submit;

    @ViewInject(R.id.img_right)
    private ImageView img_right;

    @Override
    protected int initLayout() {
        return R.layout.activity_userinfo;
    }



    @Override
    protected void initView() {
        super.initView();
        back.setVisibility(View.GONE);
        title.setText("完善信息");
        submit.setOnClickListener(this);
        img_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:

                break;
            case R.id.img_right:
                if (mDialog == null) {
                    mDialog = createDialog("选择图片");
                }
                mDialog.show();
                break;
        }
    }

    private Dialog mDialog;

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] items = getResources().getStringArray(R.array.select_dialog_items);
                        ToastUtils.show(act, items[which]);
                    }
                })
                .create();
    }
}
