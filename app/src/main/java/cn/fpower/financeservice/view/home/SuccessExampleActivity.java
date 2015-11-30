package cn.fpower.financeservice.view.home;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.SuccessExampleAdapter;
import cn.fpower.financeservice.mode.SuccessExampleInfo;
import cn.fpower.financeservice.mode.SuccessExampleInfo.SuccessExample;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * Created by ll on 2015/11/30.
 */
public class SuccessExampleActivity extends BaseActivity implements View.OnClickListener, RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.activity_success_example_rlv)
    private RefreshListView successExampleRlv;

    private SuccessExampleAdapter successExampleAdapter;

    private List<SuccessExample> successExampleList;

    private int count = 1;

    @Override
    protected int initLayout() {
        return R.layout.activity_success_example;
    }

    @Override
    protected void initView() {
        title.setText("成功案例");
        back.setOnClickListener(this);
        successExampleRlv.setOnRefreshListener(this);
        successExampleRlv.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        successExampleList = addData();
        successExampleAdapter = new SuccessExampleAdapter(this, successExampleList);
        successExampleRlv.setAdapter(successExampleAdapter);
        successExampleRlv.showFooterResult(true);
    }

    public List<SuccessExample> addData() {
        List<SuccessExample> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new SuccessExampleInfo().new SuccessExample());
        }
        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
        }
    }

    @Override
    public void OnRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count = 1;
                        successExampleRlv.onRefreshComplete();
                        successExampleAdapter.refresh(addData());
                        successExampleRlv.showFooterResult(true);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onLoadMore() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        successExampleAdapter.addData(addData());
                        if (count < 3) {
                            successExampleRlv.showFooterResult(true);
                            count++;
                        } else {
                            successExampleRlv.showFooterResult(false);
                        }
                        successExampleRlv.onLoadComplete();
                    }
                });
            }
        }).start();
    }
}
