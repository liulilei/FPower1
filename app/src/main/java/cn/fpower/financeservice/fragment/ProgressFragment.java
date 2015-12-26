package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.ProgressFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.mode.LoanInfo;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.progress.ProgressDetailActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * Created by ll on 2015/11/26.
 */
public class ProgressFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, RefreshListView.IOnRefreshListener, RefreshListView.IOnLoadMoreListener {

    @ViewInject(R.id.fragment_progress_rlv)
    private RefreshListView progressRlv;

    @ViewInject(R.id.line1)
    private View line1;

    @ViewInject(R.id.line2)
    private View line2;

    @ViewInject(R.id.line3)
    private View line3;

    @ViewInject(R.id.line4)
    private View line4;

    @ViewInject(R.id.fragment_progress_all)
    private RadioButton progressAll;

    @ViewInject(R.id.fragment_progress_checking)
    private RadioButton progressChecking;

    @ViewInject(R.id.fragment_progress_checked)
    private RadioButton progressChecked;

    @ViewInject(R.id.fragment_progress_check_ok)
    private RadioButton progressCheckOk;

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    private RadioButton currentRb;

    private View currentView;

    private int currentProgress = -2;

    private int now_page = 1;

    private List<DataInfo> dataInfoList;

    private List<DataInfo> refreshDataInfoList;

    private List<DataInfo> loadMoreDataInfoList;

    private ProgressFragmentAdapter progressFragmentAdapter;

    private String userId;

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_progress, null);
        return ll;
    }

    @Override
    protected void initView() {
        userId = FSApplication.getInstance().getUserInfo().getData().getId() + "";
        currentProgress = Constants.ProgressStatus.ALL;
        back.setVisibility(View.GONE);
        title.setText("进度");
        progressAll.setOnClickListener(this);
        progressChecking.setOnClickListener(this);
        progressChecked.setOnClickListener(this);
        progressCheckOk.setOnClickListener(this);
        progressRlv.setOnItemClickListener(this);
        progressRlv.setOnRefreshListener(this);
        progressRlv.setOnLoadMoreListener(this);
        currentRb = progressAll;
        currentView = line1;
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(), userId,
                currentProgress, now_page, true, LoanInfo.class, new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        dataInfoList = ((LoanInfo) data).getData().getLoan_list();

                        if (dataInfoList == null || dataInfoList.size() == 0) {
                            ToastUtils.show(getActivity(), "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        progressFragmentAdapter = new ProgressFragmentAdapter(getActivity(), dataInfoList);
                        progressRlv.setAdapter(progressFragmentAdapter);
                        progressRlv.showFooterResult(now_page <= (((LoanInfo) data).getData().getLoan_total() / Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        progressRlv.setLoadEnable(false);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_progress_all:
                showView(line1, progressAll);
                currentProgress = Constants.ProgressStatus.ALL;
                break;
            case R.id.fragment_progress_checking:
                showView(line2, progressChecking);
                currentProgress = Constants.ProgressStatus.AUDIT;
                break;
            case R.id.fragment_progress_checked:
                showView(line3, progressChecked);
                currentProgress = Constants.ProgressStatus.AUDIT_SUCCESS;
                break;
            case R.id.fragment_progress_check_ok:
                showView(line4, progressCheckOk);
                currentProgress = Constants.ProgressStatus.APPLY_SUCCESS;
                break;
        }
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(), userId,
                currentProgress, 1, true, LoanInfo.class, new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        now_page = 1;
                        dataInfoList = ((LoanInfo) data).getData().getLoan_list();
                        if (dataInfoList == null || dataInfoList.size() == 0) {
                            ToastUtils.show(getActivity(), "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        if (progressFragmentAdapter == null) {
                            progressFragmentAdapter = new ProgressFragmentAdapter(getActivity(), dataInfoList);
                            progressRlv.setAdapter(progressFragmentAdapter);
                        } else {
                            progressFragmentAdapter.refresh(dataInfoList);
                        }
                        progressRlv.showFooterResult(now_page <= (((LoanInfo) data).getData().getLoan_total() /  Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        dataInfoList.clear();
                        if (progressFragmentAdapter == null) {
                            progressFragmentAdapter = new ProgressFragmentAdapter(getActivity(), dataInfoList);
                            progressRlv.setAdapter(progressFragmentAdapter);
                        } else {
                            progressFragmentAdapter.refresh(dataInfoList);
                        }
                    }
                });
    }

    private void showView(View view, RadioButton rb) {
        currentRb.setChecked(false);
        rb.setChecked(true);
        currentRb = rb;
        currentView.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        currentView = view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0 || position > dataInfoList.size()){
            return;
        }
        Intent intent = new Intent(getActivity(), ProgressDetailActivity.class);
        intent.putExtra("loan_id", dataInfoList.get(position-1).getId());
        startActivity(intent);
    }

    @Override
    public void OnRefresh() {
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(), userId, currentProgress ,
                1, false, LoanInfo.class, new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        now_page = 1;
                        progressRlv.onRefreshComplete();
                        refreshDataInfoList = ((LoanInfo) data).getData().getLoan_list();
                        if (refreshDataInfoList == null || refreshDataInfoList.size() == 0) {
                            return;
                        }
                        if (progressFragmentAdapter == null) {
                            progressFragmentAdapter = new ProgressFragmentAdapter(getActivity(), refreshDataInfoList);
                            progressRlv.setAdapter(progressFragmentAdapter);
                        } else {
                            progressFragmentAdapter.refresh(refreshDataInfoList);
                        }
                        progressRlv.showFooterResult(now_page <= (((LoanInfo) data).getData().getLoan_total() / Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        progressRlv.onRefreshComplete();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(), userId, currentProgress,
                ++now_page, false, LoanInfo.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        progressRlv.onLoadComplete();
                        loadMoreDataInfoList = ((LoanInfo) data).getData().getLoan_list();
                        if (loadMoreDataInfoList == null || loadMoreDataInfoList.size() == 0) {
                            progressRlv.showFooterResult(false);
                            return;
                        }
                        progressFragmentAdapter.addData(loadMoreDataInfoList);
                        progressRlv.showFooterResult(now_page <= (((LoanInfo) data).getData().getLoan_total() /  Constants.PAGE_SIZE));
                    }

                    @Override
                    public void onError(String error) {
                        progressRlv.onLoadComplete();
                    }
                });

    }
}
