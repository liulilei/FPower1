package cn.fpower.financeservice.view.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.SuccessExampleAdapter;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.CaseListInfo;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.utils.ToastUtils;
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

    private List<DataInfo> exampleList;

    private List<DataInfo> refreshExampleList;

    private List<DataInfo> loadMoreExampleList;

    private int now_page = 1;

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
        FinanceManagerControl.getFinanceServiceManager().case_list(this, 0, now_page, true, CaseListInfo.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                exampleList = ((CaseListInfo) data).getData().getCase_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(SuccessExampleActivity.this, "没有数据");
                    successExampleRlv.showFooterResult(false);
                    return;
                }
                successExampleAdapter = new SuccessExampleAdapter(SuccessExampleActivity.this, exampleList);
                successExampleRlv.setAdapter(successExampleAdapter);
                successExampleRlv.showFooterResult(now_page <=  (((CaseListInfo) data).getData().getCase_total() / 10));
            }

            @Override
            public void onError(String error) {
                successExampleRlv.setLoadEnable(false);
            }
        });
    }

    public List<DataInfo> addData() {
        List<DataInfo> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new DataInfo());
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
        FinanceManagerControl.getFinanceServiceManager().case_list(this, 0, 1, false, CaseListInfo.class,
                new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        now_page = 1;
                        successExampleRlv.onRefreshComplete();
                        refreshExampleList = ((CaseListInfo) data).getData().getCase_list();
                        if (refreshExampleList == null || refreshExampleList.size() == 0) {
                            return;
                        }
                        if (successExampleAdapter == null) {
                            successExampleAdapter = new SuccessExampleAdapter(SuccessExampleActivity.this, refreshExampleList);
                            successExampleRlv.setAdapter(successExampleAdapter);
                        } else {
                            successExampleAdapter.refresh(refreshExampleList);
                        }
                        successExampleRlv.showFooterResult(now_page <=  (((CaseListInfo) data).getData().getCase_total() / 10));
                    }

                    @Override
                    public void onError(String error) {
                        successExampleRlv.onRefreshComplete();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        FinanceManagerControl.getFinanceServiceManager().case_list(this, 0, ++now_page, false, CaseListInfo.class,
                new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        successExampleRlv.onLoadComplete();
                        loadMoreExampleList = ((CaseListInfo) data).getData().getCase_list();
                        if (loadMoreExampleList == null || loadMoreExampleList.size() == 0) {
                            successExampleRlv.showFooterResult(false);
                            return;
                        }
                        successExampleAdapter.addData(loadMoreExampleList);
                        successExampleRlv.showFooterResult(now_page <= (((CaseListInfo) data).getData().getCase_total() / 10));
                    }

                    @Override
                    public void onError(String error) {
                        successExampleRlv.onLoadComplete();
                    }
                });
    }
}
