package cn.fpower.financeservice.view.me;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.view.BaseActivity;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.version)
    private TextView version;

    @Override
    protected int initLayout() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText("关于我们");
        version.setText("微妙金服v"+FSApplication.getInstance().APP_VERSION_NAME);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
        }
    }
}
