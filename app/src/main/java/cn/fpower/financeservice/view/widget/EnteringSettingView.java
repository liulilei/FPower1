package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.fpower.financeservice.R;

public class EnteringSettingView extends RelativeLayout {

    private TextView titleName;
    private TextView valueName;
    private ImageView img_go;
    private String key="";
    private String defaultValue= "请填写" ;

    public EnteringSettingView(Context context) {
        this(context, null);
    }

    public EnteringSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.ui_add_info, this);
        titleName = (TextView) findViewById(R.id.title);
        valueName = (TextView) findViewById(R.id.value);
        img_go=(ImageView) findViewById(R.id.img_go);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.me_setting);
        String txtTitle = mTypedArray.getString(R.styleable.me_setting_txtName);
        if (!TextUtils.isEmpty(txtTitle)) {
            titleName.setText(txtTitle);
        }
        boolean isHideRight =mTypedArray.getBoolean(R.styleable.me_setting_hideRight, false);
        if(isHideRight) {
            img_go.setVisibility(View.GONE);
        }else {
            String value = mTypedArray.getString(R.styleable.me_setting_defaultValue);
            if (!TextUtils.isEmpty(value)) {
                defaultValue = value;
            }
            valueName.setText(defaultValue);
        }
        mTypedArray.recycle();
    }

    public String getTitle(){
        return titleName.getText().toString();
    }

    public void setValue(String value){
        if(TextUtils.isEmpty(value)){
            valueName.setText(defaultValue);
        }else {
            this.key=value;
            valueName.setText(value);
        }
    }

    public void setValue(String key,String value){
        this.key=key;
        valueName.setText(value);
    }

    public void clear(){
        this.key="";
        valueName.setText(defaultValue);
    }

    public String getKey(){
        return key;
    }

}
