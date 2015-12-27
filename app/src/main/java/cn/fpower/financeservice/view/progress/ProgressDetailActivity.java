package cn.fpower.financeservice.view.progress;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.MappingManager;
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

    @ViewInject(R.id.progress_state_tv1)
    private TextView progressStateTv1;

    @ViewInject(R.id.progress_tqime1)
    private TextView progressTime1;

    @ViewInject(R.id.progress_state_tv2)
    private TextView progressStateTv2;

    @ViewInject(R.id.progress_time2)
    private TextView progressTime2;


    @ViewInject(R.id.line1)
    private View line1;

    @ViewInject(R.id.line2)
    private View line2;

    @ViewInject(R.id.line3)
    private View line3;

    @ViewInject(R.id.line4)
    private View line4;

    @ViewInject(R.id.progress_state_iv1)
    private ImageView progress_state_iv1;

    @ViewInject(R.id.progress_state_iv2)
    private ImageView progress_state_iv2;

    @ViewInject(R.id.progress_state_iv3)
    private ImageView progress_state_iv3;

    @ViewInject(R.id.progress_state_tv3)
    private TextView progressStateTv3;

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
        ImageUtils.displayImageRoundImg(R.mipmap.moren, "", progressHeader);
        progressName.setText(loanDetail.realname);
        progressTime1.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
        progressStateTv1.setText("待审核");
        switch(loanDetail.process){
            case Constants.ProgressStatus.AUDIT:
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                progress_state_iv2.setImageResource(R.mipmap.progress_fail);
                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                break;
            case Constants.ProgressStatus.AUDIT_SUCCESS:
                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));

                progressStateTv2.setText(MappingManager.getProcess(loanDetail.process));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));
                break;
            case Constants.ProgressStatus.AUDIT_FAIL:
                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));

                progressStateTv2.setText(MappingManager.getProcess(loanDetail.process));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_fail_time));
                break;
            case Constants.ProgressStatus.APPLY_SUCCESS:
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));

                progressStateTv3.setText(MappingManager.getProcess(loanDetail.process));
                progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_success_time));

                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progress_state_iv3.setImageResource(R.mipmap.progress_ok);
                break;
            case Constants.ProgressStatus.APPLY_FAIL:
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));
                progressStateTv3.setText(MappingManager.getProcess(loanDetail.process));
                progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_fail_time));

                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progress_state_iv3.setImageResource(R.mipmap.progress_ok);
                break;
        }

        /*if (loanDetail.process==Constants.ProgressStatus.AUDIT) {

        } else if (loanDetail.process==Constants.ProgressStatus.AUDIT_SUCCESS||
                loanDetail.process==Constants.ProgressStatus.AUDIT_SUCCESS) {
            long audit=loanDetail.audit_success_time > 0?loanDetail.audit_success_time:loanDetail.audit_fail_time;
            progressTime2.setText(TimeUtils.fullTimeAndDay(audit));
            line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
            line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
           // progressStateIv.setImageResource(R.mipmap.progress_ok);
          //  progressStateTv.setText("申请成功");

        } else {
            long audit=loanDetail.audit_success_time>0?loanDetail.audit_success_time:loanDetail.audit_fail_time;
            progressTime2.setText(TimeUtils.fullTimeAndDay(audit));
            long apply=loanDetail.apply_success_time>0?loanDetail.apply_success_time:loanDetail.apply_fail_time;
            progressTime2.setText(TimeUtils.fullTimeAndDay(audit));
            progressTime3.setText(TimeUtils.fullTimeAndDay(apply));
        }*/
        progressAddress.setText(loanDetail.address);
        progressHasHouse.setText(loanDetail.is_housing == 0 ? "无" : "有");
        progressHasMoney.setText(loanDetail.is_loan == 0 ? "无" : "有");
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
