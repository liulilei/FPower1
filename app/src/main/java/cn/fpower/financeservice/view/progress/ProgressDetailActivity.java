package cn.fpower.financeservice.view.progress;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.LoanDetail;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.BaseActivity;

/**
 * Created by ll on 2015/12/2.
 */
public class ProgressDetailActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.progress_time)
    private TextView progressTime;

    @ViewInject(R.id.progress_money)
    private TextView progressMoney;

    @ViewInject(R.id.progress_header)
    private ImageView progressHeader;

    @ViewInject(R.id.progress_name)
    private TextView progressName;

    @ViewInject(R.id.progress_phone)
    private ImageView progressPhone;

    @ViewInject(R.id.progress_tqime1)
    private TextView progressTime1;

    @ViewInject(R.id.progress_time2)
    private TextView progressTime2;

    @ViewInject(R.id.line1)
    private View line1;

    @ViewInject(R.id.line2)
    private View line2;

    @ViewInject(R.id.progress_state_iv)
    private ImageView progressStateIv;

    @ViewInject(R.id.progress_state_tv)
    private TextView progressStateTv;

    @ViewInject(R.id.progress_time3)
    private TextView progressTime3;

    @ViewInject(R.id.progress_address)
    private TextView progressAddress;

    @ViewInject(R.id.progress_has_house)
    private TextView progressHasHouse;

    @ViewInject(R.id.progress_has_money)
    private TextView progressHasMoney;

    @ViewInject(R.id.progress_has_chanel)
    private TextView progressHasChanel;

    private int loanId;

    private LoanDetail.Data loanDetail;

    @Override
    protected int initLayout() {
        return R.layout.activity_progress_detail;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        progressPhone.setOnClickListener(this);
        title.setText("进度");
        loanId = getIntent().getIntExtra("loan_id", -1);
    }

    @Override
    protected void initData() {
        super.initData();
        FinanceManagerControl.getFinanceServiceManager().loan_detail(this, loanId, true, LoanDetail.class, new ManagerDataListener() {
            @Override
            public void onSuccess(Object data) {
                loanDetail = ((LoanDetail) data).getData();
                if (loanDetail != null) {
                    showView(loanDetail);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showView(LoanDetail.Data loanDetail) {
        progressTime.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
        progressMoney.setText("￥" + loanDetail.money);
        ImageUtils.displayImageRoundImg(R.mipmap.ad1, "http://stimgcn1.s-msn.com/msnportal/fashion/2012/12/24/51478f07-382d-4da9-99b5-815dd2848aa8.jpg", progressHeader);
        progressName.setText(loanDetail.realname);
        if (loanDetail.process.equals(Constants.PROGRESS_CHECKING)) {
            progressTime1.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
            progressTime2.setText(TimeUtils.fullTimeAndDay(System.currentTimeMillis()));
            progressStateTv.setTextColor(getResources().getColor(R.color.progress_checking_tv));
            progressStateTv.setText("待审核");
        } else if (loanDetail.process.equals(Constants.PROGRESS_CHECKED) || loanDetail.process.equals(Constants.PROGRESS_CHECK_OK)) {
            progressTime1.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
            progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));
            progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_success_time));
            line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
            line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
            progressStateIv.setImageResource(R.mipmap.progress_success);
            progressStateTv.setText("申请成功");
            progressStateTv.setTextColor(getResources().getColor(R.color.progress_checked));
        } else {
            progressTime1.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
            progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_fail_time));
            progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_fail_time));
            progressStateTv.setText("申请失败");
            progressStateTv.setTextColor(getResources().getColor(R.color.progress_check_ok));
        }
        progressAddress.setText(loanDetail.address);
        progressHasHouse.setText(loanDetail.is_housing == 0 ? "没有" : "有");
        progressHasMoney.setText(loanDetail.is_loan == 0 ? "没有" : "有");
        progressHasChanel.setText(loanDetail.channel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.progress_phone:
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + loanDetail.mobile));
                startActivity(intent);
                break;
        }
    }
}
