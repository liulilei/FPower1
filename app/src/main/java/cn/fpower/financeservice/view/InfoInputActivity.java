package cn.fpower.financeservice.view;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.view.annotation.ViewInject;


import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.KeyBoardUtils;
import cn.fpower.financeservice.view.widget.ClearEditText;

public class InfoInputActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.title_bar_save)
    private TextView save;

    @ViewInject(R.id.inputinfo)
    private ClearEditText inputinfo;

    public InfoInputActivity() {
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_info_inpput;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        Bundle b=getIntent().getExtras();
        if(b!=null) {
            title.setText("输入" + b.getString("title"));
            int type=b.getInt("inputType",0);
            if(type!=0) {
                inputinfo.setInputType(type);
                if(type==EditorInfo.TYPE_CLASS_NUMBER){
                    inputinfo.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
                            11) });
                }
            }
            inputinfo.setText(b.getString("value"));
        }
        save.setText("完成");
        save.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.openKeybord(inputinfo, InfoInputActivity.this);
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;
            case R.id.title_bar_save:
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", inputinfo.getText().toString());
                //设置返回数据
                this.setResult(0, intent);
                //关闭Activity
                this.finish();
                break;
        }
    }

}
