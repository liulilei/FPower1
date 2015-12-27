package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.AchievementFragmentAdapter;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.AchievementData;
import cn.fpower.financeservice.mode.AchievementList;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 业绩列表
 **/
public class AchievementListActivity extends BaseActivity implements OnClickListener, RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    private AchievementFragmentAdapter adapter;

    private int now_page = 1;
    private List<AchievementData> exampleList;
    private List<AchievementData> loadMoreExampleList;

    @Override
    protected int initLayout() {
        return R.layout.activity_list;
    }


    private int userId;

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("业绩列表");
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        progressRlv.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        userId = getIntent().getExtras().getInt("user_id");
        FinanceManagerControl.getFinanceServiceManager().achievement_list(act, userId, 1, 0, 0, true, AchievementList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                AchievementList info = (AchievementList) data;
                exampleList = info.getData().getAchievement_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                    return;
                }
                adapter = new AchievementFragmentAdapter(act, exampleList);
                progressRlv.setAdapter(adapter);
                progressRlv.showFooterResult(now_page <= (info.getData().getAchievement_total() / Constants.PAGE_SIZE));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        FinanceManagerControl.getFinanceServiceManager().achievement_list(act, userId, ++now_page, 0, 0, false, AchievementList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                progressRlv.onRefreshComplete();
                AchievementList info = (AchievementList) data;
                loadMoreExampleList = info.getData().getAchievement_list();
                if (loadMoreExampleList == null || loadMoreExampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                    return;
                }
                adapter.addData(loadMoreExampleList);
                progressRlv.showFooterResult(now_page <= (info.getData().getAchievement_total() / Constants.PAGE_SIZE));
            }

            @Override
            public void onError(String error) {
                progressRlv.onRefreshComplete();
            }
        });
    }

    @Override
    public void OnRefresh() {
        FinanceManagerControl.getFinanceServiceManager().achievement_list(act, userId, 1, 0, 0, true, AchievementList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                now_page = 1;
                progressRlv.onRefreshComplete();
                AchievementList info = (AchievementList) data;
                exampleList = info.getData().getAchievement_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                    return;
                }
                if (adapter == null) {
                    adapter = new AchievementFragmentAdapter(act, exampleList);
                    progressRlv.setAdapter(adapter);
                } else {
                    adapter.refresh(exampleList);
                }
                progressRlv.showFooterResult(now_page <= (info.getData().getAchievement_total() / Constants.PAGE_SIZE));
            }

            @Override
            public void onError(String error) {
                progressRlv.onRefreshComplete();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 || position > exampleList.size()) {
            return;
        }
       /* Intent intent = new Intent(act, ShopDetailActivity.class);
        intent.putExtra("shop_id", exampleList.get(position - 1).id);
        startActivity(intent);*/
    }
}
