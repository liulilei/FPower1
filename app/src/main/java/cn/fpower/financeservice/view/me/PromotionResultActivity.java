package cn.fpower.financeservice.view.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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
import cn.fpower.financeservice.mode.ProvinceInfo;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.ImgSelActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;
import cn.fpower.financeservice.mode.ProvinceInfo.Province;

/**
 * 推广员我的业绩
 */
public class PromotionResultActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    private List<String> list = new ArrayList<String>();

    @ViewInject(R.id.noScrollgridview)
    private GridView noScrollgridview;

    @ViewInject(R.id.info_add1)
    private EnteringSettingView locationTv;

    @ViewInject(R.id.info_add2)
    private EnteringSettingView locationDetialTv;

    @ViewInject(R.id.longitude)
    private EnteringSettingView longitudeTv;

    @ViewInject(R.id.latitude)
    private EnteringSettingView latitudeTv;


    private GridAdapter adapter;

    private AMapLocationClient aMapLocationClient;

    private OptionsPickerView optionsPickerView;

    private Gson gson;

    private ArrayList<Province> options1Items = new ArrayList<Province>();
    private ArrayList<ArrayList<Province>> options2Items = new ArrayList<ArrayList<Province>>();
    private ArrayList<ArrayList<ArrayList<Province>>> options3Items = new ArrayList<ArrayList<ArrayList<Province>>>();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PickPhotoUtil.PICKPHOTO_LOCAL:
                if (resultCode == RESULT_OK) {
                    String picturePath = PickPhotoUtil.getInstance().getPathNameFromUri(this, data.getData());
                    list.add(picturePath);
                    adapter.notifyDataSetChanged();
                }
                break;
            case PickPhotoUtil.PICKPHOTO_TAKE:
                if (resultCode == RESULT_OK) {
                    PickPhotoUtil.galleryAddPic(this, photo);
                    list.add(photo);
                    adapter.notifyDataSetChanged();
                }
                break;
            case PickPhotoUtil.PICKPHOTO_DELETE:
                if (resultCode == RESULT_OK) {
                    int position = data.getExtras().getInt("position");
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private String photo;

    @Override
    protected int initLayout() {
        return R.layout.activity_promotion_result;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("店面信息");
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        noScrollgridview.setAdapter(adapter);
        locationTv.setOnClickListener(this);
        locationDetialTv.setOnClickListener(this);
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
                    intent.setClass(act, ImgSelActivity.class);
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
                        latitudeTv.setValue(amapLocation.getLatitude() + "");//获取经度
                        longitudeTv.setValue(amapLocation.getLongitude() + "");//获取纬度
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
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PickPhotoUtil.getInstance().localPhoto(act);
                        } else {
                            photo = PickPhotoUtil.getInstance().takePhoto(act);
                        }
                    }
                }).create();
    }

    @Override
    protected void initData() {
        gson = new Gson();
        optionsPickerView = new OptionsPickerView(this);
        String city = StringUtils.getJsonFromAssets(this, "city.txt");
        String province = StringUtils.getJsonFromAssets(this, "provice.txt");
        String distric = StringUtils.getJsonFromAssets(this, "distric.txt");
        ProvinceInfo provinceInfo = gson.fromJson(province, ProvinceInfo.class);
        ProvinceInfo cityInfo = gson.fromJson(city, ProvinceInfo.class);
        ProvinceInfo districInfo = gson.fromJson(distric, ProvinceInfo.class);
        ArrayList<Province> provinceList = provinceInfo.province;
        TreeMap<String, ArrayList<Map<String, String>>> cityMap = cityInfo.city;
        TreeMap<String, ArrayList<Map<String, String>>> districtMap = districInfo.district;
        options1Items = provinceList;
        ArrayList<Province> cityList_01;
        ArrayList<ArrayList<Province>> districList_01;
        ArrayList<Province> districList_02;
        ArrayList<Map<String, String>> cityList;
        ArrayList<Map<String, String>> districList;
        for (Province pro : provinceList) {
            cityList_01 = new ArrayList<Province>();
            districList_01 = new ArrayList<ArrayList<Province>>();
            cityList = cityMap.get(pro.code);
            for (Map<String, String> mapCity : cityList) {
                for (String k : mapCity.keySet()) {
                    districList_02 = new ArrayList<Province>();
                    cityList_01.add(new ProvinceInfo().new Province(k, mapCity.get(k)));
                    districList = districtMap.get(k);
                    for (Map<String, String> districMap : districList) {
                        for (String k1 : districMap.keySet()) {
                            districList_02.add(new ProvinceInfo().new Province(k1, districMap.get(k1)));
                        }
                    }
                    districList_01.add(districList_02);
                }
            }
            options3Items.add(districList_01);
            options2Items.add(cityList_01);
        }

        //三级联动效果
        optionsPickerView.setPicker(options1Items, options2Items, options3Items, true);
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
                String tx = options1Items.get(options1).name
                        + options2Items.get(options1).get(option2).name
                        + options3Items.get(options1).get(option2).get(options3).name;
                locationTv.setValue(tx);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_add1:
                optionsPickerView.show();
                break;
            case R.id.info_add2:
                break;
        }
    }

}