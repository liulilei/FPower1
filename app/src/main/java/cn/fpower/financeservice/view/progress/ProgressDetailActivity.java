package cn.fpower.financeservice.view.progress;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.view.BaseActivity;

/**
 * Created by ll on 2015/12/2.
 */
public class ProgressDetailActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @Override
    protected int initLayout() {
        return R.layout.activity_progress_detail;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("进度");
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
