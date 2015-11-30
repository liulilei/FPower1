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
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.viewmanager.HomeMenuFragmentGroup;
import cn.fpower.financeservice.mode.BannerInfo;
import cn.fpower.financeservice.mode.BannerInfo.BannerList;
import cn.fpower.financeservice.mode.SuccessExampleInfo;
import cn.fpower.financeservice.mode.SuccessExampleInfo.SuccessExample;
import cn.fpower.financeservice.view.WebViewActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.home.SuccessExampleActivity;
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

    private List<BannerList> bannerList;

    private String no_banner_url = "drawable://" + R.mipmap.ad1;

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
        bannerList = new ArrayList<BannerList>();
        BannerList banner = new BannerInfo().new BannerList();
        banner.setPicture(no_banner_url);
        banner.setUrl("http://www.baidu.com");
        bannerList.add(banner);
        homeMenuFragmentGroup = new HomeMenuFragmentGroup(getActivity());
        homeMenuFragmentGroup.initAd(bannerList, homeVp, pointLl);

        List<SuccessExample> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            data.add(new SuccessExampleInfo().new SuccessExample());
        }
        successLv.setAdapter(new SuccessExampleAdapter(getActivity(), data));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_interest_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, "http://www.baidu.com");
                startActivity(intent);
                break;
            case R.id.fragment_home_loan_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, "http://www.baidu.com");
                startActivity(intent);
                break;
            case R.id.fragment_home_limit_tv:
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constants.ADDRESS, "http://www.baidu.com");
                startActivity(intent);
                break;
            case R.id.fragment_home_add_entering_tv:
                ((HomeActivity) getActivity()).selectEnteringFragment();
                break;
            case R.id.fragment_home_success_more:
                intent = new Intent(getActivity(), SuccessExampleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
