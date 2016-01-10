package cn.fpower.financeservice.view.me;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.EmpFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.AchievementAmount;
import cn.fpower.financeservice.mode.EmployeeList;
import cn.fpower.financeservice.mode.PickData;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenu;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuCreator;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuItem;
import cn.fpower.financeservice.view.widget.swipelistview.SwipeMenuListView;

/**
 * 我的店铺
 */
public class ShopActivity extends BaseActivity implements AdapterView.OnItemClickListener, RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.title_bar_right_iv)
    private ImageView title_bar_right_iv;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.time)
    private TextView time;

    @ViewInject(R.id.money)
    private TextView money;

    @ViewInject(R.id.img_my_head)
    private ImageView img_my_head;

    @ViewInject(R.id.top_name)
    private TextView top_name;

    @ViewInject(R.id.fragment_progress_rlv)
    private SwipeMenuListView progressRlv;

    private EmpFragmentAdapter adapter;

    private int now_page = 1;
    List<EmployeeList.EmpInfo> exampleList;
    List<EmployeeList.EmpInfo> loadMoreExampleList;

    private EmployeeList emp;

    @ViewInject(R.id.layout_info)
    private View layout_info;

    private PickData pick;
    OptionsPickerView optionsPickerView ;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title_bar_right_iv.setVisibility(View.VISIBLE);
        title_bar_right_iv.setOnClickListener(this);
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        layout_info.setOnClickListener(this);
        title.setText("我的店铺");
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
            public boolean onMenuItemClick(final int position,
                                           SwipeMenu menu, int index) {
                switch (index) {
                    case 0://删除 点击事件
                        //deleteDrugPosition(position);
                        FinanceManagerControl.getFinanceServiceManager().delete_employee(act, userId
                                , exampleList.get(position).getId(), true, new ManagerStringListener() {
                            @Override
                            public void onSuccess(String data) {
                                adapter.remove(position);
                                int total = emp.getData().getEmployee_total() - 1;
                                emp.getData().setEmployee_total(total);
                                progressRlv.showFooterResult(emp.getData().getEmployee_total() > now_page * Constants.PAGE_SIZE);
                            }

                            @Override
                            public void onError(String error) {
                                ToastUtils.show(act, "请重新刷新数据！");
                            }
                        });
                        break;
                }
                return true;
            }
        });

        progressRlv.setOnItemClickListener(this);
    }

    private int userId;

    @Override
    protected void initData() {
        super.initData();
        initListViewMenu();
        initPick();
        userId = FSApplication.getInstance().getUserInfo().getData().getId();
        ImageUtils.displayImageRoundImg(R.mipmap.moren, NetApi.URL_HTTP + FSApplication.getInstance().getUserInfo().getData().getShop_img(), img_my_head);
        top_name.setText(FSApplication.getInstance().getUserInfo().getData().getShop_name());
        refresh();
    }

    private void refresh(){
        getAmount(false);
        FinanceManagerControl.getFinanceServiceManager().employee_list(act, userId
                , now_page, true, EmployeeList.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                emp = (EmployeeList) data;
                exampleList = emp.getData().getEmployee_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                    return;
                }
                adapter = new EmpFragmentAdapter(act, exampleList);
                progressRlv.setAdapter(adapter);
                progressRlv.showFooterResult(emp.getData().getEmployee_total() > now_page * Constants.PAGE_SIZE);
            }

            @Override
            public void onError(String error) {
                progressRlv.setLoadEnable(false);
            }
        });
    }


    private void getAmount(boolean isDialog) {
        FinanceManagerControl.getFinanceServiceManager().achievement_amount(act, userId
                , pick.getStart(), pick.getEnd(), isDialog, AchievementAmount.class, new ManagerDataListener() {
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
            case R.id.title_bar_right_iv:
                startActivityForResult(new Intent(this, ShopAddEmpActivity.class), 100);
                break;
            case R.id.layout_info:
                optionsPickerView.show();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 || position > adapter.getList().size()) {
            return;
        }
        Intent intent = new Intent(act, AchievementActivity.class);
        intent.putExtra("user_id", adapter.getList().get(position - 1).getId());
        startActivityForResult(intent,100);
    }

    @Override
    public void onLoadMore() {
        FinanceManagerControl.getFinanceServiceManager().employee_list(act, userId
                , ++now_page, false, EmployeeList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                progressRlv.onRefreshComplete();
                emp = (EmployeeList) data;
                loadMoreExampleList = emp.getData().getEmployee_list();
                if (loadMoreExampleList == null || loadMoreExampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                }
                adapter.addData(loadMoreExampleList);
                progressRlv.showFooterResult(emp.getData().getEmployee_total() > now_page * Constants.PAGE_SIZE);
            }

            @Override
            public void onError(String error) {
                progressRlv.onRefreshComplete();
            }
        });
    }

    @Override
    public void OnRefresh() {
        getAmount(false);
        FinanceManagerControl.getFinanceServiceManager().employee_list(act, userId
                , 1, false, EmployeeList.class, new ManagerDataListener() {

            @Override
            public void onSuccess(Object data) {
                now_page = 1;
                progressRlv.onRefreshComplete();
                emp = (EmployeeList) data;
                exampleList = emp.getData().getEmployee_list();
                if (exampleList == null || exampleList.size() == 0) {
                    ToastUtils.show(act, "没有数据");
                    progressRlv.showFooterResult(false);
                    return;
                }
                if (adapter == null) {
                    adapter = new EmpFragmentAdapter(act, exampleList);
                    progressRlv.setAdapter(adapter);
                } else {
                    adapter.refresh(exampleList);
                }
                progressRlv.showFooterResult(emp.getData().getEmployee_total() > now_page * Constants.PAGE_SIZE);
            }

            @Override
            public void onError(String error) {
                progressRlv.onRefreshComplete();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 100) {
            refresh();
        }
    }
}