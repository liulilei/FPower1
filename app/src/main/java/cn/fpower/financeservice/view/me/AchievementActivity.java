package cn.fpower.financeservice.view.me;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.AchievementFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.AchievementAmount;
import cn.fpower.financeservice.mode.AchievementData;
import cn.fpower.financeservice.mode.AchievementList;
import cn.fpower.financeservice.mode.PickData;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 我的业绩 员工
 */
public class AchievementActivity extends BaseActivity implements RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.list_title)
    private TextView list_title;

    @ViewInject(R.id.top_name)
    private TextView top_name;

    @ViewInject(R.id.img_my_head)
    private ImageView img_my_head;

    @ViewInject(R.id.money)
    private TextView money;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    private AchievementFragmentAdapter adapter;
    private int now_page = 1;
    private List<AchievementData> exampleList;
    private List<AchievementData> loadMoreExampleList;

    private UserInfo.Data user_info;

    @ViewInject(R.id.layout_info)
    private View layout_info;

    private PickData pick;
    OptionsPickerView optionsPickerView ;

    @Override
    protected int initLayout() {
        return R.layout.activity_me_result;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        layout_info.setOnClickListener(this);
        title.setText("业绩详情");
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        top_name.setText("");
    }

    private int userId;

    @Override
    protected void initData() {
        super.initData();
        initPick();
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            userId = extras.getInt("user_id");
            list_title.setText("他的业绩");
        }else{
            userId= FSApplication.getInstance().getUserInfo().getData().getId();
            list_title.setText("我的业绩");
        }
        getAmount(false);
        FinanceManagerControl.getFinanceServiceManager().achievement_list(act, userId, now_page, 0, 0, true, AchievementList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                AchievementList info = (AchievementList) data;
                exampleList = info.getData().getAchievement_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
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

    private void initPick(){
        time.setText(TimeUtils.getMonth() + "月");
        pick=new PickData();
        optionsPickerView = new OptionsPickerView(act);
        optionsPickerView.setPicker(pick.getOptions1Items(), pick.getOptions2Items(), true);
        optionsPickerView.setCyclic(false);
        optionsPickerView.setSelectOptions(0, pick.position);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                pick.setStartAndEnd(pick.getOptions1Items().get(options1).getCode(),
                        pick.getOptions2Items().get(options1).get(option2).getCode());
                time.setText(pick.getOptions2Items().get(options1).get(option2).getName());
                getAmount(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                setResult(RESULT_OK);
                this.finish();
                break;
            case R.id.layout_info:
                optionsPickerView.show();
                break;
        }
    }

    public void getAmount(boolean isDialog){
        FinanceManagerControl.getFinanceServiceManager().achievement_amount(act, userId
                , TimeUtils.getMonthStart(), TimeUtils.getMonthEnd(), isDialog, AchievementAmount.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                AchievementAmount m = (AchievementAmount) data;
                user_info = m.getData().getUser_info();
                ImageUtils.displayImageRoundImg(R.mipmap.moren,  NetApi.URL_HTTP + user_info.getFace(), img_my_head);
                money.setText("￥" + m.getData().getAchievement_amount());
                top_name.setText(user_info.getUsername());
            }

            @Override
            public void onError(String error) {
                money.setText("￥0");
            }
        });
    }


    @Override
    public void OnRefresh() {
        getAmount(false);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}