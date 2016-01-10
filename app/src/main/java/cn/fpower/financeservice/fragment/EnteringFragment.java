package cn.fpower.financeservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.LoanPara;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.InfoInputActivity;
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
    @ViewInject(R.id.info_addrdetail)
    private EnteringSettingView info_addrdetail;
    @ViewInject(R.id.info_fanchan)
    private EnteringSettingView info_fanchan;
    @ViewInject(R.id.info_pay)
    private EnteringSettingView info_pay;
    @ViewInject(R.id.info_qudao)
    private EnteringSettingView info_qudao;

    LoanPara loanPara = new LoanPara();
    private Dialog mDialog;

    @ViewInject(R.id.submit)
    private Button submit;

    private OptionsPickerView optionsPickerView;
    private ProvinceData provinceData;

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_entering, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(R.string.entering);
        info_name.setOnClickListener(this);
        info_money.setOnClickListener(this);
        info_mobile.setOnClickListener(this);
        info_addr.setOnClickListener(this);
        info_addrdetail.setOnClickListener(this);
        info_fanchan.setOnClickListener(this);
        info_pay.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        initPro();
        initQudao();
    }

    public void initQudao(){
        if (FSApplication.getInstance().getUserInfo() != null) {
            info_qudao.setValue(FSApplication.getInstance().getUserInfo().getData().getShop_id() + "",
                    FSApplication.getInstance().getUserInfo().getData().getShop_name() );
        }
    }

    private void initPro() {
        optionsPickerView = new OptionsPickerView(getActivity());
        provinceData = FSApplication.getInstance().getProvinceData();
        //三级联动效果
        optionsPickerView.setPicker(provinceData.getOptions1Items(), provinceData.getOptions2Items(), provinceData.getOptions3Items(), true);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        optionsPickerView.setCyclic(false);
        //设置默认选中的三级项目
        optionsPickerView.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = provinceData.getOptions1Items().get(options1).name
                        + provinceData.getOptions2Items().get(options1).get(option2).name
                        + provinceData.getOptions3Items().get(options1).get(option2).get(options3).name;

                String key = provinceData.getOptions1Items().get(options1).code + ","
                        + provinceData.getOptions2Items().get(options1).get(option2).code + ","
                        + provinceData.getOptions3Items().get(options1).get(option2).get(options3).code;
                info_addr.setValue(key, tx);
            }
        });
    }

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getKey());
        intent.putExtra("inputType", inputType);
        intent.setClass(getContext(), InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.info_name:
                jump(info_name, Constants.CODE_NAME, 0);
                break;
            case R.id.info_money:
                jump(info_money, Constants.CODE_MONEY, EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case R.id.info_mobile:
                jump(info_mobile, Constants.CODE_MOBILE, EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case R.id.info_addr:
                optionsPickerView.show();
                break;
            case R.id.info_addrdetail:
                jump(info_addrdetail, Constants.CODE_ADDRDETAIL, 0);
                break;
            case R.id.info_fanchan:
                mDialog = createSexDialog("选择房产", info_fanchan);
                mDialog.show();
                break;
            case R.id.info_pay:
                mDialog = createSexDialog("选择贷款", info_pay);
                mDialog.show();
                break;
            case R.id.submit:
                //渠道默认为空，可以不传
                loanPara.user_id = FSApplication.getInstance().getUserInfo().getData().getId();
                loanPara.realname = info_name.getKey();
                loanPara.mobile = info_mobile.getKey();
                loanPara.money = info_money.getKey();
                String[] keys = info_addr.getKey().split(",");
                if (keys != null && keys.length == 3) {
                    loanPara.province_id = keys[0];
                    loanPara.city_id = keys[1];
                    loanPara.district_id = keys[2];
                }
                loanPara.address = info_addrdetail.getKey();
                loanPara.is_housing = info_fanchan.getKey() + "";
                loanPara.is_loan = info_pay.getKey() + "";
                loanPara.channel = info_qudao.getKey();
                try {
                    loanPara.checkLoan();
                } catch (Exception e) {
                    ToastUtils.show(getActivity(), e.getMessage());
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().create_loan(getActivity(), loanPara,
                        true,  new ManagerStringListener() {

                            @Override
                            public void onSuccess(String data) {
                                //清空所有数据
                                info_name.clear();
                                info_money.clear();
                                info_mobile.clear();
                                info_addr.clear();
                                info_fanchan.clear();
                                info_pay.clear();
                                info_addrdetail.clear();
                                loanPara = new LoanPara();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });

                break;
        }
    }


    protected Dialog createSexDialog(String title, final EnteringSettingView esView) {
        return new AlertDialog.Builder(getActivity()).setCancelable(true)
                .setTitle(title)
                .setItems(Constants.isHas, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        esView.setValue((which == 0) ? "1" : "0", Constants.isHas[which]);
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String result = data.getStringExtra("result");
            switch (requestCode) {
                case Constants.CODE_NAME:
                    info_name.setValue(result);
                    break;
                case Constants.CODE_MONEY:
                    if(TextUtils.isEmpty(result)){
                        info_money.setValue(result);
                    }else {
                        info_money.setValue(result, result + "元");
                    }
                    break;
                case Constants.CODE_MOBILE:
                    info_mobile.setValue(result);
                    break;
                case Constants.CODE_ADDRDETAIL:
                    info_addrdetail.setValue(result);
                    break;
            }
        }
    }
}
