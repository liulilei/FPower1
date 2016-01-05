package cn.fpower.financeservice.view.me;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.BitmapUtils;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
import cn.fpower.financeservice.utils.SpUtils;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.home.HomeActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * 完善个人信息
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
    String sex = "1"; //默认是男
    private final int CODE_NAME = 0;

    private OptionsPickerView optionsPickerView;
    private ProvinceData provinceData;


    TimePickerView pvTime;

    @Override
    protected int initLayout() {
        return R.layout.activity_userinfo;
    }


    @Override
    protected void initView() {
        super.initView();
        back.setOnClickListener(this);
        title.setText("个人信息");
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
        initPro();
        initTime();
        //假如已经登陆
        if (FSApplication.getInstance().getUserInfo() != null) {
            info_name.setValue(FSApplication.getInstance().getUserInfo().getData().getUsername());
            if(!FSApplication.getInstance().getUserInfo().getData().getBirthday().equals("0000-00-00")) {
                info_birthday.setValue(FSApplication.getInstance().getUserInfo().getData().getBirthday());
            }
            info_sex.setValue(MappingManager.getSex(FSApplication.getInstance().getUserInfo().getData().getSex()));
            String addr = provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getProvince_id() + "") +
                    provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getCity_id() + "") +
                    provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getDistrict_id() + "");
            String key = FSApplication.getInstance().getUserInfo().getData().getProvince_id() + ","
                    + FSApplication.getInstance().getUserInfo().getData().getCity_id() + ","
                    + FSApplication.getInstance().getUserInfo().getData().getDistrict_id();
            info_addr.setValue(key, addr);
            ImageUtils.displayImageRoundImg(R.mipmap.moren, NetApi.URL + FSApplication.getInstance().getUserInfo().getData().getFace(), img_login);
        }
    }

    private void initTime() {
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setRange(TimeUtils.getYear() - 100, TimeUtils.getYear() - 10);
        pvTime.setTime(new Date());
        pvTime.setCancelable(true);
        pvTime.setCyclic(false);//不许循环
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                info_birthday.setValue(TimeUtils.getTime(date));
            }
        });
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
                info_addr.setValue(key, tx);
            }
        });
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
                String user_id = FSApplication.getInstance().getUserInfo().getData().getId() + "";

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
                String face = BitmapUtils.Bitmap2StrByBase64(picturePath);
                String[] keys = info_addr.getKey().split(",");
                if (keys == null || keys.length < 3) {
                    ToastUtils.show(this, R.string.input_addr);
                    return;
                }
                String province_id = keys[0];
                String city_id = keys[1];
                String district_id = keys[2];
                FinanceManagerControl.getFinanceServiceManager().complete_user_info(act, user_id, face,
                        username, birthday, sex, province_id,
                        city_id, district_id,
                        true, UserInfo.class, new ManagerDataListener() {

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
                mDialog = createDialog("选择图片");
                mDialog.show();
                break;
            case R.id.info_name:
                jump(info_name, CODE_NAME, 0);
                break;
            case R.id.info_birthday:
                //下拉控件
                pvTime.show();
                break;
            case R.id.info_sex:
                mDialog = createSexDialog("选择性别");
                mDialog.show();
                break;
            case R.id.info_addr:
                optionsPickerView.show();
                break;
            case R.id.title_bar_back:
                this.finish();
                break;
        }
    }

    private Dialog mDialog;
    private String picturePath;

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PickPhotoUtil.getInstance().localPhoto(act);
                        } else {
                            picturePath = PickPhotoUtil.getInstance().takePhoto(act);
                        }
                    }
                })
                .create();
    }

    protected Dialog createSexDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(Constants.sexs, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            sex = "1";
                        } else {
                            sex = "2";
                        }
                        info_sex.setValue(Constants.sexs[which]);
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE_NAME:
                if (data != null) {
                    String result = data.getStringExtra("result");
                    info_name.setValue(result);
                }
                break;
            case PickPhotoUtil.PICKPHOTO_LOCAL:
                if (resultCode == RESULT_OK) {
                    picturePath = PickPhotoUtil.getInstance().getPathNameFromUri(this, data.getData());
                    ImageUtils.displayImageRoundImg(R.mipmap.moren, "file://" + picturePath, img_login);
                }
                break;
            case PickPhotoUtil.PICKPHOTO_TAKE:
                if (resultCode == RESULT_OK) {
                    PickPhotoUtil.galleryAddPic(this, picturePath);
                    ImageUtils.displayImageRoundImg(R.mipmap.moren, "file://" + picturePath, img_login);
                }
                break;
        }
    }
}
