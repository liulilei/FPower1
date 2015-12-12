package cn.fpower.financeservice.view.me;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.widget.ClearEditText;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * 完善信息
 */
public class MeInfoActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;


    @ViewInject(R.id.img_login)
    private ImageView img_login;

    @ViewInject(R.id.submit)
    private Button submit;

    @ViewInject(R.id.img_right)
    private ImageView img_right;

    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;

    @ViewInject(R.id.info_birthday)
    private EnteringSettingView info_birthday;

    @ViewInject(R.id.info_sex)
    private EnteringSettingView info_sex;

    @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;
    private UserInfo userInfo;
    String[] sexs={"男","女"};
    String sex = "1"; //默认是男
    private final int CODE_NAME = 0;

    @Override
    protected int initLayout() {
        return R.layout.activity_userinfo;
    }


    @Override
    protected void initView() {
        super.initView();
        back.setVisibility(View.GONE);
        title.setText("完善信息");
        info_sex.setValue("男");
        submit.setOnClickListener(this);
        img_right.setOnClickListener(this);
        info_name.setOnClickListener(this);
        info_birthday.setOnClickListener(this);
        info_sex.setOnClickListener(this);
        info_addr.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //假如已经登陆
        if(FSApplication.getInstance().isLogin()){
            if(FSApplication.getInstance().getUserInfo().getData()!=null) {
                info_name.setValue(FSApplication.getInstance().getUserInfo().getData().getUsername());
                info_birthday.setValue(FSApplication.getInstance().getUserInfo().getData().getBirthday());
                info_sex.setValue(FSApplication.getInstance().getUserInfo().getData().getSex()==1?"男":"女");
                info_addr.setValue(FSApplication.getInstance().getUserInfo().getData().getProvince_id()+"");
                ImageUtils.displayImageRoundImg(R.mipmap.ad1, NetApi.URL + FSApplication.getInstance().getUserInfo().getData().getFace(), img_login);
            }
        }
    }

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getValue());
        intent.putExtra("inputType", inputType);
        intent.setClass(act, InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                //验证，姓名，生日，城市不能为空，性别默认是男，头像默认为空，可以不传
                String user_id = FSApplication.getInstance().getUserInfo().getData().getId()+"";
                String face = "";
                String username = info_name.getValue();
                if (TextUtils.isEmpty(username)) {
                    ToastUtils.show(this, R.string.input_name);
                    return;
                }
                String birthday = info_birthday.getValue();
                if (TextUtils.isEmpty(birthday)) {
                    ToastUtils.show(this, R.string.input_bri);
                    return;
                }
                String province_id = "110000";
                String city_id = "110100";
                String district_id = "110101";
                FinanceManagerControl.getFinanceServiceManager().complete_user_info(act, user_id, face,
                        username, birthday, sex, province_id,
                        city_id, district_id,
                 true,  UserInfo.class, new ManagerDataListener(){

                            @Override
                            public void onSuccess(Object data) {
                                userInfo = (UserInfo) data;
                                FSApplication.getInstance().setUserInfo(userInfo);
                                FSApplication.getInstance().setIsLogin(true);
                                SpUtils.putString(act, Constants.MOBLEE, userInfo.getData().getMobile());
                              //  SpUtils.putString(act, Constants.PASSWD, view_pwd.getText().toString());
                                IntentUtils.startActivity(act, HomeActivity.class);
                                finish();

                            }

                            @Override
                            public void onError(String error) {

                            }
                        });

                break;
            case R.id.img_right:
                if (mDialog == null) {
                    mDialog = createDialog("选择图片");
                }
                mDialog.show();
                break;
            case R.id.info_name:
                jump(info_name, CODE_NAME, 0);
                break;
            case R.id.info_birthday:
                //下拉控件
                info_birthday.setValue("1989-09-27");
                break;
            case R.id.info_sex:
                mDialog = createSexDialog("选择性别");
                mDialog.show();
                break;
            case R.id.info_addr:
               //下拉 控件
                info_addr.setValue("北京市 北京市 东城区");
                break;
        }
    }

    private Dialog mDialog;

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] items = getResources().getStringArray(R.array.select_dialog_items);
                        ToastUtils.show(act, items[which]);
                    }
                })
                .create();
    }

    protected Dialog createSexDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(sexs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            sex="1";
                        } else {
                            sex="2";
                        }
                        info_sex.setValue(sexs[which]);
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
            }
        }
    }
}
