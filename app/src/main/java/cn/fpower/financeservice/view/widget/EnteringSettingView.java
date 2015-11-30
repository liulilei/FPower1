package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.fpower.financeservice.R;

public class EnteringSettingView extends RelativeLayout {

    private TextView title;
    private TextView value;

    public EnteringSettingView(Context context) {
        this(context, null);
    }

    public EnteringSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.ui_add_info, this);
        title = (TextView) findViewById(R.id.title);
        value = (TextView) findViewById(R.id.value);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.me_setting);
        String txtTitle = mTypedArray.getString(R.styleable.me_setting_txtName);
        if (txtTitle != null) {
            title.setText(txtTitle);
        }
        mTypedArray.recycle();
    }

    public void setValue(String value){
        title.setText(value);
    }
}
