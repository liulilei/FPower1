package cn.fpower.financeservice.view.progress;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.LoanDetail;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.TelephonyUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

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

    @ViewInject(R.id.progress_name)
    private TextView progressName;

    @ViewInject(R.id.progress_phone)
    private ImageView progressPhone;

    @ViewInject(R.id.progress_state_tv1)
    private TextView progressStateTv1;

    @ViewInject(R.id.progress_time1)
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

    @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;
    @ViewInject(R.id.info_fanchan)
    private EnteringSettingView info_fanchan;
    @ViewInject(R.id.info_pay)
    private EnteringSettingView info_pay;
    @ViewInject(R.id.info_qudao)
    private EnteringSettingView info_qudao;

    private int loanId;

    private LoanDetail.Data loanDetail;
    private ProvinceData provinceData;
    private MyAlertDialog dialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_progress_detail;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        progressPhone.setOnClickListener(this);
        title.setText("申请详情");
        loanId = getIntent().getIntExtra("loan_id", -1);
        provinceData = FSApplication.getInstance().getProvinceData();
    }

    @Override
    protected void initData() {
        super.initData();
        if (FSApplication.getInstance().getUserInfo() != null) {
            info_qudao.setValue(FSApplication.getInstance().getUserInfo().getData().getShop_id() + "",
                    FSApplication.getInstance().getUserInfo().getData().getShop_name() );
        }
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
        progressName.setText(loanDetail.realname);
        progressTime1.setText(TimeUtils.fullTimeAndDay(loanDetail.addtime));
        switch (loanDetail.process) {
            case Constants.ProgressStatus.AUDIT:
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));

                progress_state_iv2.setImageResource(R.mipmap.progress_fail);
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressStateTv2.setTextColor(getResources().getColor(R.color.progress_audit_success));
                progressTime2.setText("待定");

                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                progressStateTv3.setText(MappingManager.getProcess(Constants.ProgressStatus.APPLY_SUCCESS));
                progressTime3.setText("待定");
                break;
            case Constants.ProgressStatus.AUDIT_SUCCESS:
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));

                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressStateTv2.setTextColor(getResources().getColor(R.color.progress_apply_success));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));

                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                progressStateTv3.setText(MappingManager.getProcess(Constants.ProgressStatus.APPLY_SUCCESS));
                progressTime3.setText("待定");
                break;
            case Constants.ProgressStatus.AUDIT_FAIL:
                //进度2隐藏，3显示
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                progress_state_iv2.setVisibility(View.GONE);
                progressStateTv2.setText("");
                progressTime2.setText("");

                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                progressStateTv3.setText(MappingManager.getProcess(loanDetail.process));
                progressStateTv3.setTextColor(getResources().getColor(R.color.progress_audit_fail));
                progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_fail_time));
                break;
            case Constants.ProgressStatus.APPLY_SUCCESS:
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_select));

                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressStateTv2.setTextColor(getResources().getColor(R.color.progress_apply_success));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));

                progress_state_iv3.setImageResource(R.mipmap.check);
                progressStateTv3.setText(MappingManager.getProcess(Constants.ProgressStatus.APPLY_SUCCESS));
                progressStateTv3.setTextColor(getResources().getColor(R.color.progress_apply_success));
                progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_success_time));
                break;
            case Constants.ProgressStatus.APPLY_FAIL:
                line1.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line2.setBackgroundColor(getResources().getColor(R.color.progress_line_select));
                line3.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));
                line4.setBackgroundColor(getResources().getColor(R.color.progress_line_normal));

                progress_state_iv2.setImageResource(R.mipmap.progress_ok);
                progressStateTv2.setText(MappingManager.getProcess(Constants.ProgressStatus.AUDIT_SUCCESS));
                progressStateTv2.setTextColor(getResources().getColor(R.color.progress_apply_success));
                progressTime2.setText(TimeUtils.fullTimeAndDay(loanDetail.audit_success_time));

                progress_state_iv3.setImageResource(R.mipmap.progress_fail);
                progressStateTv3.setText(MappingManager.getProcess(Constants.ProgressStatus.APPLY_FAIL));
                progressStateTv3.setTextColor(getResources().getColor(R.color.progress_apply_fail));
                progressTime3.setText(TimeUtils.fullTimeAndDay(loanDetail.apply_fail_time));
                break;
        }
        String addr = provinceData.getProvinceAddress(
                loanDetail.province_id,
                loanDetail.city_id,
                loanDetail.district_id);
        info_addr.setValue(addr + " " + loanDetail.address);
        info_fanchan.setValue(loanDetail.is_housing == 0 ? "无" : "有");
        info_pay.setValue(loanDetail.is_loan == 0 ? "无" : "有");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.progress_phone:
                if (dialog == null) {
                    dialog = new MyAlertDialog(act).setTitle(StringUtils.getPhoneFormat(loanDetail.mobile)).setRightButton("呼叫", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TelephonyUtils.callPhone(act, loanDetail.mobile);
                        }
                    }).setLeftButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
                dialog.show();
                break;
        }
    }
}
