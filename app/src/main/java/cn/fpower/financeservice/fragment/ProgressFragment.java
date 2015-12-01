package cn.fpower.financeservice.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.view.widget.RefreshListView;

/**
 * Created by ll on 2015/11/26.
 */
public class ProgressFragment extends BaseFragment implements View.OnClickListener {

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

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_progress, null);
        return ll;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("进度");
        progressAll.setOnClickListener(this);
        progressChecking.setOnClickListener(this);
        progressChecked.setOnClickListener(this);
        progressCheckOk.setOnClickListener(this);
        currentRb = progressAll;
        currentView = line1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_progress_all:
                showView(line1, progressAll);
                break;
            case R.id.fragment_progress_checking:
                showView(line2, progressChecking);
                break;
            case R.id.fragment_progress_checked:
                showView(line3, progressChecked);
                break;
            case R.id.fragment_progress_check_ok:
                showView(line4, progressCheckOk);
                break;
        }
    }

    private void showView(View view, RadioButton rb) {
        currentRb.setChecked(false);
        rb.setChecked(true);
        currentRb = rb;
        currentView.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
        currentView = view;
    }
}
