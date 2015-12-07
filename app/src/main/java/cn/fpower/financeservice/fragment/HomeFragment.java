package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.SuccessExampleAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.viewmanager.HomeMenuFragmentGroup;
import cn.fpower.financeservice.mode.BannerInfo;
import cn.fpower.financeservice.mode.BannerInfo.BannerList;
import cn.fpower.financeservice.mode.SuccessExampleInfo;
import cn.fpower.financeservice.mode.SuccessExampleInfo.SuccessExample;
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

    private List<BannerList> bannerList;

    private String no_banner_url = "drawable://" + R.mipmap.ad1;

    private HomeMenuFragmentGroup homeMenuFragmentGroup;

    private Intent intent;

    private AMapLocationClient aMapLocationClient;

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
        aMapLocationClient = FSApplication.getInstance().getAMapLocationClient();
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        amapLocation.getLatitude();//获取经度
                        amapLocation.getLongitude();//获取纬度
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
                        amapLocation.getCountry();//国家信息
                        amapLocation.getProvince();//省信息
                        amapLocation.getCity();//城市信息
                        amapLocation.getDistrict();//城区信息
                        amapLocation.getRoad();//街道信息
                        amapLocation.getCityCode();//城市编码
                        amapLocation.getAdCode();//地区编码
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
                aMapLocationClient.stopLocation();
            }
        });
        aMapLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_home_interest_tv:
//                intent = new Intent(getActivity(), WebViewActivity.class);
//                intent.putExtra(Constants.ADDRESS, "http://www.baidu.com");
//                startActivity(intent);
                aMapLocationClient.setLocationOption(FSApplication.getInstance().getAMapLocationClientOption());
                aMapLocationClient.startLocation();
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
                if (!FSApplication.getInstance().isLogin()) {
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
