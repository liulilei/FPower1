package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.view.View;

import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.TelephonyUtils;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * 打电话事件
 */
public class CallPhoneClickListener implements View.OnClickListener {

    private String phone;
    private MyAlertDialog dialog;
    private Context mContext;

    public CallPhoneClickListener(String phone,MyAlertDialog dialog,Context context) {
        this.phone = phone;
        this.dialog=dialog;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        dialog.setTitle(StringUtils.getPhoneFormat(phone)).setRightButton("呼叫", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyUtils.callPhone(mContext, phone);
            }
        }).setLeftButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
