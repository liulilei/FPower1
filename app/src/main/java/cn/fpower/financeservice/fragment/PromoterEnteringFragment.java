package cn.fpower.financeservice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.manager.netmanager.ManagerStringListener;
import cn.fpower.financeservice.mode.LoanPara;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.UserInfo;
import cn.fpower.financeservice.utils.BitmapUtils;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
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

    //private Map<String,String> mapList=new LinkedHashMap<>();
    private List<String> list = new ArrayList<String>();

    @ViewInject(R.id.noScrollgridview)
    private GridView noScrollgridview;

    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;

    @ViewInject(R.id.info_addr)
    private EnteringSettingView info_addr;

    @ViewInject(R.id.info_addrdetail)
    private EnteringSettingView info_addrdetail;

   /* @ViewInject(R.id.longitude)
    private EnteringSettingView info_longitude;

    @ViewInject(R.id.latitude)
    private EnteringSettingView info_latitude;*/


    @ViewInject(R.id.longitude)
    private TextView longitude;

    @ViewInject(R.id.latitude)
    private TextView latitude;


    @ViewInject(R.id.info_username)
    private EnteringSettingView info_username;

    @ViewInject(R.id.info_mobile)
    private EnteringSettingView info_mobile;

    @ViewInject(R.id.img_loc)
    private ImageView img_loc;

    @ViewInject(R.id.submit)
    private Button submit;

    private GridAdapter adapter;

    private AMapLocationClient aMapLocationClient;

    private OptionsPickerView optionsPickerView;
    private ProvinceData provinceData;

    LoanPara loanPara = new LoanPara();

    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_promoterentering, null);
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
                ImageUtils.displayImageRoundImg(R.mipmap.ad, "drawable://" + R.mipmap.icon_addpic, holder.image);
                if (position == 5) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                ImageUtils.displayImageRoundImg(R.mipmap.ad, "file://" + list.get(position), holder.image);
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String result = "";
        if (data != null) {
            result = data.getStringExtra("result");
        }
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
            case Constants.CODE_NAME:
                info_name.setValue(result);
                break;
            case Constants.CODE_USERNAME:
                info_username.setValue(result);
                break;
            case Constants.CODE_MOBILE:
                info_mobile.setValue(result);
                break;
            case Constants.CODE_ADDRDETAIL:
                info_addrdetail.setValue(result);
                break;
        }
    }

    private String photo;
    private Animation animation;

    @Override
    protected void initView() {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.loading_home);
        back.setVisibility(View.GONE);
        title.setText(R.string.entering);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getActivity());
        noScrollgridview.setAdapter(adapter);

        info_name.setOnClickListener(this);
        info_addr.setOnClickListener(this);
        info_addrdetail.setOnClickListener(this);
        info_mobile.setOnClickListener(this);
        info_username.setOnClickListener(this);
        submit.setOnClickListener(this);
        img_loc.setOnClickListener(this);
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
                        latitude.setText(amapLocation.getLatitude() + "");//获取经度
                        longitude.setText(amapLocation.getLongitude() + "");//获取纬度
                        img_loc.clearAnimation();
                        // ToastUtils.show(getActivity(),"定位成功");//getActivity()为空
                    } else {

                    }
                }
                aMapLocationClient.stopLocation();
            }
        });
        aMapLocationClient.startLocation();
    }

    private Dialog mDialog;

    private boolean isStorageState() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(getActivity()).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (isStorageState()) {
                                Intent mIntent = new Intent(Intent.ACTION_PICK);
                                mIntent.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(mIntent, PickPhotoUtil.PICKPHOTO_LOCAL);
                            } else {
                                Toast.makeText(getActivity(), "未找到SD卡", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (isStorageState()) {
                                String path = System.currentTimeMillis() + ".png";
                                File imgFile = new File(PickPhotoUtil.FILEDIR, path);
                                if (null != imgFile) {
                                    Intent mIntent = new Intent(
                                            "android.media.action.IMAGE_CAPTURE");
                                    mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                                    startActivityForResult(mIntent, PickPhotoUtil.PICKPHOTO_TAKE);
                                    photo = imgFile.getAbsolutePath();
                                } else {
                                    Toast.makeText(getActivity(), "创建图片对象有误", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "未找到SD卡", Toast.LENGTH_LONG).show();
                            }
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
            case R.id.img_loc:
                img_loc.startAnimation(animation);
                aMapLocationClient.startLocation();
                break;
            case R.id.info_addr:
                optionsPickerView.show();
                break;
            case R.id.info_name:
                jump(info_name, Constants.CODE_NAME, 0);
                break;
            case R.id.info_username:
                jump(info_username, Constants.CODE_USERNAME, 0);
                break;
            case R.id.info_mobile:
                jump(info_mobile, Constants.CODE_MOBILE, EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case R.id.info_addrdetail:
                jump(info_addrdetail, Constants.CODE_ADDRDETAIL, 0);
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
                loanPara.longitude = longitude.getText().toString();
                loanPara.latitude = latitude.getText().toString();
                loanPara.address = info_addrdetail.getValue();
                String imgs = "";
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        imgs += BitmapUtils.Bitmap2StrByBase64(list.get(i));
                    } else {
                        imgs += BitmapUtils.Bitmap2StrByBase64(list.get(i)) + "#####";
                    }
                }
                try {
                    loanPara.checkShop();
                } catch (Exception e) {
                    ToastUtils.show(getActivity(), e.getMessage());
                    return;
                }
                FinanceManagerControl.getFinanceServiceManager().create_shop(getActivity(), loanPara, imgs,
                        true,  new ManagerStringListener() {

                            @Override
                            public void onSuccess(String data) {
                                //清空所有数据
                                info_name.clear();
                                info_username.clear();
                                info_mobile.clear();
                                info_addr.clear();
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

    private void jump(EnteringSettingView esView, int code, int inputType) {
        Intent intent = new Intent();
        intent.putExtra("title", esView.getTitle());
        intent.putExtra("value", esView.getValue());
        intent.putExtra("inputType", inputType);
        intent.setClass(getContext(), InfoInputActivity.class);
        startActivityForResult(intent, code);
    }

}
