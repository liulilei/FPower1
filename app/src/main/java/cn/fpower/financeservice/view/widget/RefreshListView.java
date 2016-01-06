package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.LogUtils;

public class RefreshListView extends ListView implements OnScrollListener {

    public interface IListViewState {
        int LVS_NORMAL = 0; // 普通状态
        int LVS_PULL_REFRESH = 1; // 下拉刷新状态
        int LVS_RELEASE_REFRESH = 2; // 松开刷新状态
        int LVS_LOADING = 3; // 加载状态
    }

    public interface IOnRefreshListener {
        void OnRefresh();
    }

    private SimpleDateFormat dateFormat;
    private LinearLayout mHeadView;
    private TextView mRefreshTextview;
    private TextView mLastUpdateTextView;
    private ImageView mArrowImageView;
    private ProgressBar mHeadProgressBar;

    private int mHeadContentHeight;

    private IOnRefreshListener mOnRefreshListener; // 头部刷新监听器

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean mIsRecord = false;
    // 标记的Y坐标值
    private int mStartY = -1;
    // 当前视图能看到的第一个项的索引
    private int mFirstItemIndex = -1;
    // MOVE时保存的Y坐标值
    private int mMoveY = -1;
    // LISTVIEW状态
    private int mViewState = IListViewState.LVS_NORMAL;

    private final static int RATIO = 2;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    private boolean mBack = false;

    private TextView loadFull; // 加载完毕
    private TextView more; // 还可以继续加载
    private ProgressBar loading;// 底部进度条
    private IOnLoadMoreListener mLoadMoreListener; // 加载更多监听器

    private View footer;

    private int scrollState;

    private boolean loadEnable = true;// 开启或者关闭加载更多功能

    private boolean isLoading;// 判断是否正在加载

    private boolean isLoadFull;

    private int pageSize = 10;

    public int stateHeight = -1;

    private int mListViewYOnScreen = -1;

    private View mCustomView;

    private int[] location;


    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
        init(context);
    }

    public void setOnRefreshListener(IOnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    private void onRefresh() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.OnRefresh();
        }
    }

    public void onRefreshComplete() {
        mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
        mLastUpdateTextView.setText("上次更新于:" + dateFormat.format(new Date()));
        switchViewState(IListViewState.LVS_NORMAL);
    }

    private void init(Context context) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        initHeadView(context);
        initLoadMoreView(context);
        setOnScrollListener(this);
    }

    public void setFirstItemIndex(int firstItemIndex) {
        mFirstItemIndex = firstItemIndex;
    }

    // 初始化headview试图
    private void initHeadView(Context context) {
        mHeadView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.refresh_list_view_head, null);

        mArrowImageView = (ImageView) mHeadView.findViewById(R.id.head_arrowImageView);
        mArrowImageView.setMinimumWidth(60);

        mHeadProgressBar = (ProgressBar) mHeadView.findViewById(R.id.head_progressBar);

        mRefreshTextview = (TextView) mHeadView.findViewById(R.id.head_tipsTextView);

        mLastUpdateTextView = (TextView) mHeadView.findViewById(R.id.head_lastUpdatedTextView);

        mLastUpdateTextView.setText("上次更新于：" + dateFormat.format(new Date()));
        measureView(mHeadView);
        mHeadContentHeight = mHeadView.getMeasuredHeight();

        mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
        mHeadView.invalidate();

        addHeaderView(mHeadView, null, false);

