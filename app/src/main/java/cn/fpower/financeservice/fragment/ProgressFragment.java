package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.adapter.AllProgressFragmentAdapter;
import cn.fpower.financeservice.adapter.ProgressFragmentAdapter;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.CaseListInfo;
import cn.fpower.financeservice.mode.LoanInfo;
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

    private int now_page=1;

    private static final int PROGRESS_ALL = 1;

    private static final int PROGRESS_CHECKING = 2;

    private static final int PROGRESS_CHECKED = 3;

    private static final int PROGRESS_CHECK_OK = 4;

    private int currentProgress = -1;

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

    private void showList(){
        FinanceManagerControl.getFinanceServiceManager().loan_list(getActivity(),
                FSApplication.getInstance().getUserInfo().getData().getId() + "", "", now_page, true, LoanInfo.class, new ManagerDataListener() {

                    @Override
                    public void onSuccess(Object data) {
                        LoanInfo info = (LoanInfo) data;
                       // progressRlv.setAdapter(new AllProgressFragmentAdapter(getActivity(), info.getData().getLoan_list()));
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
