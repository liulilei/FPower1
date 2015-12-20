package cn.fpower.financeservice.view.me;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.ShopListFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.ShopList;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 店铺列表
 **/
public class PromoterCheckListActivity extends BaseActivity implements OnClickListener {

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
        title.setText("店铺列表");

    }

    @Override
    protected void initData() {
        super.initData();
        FinanceManagerControl.getFinanceServiceManager().shop_list(act,
                FSApplication.getInstance().getUserInfo().getData().getId(), 0, true, ShopList.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        ShopList info = (ShopList) data;
                        progressRlv.setAdapter(new ShopListFragmentAdapter(act, info.getData().getShop_list()));
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
}