//        animation = new RotateAnimation(0, 23, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 1f);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setDuration(250);
//        animation.setFillAfter(true);
//
//        reverseAnimation = new RotateAnimation(23, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 1f);
//        reverseAnimation.setInterpolator(new LinearInterpolator());
//        reverseAnimation.setDuration(200);
//        reverseAnimation.setFillAfter(true);
        // 设置箭头特效
        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(100);
        reverseAnimation.setFillAfter(true);
    }

    // 初始化footview试图
    private void initLoadMoreView(Context context) {
        footer = LayoutInflater.from(context).inflate(R.layout.load_more, null);

        loadFull = (TextView) footer.findViewById(R.id.loadFull);
        more = (TextView) footer.findViewById(R.id.more);
        loading = (ProgressBar) footer.findViewById(R.id.loading);

        addFooterView(footer);

    }

    // 此方法直接照搬自网络上的一个下拉刷新的demo，计算headView的width以及height
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        location = new int[2];
        if (mOnRefreshListener != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    doActionDown(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    doActionMove(ev);
                    break;
                case MotionEvent.ACTION_UP:
                    doActionUp(ev);
                    break;
                default:
                    break;
            }
        }

        return super.onTouchEvent(ev);
    }

    private void doActionDown(MotionEvent ev) {
        if (mIsRecord == false && mFirstItemIndex == 0) {
            mStartY = (int) ev.getY();
            mIsRecord = true;
        }
    }

    private void doActionMove(MotionEvent ev) {
        if (mStartY == -1) {
            mStartY = (int) ev.getY();
        }
        mMoveY = (int) ev.getY();

        if (mIsRecord == false && mFirstItemIndex == 0) {
            mStartY = (int) ev.getY();
            mIsRecord = true;
        }

        if (mIsRecord == false || mViewState == IListViewState.LVS_LOADING) {
            return;
        }

        if (mCustomView != null) {
            if (mListViewYOnScreen == -1) {
                this.getLocationOnScreen(location);
                mListViewYOnScreen = location[1];
            }
            mCustomView.getLocationOnScreen(location);
            int mCustomViewOnScreen = location[1];
            if (stateHeight == -1) {
                stateHeight = mListViewYOnScreen - mCustomViewOnScreen;
            }

            if (mListViewYOnScreen > mCustomViewOnScreen) {
                return;
            }
        }

        int offset;

        if (stateHeight < 0 || stateHeight == 0) {
            offset = mMoveY - mStartY;
        } else {
            offset = mMoveY - mStartY - stateHeight;
        }

        offset = offset / RATIO;

        switch (mViewState) {
            case IListViewState.LVS_NORMAL: {
                if (offset > 0) {
                    mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
                    switchViewState(IListViewState.LVS_PULL_REFRESH);
                }
            }
            break;
            case IListViewState.LVS_PULL_REFRESH: {
                setSelection(0);
                mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
                if (offset < 0) {
                    switchViewState(IListViewState.LVS_NORMAL);
                } else if (offset > mHeadContentHeight) {
                    switchViewState(IListViewState.LVS_RELEASE_REFRESH);
                }
            }
            break;
            case IListViewState.LVS_RELEASE_REFRESH: {
                setSelection(0);
                mHeadView.setPadding(0, offset - mHeadContentHeight, 0, 0);
                if (offset >= 0 && offset <= mHeadContentHeight) {
                    mBack = true;
                    switchViewState(IListViewState.LVS_PULL_REFRESH);
                } else if (offset < 0) {
                    switchViewState(IListViewState.LVS_NORMAL);
                } else {

                }

            }
            break;
            default:
                return;
        }

    }

    private void doActionUp(MotionEvent ev) {
        mStartY = -1;
        stateHeight = -1;
        mIsRecord = false;
        mBack = false;

        if (mViewState == IListViewState.LVS_LOADING) {
            return;
        }

        switch (mViewState) {
            case IListViewState.LVS_NORMAL:

                break;
            case IListViewState.LVS_PULL_REFRESH:
                mHeadView.setPadding(0, -1 * mHeadContentHeight, 0, 0);
                switchViewState(IListViewState.LVS_NORMAL);
                break;
            case IListViewState.LVS_RELEASE_REFRESH:
                mHeadView.setPadding(0, 0, 0, 0);
                switchViewState(IListViewState.LVS_LOADING);
                onRefresh();
                break;
        }

    }

    // 切换headview视图
    private void switchViewState(int state) {

        switch (state) {
            case IListViewState.LVS_NORMAL: {
                LogUtils.e("!!!!!!!!!!!", "convert to IListViewState.LVS_NORMAL");
                mArrowImageView.clearAnimation();
                mArrowImageView.setImageResource(R.mipmap.loading_pull);
            }
            break;
            case IListViewState.LVS_PULL_REFRESH: {
                LogUtils.e("!!!!!!!!!!!", "convert to IListViewState.LVS_PULL_REFRESH");
                mHeadProgressBar.setVisibility(View.GONE);
                mArrowImageView.setVisibility(View.VISIBLE);
                mRefreshTextview.setText("下拉刷新");
                mArrowImageView.clearAnimation();

                // 是由RELEASE_To_REFRESH状态转变来的
                if (mBack) {
                    mBack = false;
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(reverseAnimation);
                }
            }
            break;
            case IListViewState.LVS_RELEASE_REFRESH: {
                LogUtils.e("!!!!!!!!!!!", "convert to IListViewState.LVS_RELEASE_REFRESH");
                mHeadProgressBar.setVisibility(View.GONE);
                mArrowImageView.setVisibility(View.VISIBLE);
                mRefreshTextview.setText("松开立即刷新");
                mArrowImageView.clearAnimation();
                mArrowImageView.startAnimation(animation);
            }
            break;
            case IListViewState.LVS_LOADING: {
                LogUtils.e("!!!!!!!!!!!", "convert to IListViewState.LVS_LOADING");
                mHeadProgressBar.setVisibility(View.VISIBLE);
                mArrowImageView.clearAnimation();
                mArrowImageView.setVisibility(View.GONE);
                mRefreshTextview.setText("正在刷新");
            }
            break;
            default:
                return;
        }

        mViewState = state;

    }

    public interface IOnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(IOnLoadMoreListener listener) {
        this.loadEnable = true;
        mLoadMoreListener = listener;
    }

    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        isLoading = false;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    // 这里的开启或者关闭加载更多，并不支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        this.removeFooterView(footer);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        ifNeedLoad(view, scrollState);

    }

    // 根据listview滑动的状态判断是否需要加载更多
    private void ifNeedLoad(AbsListView view, int scrollState) {
        if (!loadEnable) {
            return;
        }
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading
                    && view.getLastVisiblePosition() == view.getPositionForView(footer) && !isLoadFull) {
                isLoading = true;
                onLoad();
            }
        } catch (Exception e) {
        }
    }

    public void onLoad() {
        if (mLoadMoreListener != null) {
            mLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 这个方法是根据结果的大小来决定footer显示的。
     * <p>
     * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
     * </p>
     *
     * @param resultSize
     */
    public void setResultSize(int resultSize) {
        if (resultSize == 0) {
            isLoadFull = true;
            loadFull.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
        } else if (resultSize > 0 && resultSize < pageSize) {
            isLoadFull = true;
            loadFull.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
        } else if (resultSize == pageSize) {
            isLoadFull = false;
            loadFull.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            more.setVisibility(View.VISIBLE);
        }
    }

    public void showFooterResult(boolean hasNextPager) {
        if (hasNextPager) {
            isLoadFull = false;
            loadFull.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            more.setVisibility(View.VISIBLE);
        } else {
            isLoadFull = true;
            loadFull.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        setFirstItemIndex(firstVisibleItem);
    }

    public interface ScrollToFootListener {
        void doWhatYouWant();
    }

    private ScrollToFootListener onFootListener;

    public void setScrollToFootListener(ScrollToFootListener listener) {
        onFootListener = listener;
    }

    public void addCustomView(View v) {
        mHeadView.addView(v);
        mCustomView = v;
    }
}
