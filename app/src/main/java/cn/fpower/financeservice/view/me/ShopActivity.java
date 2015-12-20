package cn.fpower.financeservice.view.me;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.AchievementFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.Achievement;
import cn.fpower.financeservice.mode.MyAchievement;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 我的店铺
 */
public class ShopActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.title_bar_title)
    private ImageView img_my_head;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.money)
    private TextView money;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        title.setText("店铺名称");
    }

    @Override
    protected void initData() {
        super.initData();
        time.setText(TimeUtils.getMouth()+"月");
        FinanceManagerControl.getFinanceServiceManager().my_achievement(act, FSApplication.getInstance().getUserInfo().getData().getId()
                , 0, true, MyAchievement.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                MyAchievement m=(MyAchievement)data;
                money.setText("￥"+m.getData().getMonth_amount());
                progressRlv.setAdapter(new AchievementFragmentAdapter(act,m.getData().getAchievement_list()));
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}