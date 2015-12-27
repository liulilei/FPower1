package cn.fpower.financeservice.view.me;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.AchievementFragmentAdapter;
import cn.fpower.financeservice.adapter.EmpFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.AchievementData;
import cn.fpower.financeservice.mode.EmployeeList;
import cn.fpower.financeservice.mode.MyAchievement;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenu;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuCreator;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuItem;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuListView;

/**
 * 我的业绩
 */
public class AchievementActivity extends BaseActivity {

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

    List<EmployeeList.EmpInfo> employee_list;

    @Override
    protected int initLayout() {
        return R.layout.activity_me_result;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("我的业绩");
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
        if(userId==0) {
            userId = FSApplication.getInstance().getUserInfo().getData().getId();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FinanceManagerControl.getFinanceServiceManager().my_achievement(act, userId
                , 0, true, MyAchievement.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                MyAchievement m = (MyAchievement) data;
                money.setText("￥" + m.getData().getMonth_amount());
                List<AchievementData> achievement_list = m.getData().getAchievement_list();
                if (adapter == null) {
                    adapter = new AchievementFragmentAdapter(act, achievement_list);
                    progressRlv.setAdapter(adapter);
                } else {
                    adapter.refresh(achievement_list);
                }
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
            case R.id.title_bar_right_iv:
                startActivity(new Intent(this, ShopAddEmpActivity.class));
                break;
        }
    }
}