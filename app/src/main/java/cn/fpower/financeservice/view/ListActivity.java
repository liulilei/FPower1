package cn.fpower.financeservice.view;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.ProgressFragmentAdapter;
import cn.fpower.financeservice.view.widget.RefreshListView;

public class ListActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    @Override
    protected int initLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("我的审核");
        progressRlv.setAdapter(new ProgressFragmentAdapter(this, null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;
        }
    }
}
