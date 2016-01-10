package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.SuccessExampleAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.viewmanager.HomeMenuFragmentGroup;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.mode.HomeInfo;
import cn.fpower.financeservice.mode.HomeInfo.AdItem;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.view.WebViewActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.home.SuccessExampleActivity;
import cn.fpower.financeservice.view.me.LoginCheckActivity;
import cn.fpower.financeservice.view.widget.HorizontalViewPager;
import cn.fpower.financeservice.view.widget.OnClickInnerListView;

/**
 * Created by ll on 2015/11/26.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.fragment_home_vp)
    private HorizontalViewPager homeVp;

    @ViewInject(R.id.fragment_home_vp_point_group)
    private LinearLayout pointLl;

    @ViewInject(R.id.fragment_home_interest_tv)
    private TextView interestTv;

    @ViewInject(R.id.fragment_home_loan_tv)
    private TextView loanTv;

    @ViewInject(R.id.fragment_home_limit_tv)
    private TextView limitTv;

    @ViewInject(R.id.fragment_home_add_entering_tv)
    private TextView enteringTv;

    @ViewInject(R.id.fragment_home_success_lv)
    private OnClickInnerListView successLv;

    @ViewInject(R.id.fragment_home_success_more)
    private TextView successMoreTv;

    private List<AdItem> adItems;

    private List<String> introduceList;

    private List<DataInfo> loanList;

    private String no_banner_url = "drawable://" + R.mipmap.ad;

    private HomeMenuFragmentGroup homeMenuFragmentGroup;

    private Intent intent;

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_home, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(getResources().getString(R.string.app_name));
        interestTv.setOnClickListener(this);
        loanTv.setOnClickListener(this);
        limitTv.setOnClickListener(this);
        enteringTv.setOnClickListener(this);
        successMoreTv.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        FinanceManagerControl.getFinanceServiceManager().home(getActivity(), true, HomeInfo.class,
                new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        HomeInfo homeInfo = (HomeInfo) data;
                        adItems = homeInfo.getData().getAd_item();
                        introduceList = homeInfo.getData().getIntroduce();
                        loanList = homeInfo.getData().getLoan_list();
                        initAdView(adItems);
                        initListView(loanList);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });


    }

    private void initListView(List<DataInfo> loanList) {
        if (loanList != null && loanList.size() > 0) {
            successLv.setAdapter(new SuccessExampleAdapter(getActivity(), loanList));
        }
    }

    private void initAdView(List<AdItem> adItems) {
        if (adItems == null || adItems.size() == 0) {
            adItems = new ArrayList<AdItem>();
            AdItem aditem = new HomeInfo().new AdItem();
            aditem.setAd_img(no_banner_url);
        }
        homeMenuFragmentGroup = new HomeMenuFragmentGroup(getActivity());
        homeMenuFragmentGroup.initAd(adItems, homeVp, pointLl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_interest_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, NetApi.URL_HTTP + introduceList.get(0));
                startActivity(intent);
                break;
            case R.id.fragment_home_limit_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, NetApi.URL_HTTP + introduceList.get(1));
                startActivity(intent);
                break;
            case R.id.fragment_home_loan_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, NetApi.URL_HTTP + introduceList.get(2));
                startActivity(intent);
                break;
            case R.id.fragment_home_add_entering_tv:
                if (FSApplication.getInstance().getLogincode() < 200) {
                    IntentUtils.startActivity(getActivity(), LoginCheckActivity.class);
                    return;
                }
                ((HomeActivity) getActivity()).selectEnteringFragment();
                break;
            case R.id.fragment_home_success_more:
                intent = new Intent(getActivity(), SuccessExampleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
