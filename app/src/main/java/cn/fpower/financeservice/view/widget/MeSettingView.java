package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.fpower.financeservice.R;

public class MeSettingView extends RelativeLayout {

    private ImageView img_left;
    private TextView txt_name;
    private View line;

    public MeSettingView(Context context) {
        this(context, null);
    }

    public MeSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.ui_setting_me, this);
        img_left = (ImageView) findViewById(R.id.img_left);
        txt_name = (TextView) findViewById(R.id.name);
        line = findViewById(R.id.line);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.me_setting);
        String textName = mTypedArray.getString(R.styleable.me_setting_txtName);
        if (textName != null) {
            txt_name.setText(textName);
        }
        Drawable imgRes = mTypedArray.getDrawable(R.styleable.me_setting_imgRes);
        if (imgRes != null) {
            img_left.setImageDrawable(imgRes);
        }
        boolean showLine = mTypedArray.getBoolean(R.styleable.me_setting_showLine, true);
        line.setVisibility(showLine ? View.VISIBLE : View.INVISIBLE);
        mTypedArray.recycle();
    }

    public void setIconText(int imgId,String text){
        img_left.setImageResource(imgId);
        txt_name.setText(text);
    }
}
