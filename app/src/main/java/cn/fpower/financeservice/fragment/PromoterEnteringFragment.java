package cn.fpower.financeservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.LoanPara;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.ProvinceInfo;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.ImgSelActivity;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class PromoterEnteringFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    private List<String> list = new ArrayList<String>();

    @ViewInject(R.id.noScrollgridview)
    private GridView noScrollgridview;

    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;

    @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;

    @ViewInject(R.id.info_addrdetail)
    private EnteringSettingView info_addrdetail;

    @ViewInject(R.id.longitude)
    private EnteringSettingView info_longitude;

    @ViewInject(R.id.latitude)
    private EnteringSettingView info_latitude;

    @ViewInject(R.id.info_username)
    private EnteringSettingView info_username;

    @ViewInject(R.id.info_mobile)
    private EnteringSettingView info_mobile;

    private final int CODE_NAME = 0;
    private final int CODE_MOBILE = 2;
    private final int CODE_ADDRDETAIL = 3;
    private final int CODE_USERNAME = 6;

    @ViewInject(R.id.submit)
    private Button submit;

    private GridAdapter adapter;

    private AMapLocationClient aMapLocationClient;

    private OptionsPickerView optionsPickerView;
    private ProvinceData provinceData;

    LoanPara loanPara = new LoanPara();

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.activity_promotion_result, null);
        return rootView;
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            if (list.size() == 5) {
                return 5;
            }
            return list.size() + 1;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_imageview, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == list.size()) {
                ImageUtils.displayImageRoundImg(R.mipmap.ad1, "drawable://" + R.mipmap.icon_addpic, holder.image);
                if (position == 5) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                ImageUtils.displayImageRoundImg(R.mipmap.ad1, "file://" + list.get(position), holder.image);
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String result = data.getExtras().getString("result");
            switch (requestCode) {
                case PickPhotoUtil.PICKPHOTO_LOCAL:
                    if (resultCode == getActivity().RESULT_OK) {
                        String picturePath = PickPhotoUtil.getInstance().getPathNameFromUri(getActivity(), data.getData());
                        list.add(picturePath);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case PickPhotoUtil.PICKPHOTO_TAKE:
                    if (resultCode == getActivity().RESULT_OK) {
                        PickPhotoUtil.galleryAddPic(getActivity(), photo);
                        list.add(photo);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case PickPhotoUtil.PICKPHOTO_DELETE:
                    if (resultCode == getActivity().RESULT_OK) {
                        int position = data.getExtras().getInt("position");
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case CODE_NAME:
                    info_name.setValue(result);
                    break;
                case CODE_USERNAME:
                    info_username.setValue(result);
                    break;
                case CODE_MOBILE:
                    info_mobile.setValue(result);
                    break;
                case CODE_ADDRDETAIL:
                    info_addrdetail.setValue(result);
                    break;
            }
        }
    }

    private String photo;

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("店面信息");
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getActivity());
        noScrollgridview.setAdapter(adapter);

        info_name.setOnClickListener(this);
        info_addr.setOnClickListener(this);
        info_addrdetail.setOnClickListener(this);
        info_mobile.setOnClickListener(this);
        info_username.setOnClickListener(this);
        submit.setOnClickListener(this);

        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    if (mDialog == null) {
                        mDialog = createDialog("选择图片");
                    }
                    mDialog.show();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ImgSelActivity.class);
                    intent.putExtra("photo", list.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, PickPhotoUtil.PICKPHOTO_DELETE);
                }
            }
        });

        aMapLocationClient = FSApplication.getInstance().getAMapLocationClient();
        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        info_latitude.setValue(amapLocation.getLatitude() + "");//获取经度
                        info_longitude.setValue(amapLocation.getLongitude() + "");//获取纬度
                    } else {
                    }
                }
                aMapLocationClient.stopLocation();
            }
        });
        aMapLocationClient.startLocation();
    }

    private Dialog mDialog;

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(getActivity()).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PickPhotoUtil.getInstance().localPhoto(getActivity());
                        } else {
                            photo = PickPhotoUtil.getInstance().takePhoto(getActivity());
                        }
                    }
                }).create();
    }

    @Override
    protected void initData() {
        optionsPickerView = new OptionsPickerView(getActivity());
        provinceData = FSApplication.getInstance().getProvinceData();
        //三级联动效果
        optionsPickerView.setPicker(provinceData.getOptions1Items(), provinceData.getOptions2Items(), provinceData.getOptions3Items(), true);
        //三级联动效果
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_addr:
                optionsPickerView.show();
                break;
            case R.id.info_name:
                jump(info_name, CODE_NAME, 0);
                break;
            case R.id.info_username:
                jump(info_username, CODE_USERNAME, 0);
                break;
            case R.id.info_mobile:
                jump(info_mobile, CODE_MOBILE, EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case R.id.info_addrdetail:
                jump(info_addrdetail, CODE_ADDRDETAIL, 0);
                break;
            case R.id.submit:
                loanPara.user_id = FSApplication.getInstance().getUserInfo().getData().getId();
                loanPara.name = info_name.getValue();
                loanPara.username = info_username.getValue();
                loanPara.mobile = info_mobile.getValue();
                String[] keys = info_addr.getKey().split(",");
                if (keys != null && keys.length == 3) {
                    loanPara.province_id = keys[0];
                    loanPara.city_id = keys[1];
                    loanPara.district_id = keys[2];
                }
                loanPara.address = info_addrdetail.getValue();
                loanPara.latitude = info_latitude.getValue();
                loanPara.longitude = info_longitude.getValue();
                try {
                    loanPara.checShop();
                } catch (Exception e) {
                    ToastUtils.show(getActivity(), e.getMessage());
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().create_shop(getActivity(), loanPara, "",
                        true, UserInfo.class, new ManagerDataListener() {

                            @Override
                            public void onSuccess(Object data) {
                                //清空所有数据
                                info_name.clear();
                                info_username.clear();
                                info_mobile.clear();
                                info_addr.clear();
                                info_longitude.clear();
                                info_latitude.clear();
                                info_addrdetail.clear();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });

                break;
        }
    }

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getValue());
        intent.putExtra("inputType", inputType);
        intent.setClass(getContext(), InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

}
