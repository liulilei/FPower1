package cn.fpower.financeservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import cn.fpower.financeservice.R;


public class LoadingCancelDialog extends Dialog {

    private TextView tv_load;
    private String mText;

    public LoadingCancelDialog(Context context, String text) {
        super(context, R.style.loading_dialog);
        mText = text;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        tv_load = (TextView) findViewById(R.id.tv_loading);
        tv_load.setText(mText);
    }

    public void setText(String text) {
        mText = text;
        tv_load.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
