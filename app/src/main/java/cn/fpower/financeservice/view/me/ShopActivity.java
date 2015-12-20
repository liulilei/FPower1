package cn.fpower.financeservice.view.me;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenu;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuCreator;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuItem;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuListView;

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
    private SwipeMenuListView progressRlv;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        title.setText("店铺名称");
    }

    private void initListViewMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu, int position) {
                // 创建一个侧滑菜单
                SwipeMenuItem delItem = new SwipeMenuItem(
                        getApplicationContext());
                // 给该侧滑菜单设置背景
                delItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F,
                        0x25)));
                // 设置宽度
                delItem.setWidth(getResources().getDimensionPixelSize(
                        R.dimen.work_time_item_menu_width));
                /*
                 * // 设置图片 delItem.setIcon(R.drawable.icon_del);
 */
                // 设置名称
                delItem.setTitle("删除");
                // 字体大小
                delItem.setTitleSize(StringUtils.px2dip(act,
                        30));
                // 字体颜色
                delItem.setTitleColor(Color.WHITE);
                // 加入到侧滑菜单中
                menu.addMenuItem(delItem);
            }
        };
        progressRlv.setMenuCreator(creator);

        progressRlv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position,
                                           SwipeMenu menu, int index) {
                switch (index) {
                    case 0://删除 点击事件
                        //deleteDrugPosition(position);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        initListViewMenu();
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