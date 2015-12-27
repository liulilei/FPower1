package cn.fpower.financeservice.view.me;

import android.os.Bundle;
import android.view.View;
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
import cn.fpower.financeservice.mode.AchievementAmount;
import cn.fpower.financeservice.mode.AchievementData;
import cn.fpower.financeservice.mode.AchievementList;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 我的业绩 员工
 */
public class AchievementActivity extends BaseActivity implements RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.money)
    private TextView money;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    private AchievementFragmentAdapter adapter;
    private int now_page = 1;
    private List<AchievementData> exampleList;
    private List<AchievementData> loadMoreExampleList;


    @Override
    protected int initLayout() {
        return R.layout.activity_me_result;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("我的业绩");
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        progressRlv.setOnItemClickListener(this);
    }

    private int userId;

    @Override
    protected void initData() {
        super.initData();
        time.setText(TimeUtils.getMouth() + "月");
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            userId = getIntent().getExtras().getInt("user_id");
        }

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
    protected void onStart() {
        super.onStart();
        FinanceManagerControl.getFinanceServiceManager().achievement_amount(act, userId
                , TimeUtils.getMouthStart(), TimeUtils.getMouthEnd(), true, AchievementAmount.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                AchievementAmount m = (AchievementAmount) data;
                money.setText("￥" + m.getData().getAchievement_amount());
            }

            @Override
            public void onError(String error) {
                money.setText("￥0");
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 || position > exampleList.size()) {
            return;
        }
       /* Intent intent = new Intent(act, ShopDetailActivity.class);
        intent.putExtra("shop_id", exampleList.get(position - 1).id);
        startActivity(intent);*/
    }
}