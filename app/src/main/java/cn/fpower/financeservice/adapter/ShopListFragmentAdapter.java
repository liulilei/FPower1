package cn.fpower.financeservice.adapter;

import android.content.Context;
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
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * Created by ll on 2015/12/2.
 */
public class ShopListFragmentAdapter extends AbstractAdapter<ShopData> {
    private MyAlertDialog dialog;
    private ProvinceData provinceData;
    public ShopListFragmentAdapter(Context context, List<ShopData> datas) {
        super(context, datas);
        dialog = new MyAlertDialog(context);
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
            convertView = View.inflate(mContext, R.layout.item_shop, null);
            holder.progressAvatar = (ImageView) convertView.findViewById(R.id.item_success_example_avatar);
            holder.progressName = (TextView) convertView.findViewById(R.id.item_success_example_name);
            holder.progressMoney = (TextView) convertView.findViewById(R.id.item_success_example_money);
            holder.progressCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.progressRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ShopData info = mList.get(position);
        holder.progressName.setText(info.username);
        holder.progressMoney.setText(info.name);
        holder.progressCreateTime.setText(provinceData.getProvinceAddress(info.province_id,info.city_id,info.district_id)+" "+info.address);
        holder.progressRightIv.setImageResource(R.mipmap.phone);
        holder.progressRightIv.setOnClickListener(new CallPhoneClickListener(info.mobile, dialog, mContext));
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
}
