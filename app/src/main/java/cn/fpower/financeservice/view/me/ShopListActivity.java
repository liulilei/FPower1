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
import cn.fpower.financeservice.adapter.ShopListFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.ShopData;
import cn.fpower.financeservice.mode.ShopList;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * 店铺列表
 **/
public class ShopListActivity extends BaseActivity implements OnClickListener, RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener, AdapterView.OnItemClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    private ShopListFragmentAdapter adapter;

    private int now_page = 1;
    private List<ShopData> exampleList;
    private List<ShopData> loadMoreExampleList;

    @Override
    protected int initLayout() {
        return R.layout.activity_list;
    }


    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("店铺列表");
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        progressRlv.setOnItemClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        FinanceManagerControl.getFinanceServiceManager().shop_list(act,
                FSApplication.getInstance().getUserInfo().getData().getId(), 1, true, ShopList.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        ShopList info = (ShopList) data;
                        exampleList=info.getData().getShop_list();
                        if (exampleList == null || exampleList.size() == 0) {
                            ToastUtils.show(act, "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        adapter=new ShopListFragmentAdapter(act, exampleList);
                        progressRlv.setAdapter(adapter);
                        progressRlv.showFooterResult(now_page <= (info.getData().getShop_total() / Constants.PAGE_SIZE));
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
        FinanceManagerControl.getFinanceServiceManager().shop_list(act,
                FSApplication.getInstance().getUserInfo().getData().getId(), ++now_page, false, ShopList.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        progressRlv.onRefreshComplete();
                        ShopList info = (ShopList) data;
                        loadMoreExampleList=info.getData().getShop_list();
                        if (loadMoreExampleList == null || loadMoreExampleList.size() == 0) {
                            ToastUtils.show(act, "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        adapter.addData(loadMoreExampleList);
                        progressRlv.showFooterResult(now_page <= (info.getData().getShop_total() / Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        progressRlv.onRefreshComplete();
                    }
                });
    }

    @Override
    public void OnRefresh() {
        FinanceManagerControl.getFinanceServiceManager().shop_list(act,
                FSApplication.getInstance().getUserInfo().getData().getId(), 1, true, ShopList.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        now_page = 1;
                        progressRlv.onRefreshComplete();
                        ShopList info = (ShopList) data;
                        exampleList=info.getData().getShop_list();
                        if (exampleList == null || exampleList.size() == 0) {
                            ToastUtils.show(act, "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        if (adapter == null) {
                            adapter=new ShopListFragmentAdapter(act, exampleList);
                            progressRlv.setAdapter(adapter);
                        } else {
                            adapter.refresh(exampleList);
                        }
                        progressRlv.showFooterResult(now_page <= (info.getData().getShop_total() / Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        progressRlv.onRefreshComplete();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0 || position > exampleList.size()){
            return;
        }
        Intent intent = new Intent(act, ShopDetailActivity.class);
        intent.putExtra("shop_id", exampleList.get(position-1).id);
        startActivity(intent);
    }
}
