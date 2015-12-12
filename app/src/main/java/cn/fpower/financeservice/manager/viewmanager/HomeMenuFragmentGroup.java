package cn.fpower.financeservice.manager.viewmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.MainAdPageradapter;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.mode.HomeInfo;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.view.WebViewActivity;
import cn.fpower.financeservice.mode.HomeInfo.AdItem;

public class HomeMenuFragmentGroup {

    private Context context;
    private static final long AD_CHANGE_TIME = 4000;
    private ViewPager viewPager;
    public boolean isRunning;

    private int mCurrentPagePosition = FIRST_ITEM_INDEX;
    private int mCurrentIndex;
    private static final int FIRST_ITEM_INDEX = 1;

    private int viewLength;

    private boolean mIsChanged = false;

    // 菜单滚动
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1 && isRunning) {
                if (++mCurrentPagePosition < viewLength) {
                } else {
                    mCurrentPagePosition = 0;
                }
                viewPager.setCurrentItem(mCurrentPagePosition);
                sendEmptyMessageDelayed(1, AD_CHANGE_TIME);
            }
        }

        ;
    };

    public HomeMenuFragmentGroup(Context context) {
        this.context = context;
    }

    /**
     * 初始化home fragment的广告滚动栏
     *
     * @param viewPager
     * @param pointGroup
     */
    public void initAd(final List<AdItem> bannerList,
                       final ViewPager viewPager, final LinearLayout pointGroup) {
        this.viewPager = viewPager;
        List<View> views = new ArrayList<View>();
        views.add(addImageView(NetApi.URL_HTTP + bannerList.get(bannerList.size() - 1).getAd_img(),
                NetApi.URL_HTTP + bannerList.get(bannerList.size() - 1).getAd_link()));
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView point = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = StringUtils.px2dip(context, 20);
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.selector_ad_point_bg);
            if (0 == i) {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
            views.add(addImageView(NetApi.URL_HTTP + bannerList.get(i).getAd_img(), NetApi.URL_HTTP + bannerList.get(i).getAd_link()));
        }
        views.add(addImageView(NetApi.URL_HTTP + bannerList.get(0).getAd_img(), NetApi.URL_HTTP + bannerList.get(0).getAd_link()));
        viewLength = views.size();
        viewPager.setAdapter(new MainAdPageradapter(views));
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int pPosition) {
                mIsChanged = true;
                if (pPosition > viewLength - 2) {
                    mCurrentPagePosition = FIRST_ITEM_INDEX;
                } else if (pPosition < FIRST_ITEM_INDEX) {
                    mCurrentPagePosition = viewLength - 2;
                } else {
                    mCurrentPagePosition = pPosition;
                }
                setCurrentDot(mCurrentPagePosition, pointGroup);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (ViewPager.SCROLL_STATE_IDLE == arg0) {
                    if (mIsChanged) {
                        mIsChanged = false;
                        viewPager.setCurrentItem(mCurrentPagePosition, false);
                    }
                }
            }
        });

        viewPager.setCurrentItem(mCurrentPagePosition, false);
        isRunning = true;
        handler.sendEmptyMessageDelayed(1, AD_CHANGE_TIME);
    }

    public void removeHandle() {
        handler.removeCallbacksAndMessages(null);
    }

    private ImageView addImageView(String url, final String address) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.FIT_XY);
        ImageUtils.displayNormalImg(R.mipmap.ic_launcher, url, imageView);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(address)) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Constants.ADDRESS, address);
                    context.startActivity(intent);
                }
            }
        });
        return imageView;
    }

    private void setCurrentDot(int positon, LinearLayout mPointViewGroup) {
        // 界面实际显示的序号是第1, 2, 3。而点的序号应该是0, 1, 2.所以减1.
        positon = positon - 1;
        if (positon < 0 || positon > viewLength - 1 || mCurrentIndex == positon) {
            return;
        }
        mPointViewGroup.getChildAt(positon).setEnabled(false);
        mPointViewGroup.getChildAt(mCurrentIndex).setEnabled(true);
        mCurrentIndex = positon;
    }

}
