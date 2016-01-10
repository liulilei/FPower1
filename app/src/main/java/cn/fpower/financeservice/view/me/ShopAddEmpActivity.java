package cn.fpower.financeservice.view.me;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.LoanPara;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * 添加员工
 */
public class ShopAddEmpActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.submit)
    private Button submit;

    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;

    @ViewInject(R.id.info_mobile)
    private EnteringSettingView info_mobile;

   /* @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;

    @ViewInject(R.id.info_addrdetail)
    private EnteringSettingView info_addrdetail;*/

    private OptionsPickerView optionsPickerView;
    private ProvinceData provinceData;

    LoanPara loanPara=new LoanPara();
    @Override
    protected int initLayout() {
        return R.layout.activity_add_emp;
    }


    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText("添加员工");
        submit.setOnClickListener(this);
        info_name.setOnClickListener(this);
        info_mobile.setOnClickListener(this);
       /* info_addr.setOnClickListener(this);
        info_addrdetail.setOnClickListener(this);*/
    }

    @Override
    protected void initData() {
        super.initData();
        //initPro();
    }

    private void initPro() {
        optionsPickerView = new OptionsPickerView(this);
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
               // info_addr.setValue(key, tx);
            }
        });
    }

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getKey());
        intent.putExtra("inputType", inputType);
        intent.setClass(act, InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                loanPara.user_id = FSApplication.getInstance().getUserInfo().getData().getId();
                loanPara.username = info_name.getKey();
                loanPara.mobile = info_mobile.getKey();
              /*  String[] keys = info_addr.getKey().split(",");
                if (keys != null && keys.length == 3) {
                    loanPara.province_id = keys[0];
                    loanPara.city_id = keys[1];
                    loanPara.district_id = keys[2];
                }
                loanPara.address = info_addrdetail.getValue();*/
                try {
                    loanPara.checkEmp();
                } catch (Exception e) {
                    ToastUtils.show(this, e.getMessage());
                    return;
                }FinanceManagerControl.getFinanceServiceManager().create_employee(this, loanPara,
                    true, UserInfo.class, new ManagerDataListener() {

                        @Override
                        public void onSuccess(Object data) {
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                break;
            case R.id.info_name:
                jump(info_name, Constants.CODE_NAME, 0);
                break;
            case R.id.info_mobile:
                jump(info_mobile, Constants.CODE_MOBILE, EditorInfo.TYPE_CLASS_NUMBER);
                break;
/*            case R.id.info_addr:
                optionsPickerView.show();
                break;
            case R.id.info_addrdetail:
                jump(info_addrdetail, Constants.CODE_ADDRDETAIL, 0);
                break;*/
            case R.id.title_bar_back:
                setResult(RESULT_OK);
                this.finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String result = data.getStringExtra("result");
            switch (requestCode) {
                case  Constants.CODE_NAME:
                    info_name.setValue(result);
                    break;
                case  Constants.CODE_MOBILE:
                    info_mobile.setValue(result);
                    break;
/*                case  Constants.CODE_ADDRDETAIL:
                    info_addrdetail.setValue(result);
                    break;*/
            }
        }
    }
}
