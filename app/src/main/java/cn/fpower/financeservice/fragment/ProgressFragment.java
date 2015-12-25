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
import cn.fpower.financeservice.adapter.SuccessExampleAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.CaseListInfo;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.mode.LoanInfo;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.progress.ProgressDetailActivity;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * Created by ll on 2015/11/26.
 */
public class ProgressFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

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

    private static final int PROGRESS_ALL = 0;

    private static final int PROGRESS_CHECKING = 1;

    private static final int PROGRESS_CHECKED = -1;

    private static final int PROGRESS_CHECK_OK = -2;

    private int currentProgress = -1;

    private int now_page = 1;

    private List<DataInfo> dataInfoList;

    private List<DataInfo> refreshDataInfoList;

    private List<DataInfo> loadMoreDataInfoList;

    private ProgressFragmentAdapter progressFragmentAdapter;

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_progress, null);
        return ll;
    }

    @Override
    protected void initView() {
        currentProgress = PROGRESS_ALL;
        back.setVisibility(View.GONE);
        title.setText("进度");
        progressAll.setOnClickListener(this);
        progressChecking.setOnClickListener(this);
        progressChecked.setOnClickListener(this);
        progressCheckOk.setOnClickListener(this);
        progressRlv.setOnItemClickListener(this);
        currentRb = progressAll;
        currentView = line1;
        currentProgress = PROGRESS_ALL;
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(), FSApplication.getInstance().getUserInfo().getData().getId() + "",
                currentProgress + "", now_page, true, LoanInfo.class, new ManagerDataListener() {
                    @Override
                    public void onSuccess(Object data) {
                        dataInfoList = ((LoanInfo) data).getData().getLoan_list();

                        if (dataInfoList == null || dataInfoList.size() == 0) {
                            ToastUtils.show(getActivity(), "没有数据");
                            progressRlv.showFooterResult(false);
                            return;
                        }
//                        successExampleAdapter = new SuccessExampleAdapter(getActivity(), dataInfoList);
//                        successExampleRlv.setAdapter(successExampleAdapter);
//                        successExampleRlv.showFooterResult(now_page <=  (((CaseListInfo) data).getCase_total() / 10));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
        progressRlv.setAdapter(new ProgressFragmentAdapter(getActivity(), null));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_progress_all:
                showView(line1, progressAll);
                currentProgress = PROGRESS_ALL;
                break;
            case R.id.fragment_progress_checking:
                showView(line2, progressChecking);
                currentProgress = PROGRESS_CHECKING;
                break;
            case R.id.fragment_progress_checked:
                showView(line3, progressChecked);
                currentProgress = PROGRESS_CHECKED;
                break;
            case R.id.fragment_progress_check_ok:
                showView(line4, progressCheckOk);
                currentProgress = PROGRESS_CHECK_OK;
                break;
        }
        progressRlv.setAdapter(new ProgressFragmentAdapter(getActivity(), null));
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
        startActivity(new Intent(getActivity(), ProgressDetailActivity.class));
    }
}
