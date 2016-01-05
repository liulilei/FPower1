package cn.fpower.financeservice.view.me;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.manager.netmanager.FinanceManagerControl;
import cn.fpower.financeservice.manager.netmanager.ManagerDataListener;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.ShopData;
import cn.fpower.financeservice.mode.ShopDetail;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * 店面详情
 */
public class ShopDetailActivity extends BaseActivity {

    @ViewInject(R.id.des)
    private TextView des;

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

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

    private GridAdapter adapter;

    @ViewInject(R.id.submit)
    private Button submit;

    private List<String> list = new ArrayList<String>();

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {

            return list.size();
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
            ImageUtils.displayImageRoundImg(R.mipmap.moren, NetApi.URL_HTTP + list.get(position), holder.image);
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_promotion_result;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("店面详情");
        des.setText("门店照片,第一张为门店照片");
        submit.setVisibility(View.GONE);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        noScrollgridview.setAdapter(adapter);
    }
    private ProvinceData provinceData;

    private int shop_id;
    @Override
    protected void initData() {
        shop_id = getIntent().getIntExtra("shop_id", -1);
        if (shop_id > -1) {
            provinceData = FSApplication.getInstance().getProvinceData();
            FinanceManagerControl.getFinanceServiceManager().shop_detail(act, shop_id, true, ShopDetail.class, new ManagerDataListener() {

                @Override
                public void onSuccess(Object data) {
                    ShopData info= ((ShopDetail) data).getData().getShop_info();
                    list=info.imgs;
                    if(list!=null&&list.size()>0){
                        noScrollgridview.setAdapter(adapter);
                    }
                    info_name.setValue(info.name);
                    info_username.setValue(info.username);
                    info_mobile.setValue(info.mobile);
                    String addr= provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getProvince_id() + "")+
                            provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getCity_id() + "")+
                            provinceData.getMap().get(FSApplication.getInstance().getUserInfo().getData().getDistrict_id() + "");
                    String key=FSApplication.getInstance().getUserInfo().getData().getProvince_id()+","
                            +FSApplication.getInstance().getUserInfo().getData().getCity_id()+","
                            +FSApplication.getInstance().getUserInfo().getData().getDistrict_id();
                    info_addr.setValue(key,addr);
                    info_longitude.setValue(info.longitude);
                    info_latitude.setValue(info.latitude);
                    info_addrdetail.setValue(info.address);
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.finish();
                break;
        }
    }
}
