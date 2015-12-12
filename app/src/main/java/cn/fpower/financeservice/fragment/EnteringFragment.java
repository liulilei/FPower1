package cn.fpower.financeservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.LoanPara;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.KeyBoardUtils;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class EnteringFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;
    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;
    @ViewInject(R.id.info_money)
    private EnteringSettingView info_money;
    @ViewInject(R.id.info_mobile)
    private EnteringSettingView info_mobile;
    @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;
    @ViewInject(R.id.info_fanchan)
    private EnteringSettingView info_fanchan;
    @ViewInject(R.id.info_pay)
    private EnteringSettingView info_pay;
    @ViewInject(R.id.info_qudao)
    private EnteringSettingView info_qudao;
    private final int CODE_NAME = 0;
    private final int CODE_MONEY = 1;
    private final int CODE_MOBILE = 2;
    private final int CODE_ADDR = 3;
    private final int CODE_FANCHAN = 4;
    private final int CODE_PAY = 5;
    private final int CODE_QUDAO = 6;


    LoanPara loanPara = new LoanPara();
    private Dialog mDialog;

    @ViewInject(R.id.submit)
    private Button submit;


    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_entering, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.entering));
        info_name.setOnClickListener(this);
        info_money.setOnClickListener(this);
        info_mobile.setOnClickListener(this);
        info_addr.setOnClickListener(this);
        info_fanchan.setOnClickListener(this);
        info_pay.setOnClickListener(this);
        info_qudao.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getValue());
        intent.putExtra("inputType", inputType);
        intent.setClass(getContext(), InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.info_name:
                jump(info_name, CODE_NAME, 0);
                break;
            case R.id.info_money:
                jump(info_money, CODE_MONEY, EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case R.id.info_mobile:
                jump(info_mobile, CODE_MOBILE, EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case R.id.info_addr:
                //下拉 控件
                info_addr.setValue("北京市 北京市 东城区");
                break;
            case R.id.info_fanchan:
                mDialog = createSexDialog("选择房产", info_fanchan);
                mDialog.show();
                break;
            case R.id.info_pay:
                mDialog = createSexDialog("选择贷款", info_pay);
                mDialog.show();
                break;
            case R.id.info_qudao:
                jump(info_qudao, CODE_QUDAO, 0);
                break;
            case R.id.submit:
                //渠道默认为空，可以不传
                loanPara.user_id = FSApplication.getInstance().getUserInfo().getData().getId();
                loanPara.realname = info_name.getValue();
                loanPara.mobile = info_mobile.getValue();
                loanPara.money = info_money.getValue();
                loanPara.province_id = "110000";
                loanPara.city_id = "110100";
                loanPara.district_id = "110101";
                loanPara.address=info_addr.getValue();
                loanPara.is_housing = info_fanchan.getKey() + "";
                loanPara.is_loan = info_pay.getKey() + "";
                try {
                    loanPara.checkLoan();
                } catch (Exception e) {
                    ToastUtils.show(getActivity(), e.getMessage());
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().create_loan(getActivity(), loanPara,
                        true, UserInfo.class, new ManagerDataListener() {

                            @Override
                            public void onSuccess(Object data) {


                                //清空所有数据
                                info_name.clear();
                                info_money.clear();
                                info_mobile.clear();
                                info_addr.clear();
                                info_fanchan.clear();
                                info_pay.clear();
                                info_qudao.clear();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });

                break;
        }
    }

    String[] isHas = {"有", "无"};

    protected Dialog createSexDialog(String title, final EnteringSettingView esView) {
        return new AlertDialog.Builder(getActivity()).setCancelable(true)
                .setTitle(title)
                .setItems(isHas, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        esView.setValue((which == 0) ? "1" : "0", isHas[which]);
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String result = data.getExtras().getString("result");
            switch (requestCode) {
                case CODE_NAME:
                    info_name.setValue(result);
                    break;
                case CODE_MONEY:
                    info_money.setValue(result);
                    break;
                case CODE_MOBILE:
                    info_mobile.setValue(result);
                    break;
                case CODE_QUDAO:
                    info_qudao.setValue(result);
                    break;
            }
        }
    }
}
