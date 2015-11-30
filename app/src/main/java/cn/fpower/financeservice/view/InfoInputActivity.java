package cn.fpower.financeservice.view;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;

public class InfoInputActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_inpput;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle b=getIntent().getExtras();
        if(b!=null){
            title.setText("输入"+b.getString("title"));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_stable, R.anim.out_push_left_to_right);
    }

}
