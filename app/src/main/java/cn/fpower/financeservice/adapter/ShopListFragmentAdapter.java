package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.app.FSApplication;
import cn.fpower.financeservice.mode.ProvinceData;
import cn.fpower.financeservice.mode.ShopData;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;

/**
 * Created by ll on 2015/12/2.
 */
public class ShopListFragmentAdapter extends AbstractAdapter<ShopData> {

    private Intent intent;
    private ProvinceData provinceData;
    public ShopListFragmentAdapter(Context Context, List<ShopData> datas) {
        super(Context, datas);
        provinceData=FSApplication.getInstance().getProvinceData();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_success_example, null);
            holder.progressAvatar = (ImageView) convertView.findViewById(R.id.item_success_example_avatar);
            holder.progressName = (TextView) convertView.findViewById(R.id.item_success_example_name);
            holder.progressMoney = (TextView) convertView.findViewById(R.id.item_success_example_money);
            holder.progressMoney.setTextColor(mContext.getResources().getColor(R.color.black_000000));
            holder.progressCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.progressRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopData info = mList.get(position);
        holder.progressName.setText(info.username);
        holder.progressMoney.setText(provinceData.getMap().get(info.province_id)+provinceData.getMap().get(info.city_id)+provinceData.getMap().get(info.district_id));
        holder.progressCreateTime.setText(info.address);
        ImageUtils.displayImageRoundImg(R.mipmap.moren, info.imgs != null && info.imgs.size() > 0 ? NetApi.URL_HTTP +info.imgs.get(0) : "", holder.progressAvatar);
        return convertView;
    }

    class ViewHolder {
        private ImageView progressAvatar;
        private TextView progressName;
        private TextView progressMoney;
        private TextView progressCreateTime;
        private ImageView progressRightIv;
    }

    public class CallPhoneClickListener implements View.OnClickListener {

        private String phone;

        public CallPhoneClickListener(String phone) {
            this.phone = phone;
        }

        @Override
        public void onClick(View v) {
            //用intent启动拨打电话
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            mContext.startActivity(intent);
        }
    }
}
