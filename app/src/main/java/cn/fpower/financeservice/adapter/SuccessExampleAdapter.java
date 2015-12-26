package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.TimeUtils;

/**
 * Created by ll on 2015/11/30.
 */
public class SuccessExampleAdapter extends AbstractAdapter<DataInfo> {

    public SuccessExampleAdapter(Context Context, List<DataInfo> datas) {
        super(Context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_success_example, null);
            holder.exampleAvatar = (ImageView) convertView.findViewById(R.id.item_success_example_avatar);
            holder.exampleName = (TextView) convertView.findViewById(R.id.item_success_example_name);
            holder.exampleMoney = (TextView) convertView.findViewById(R.id.item_success_example_money);
            holder.exampleCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.exampleRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.displayImageRoundImg(R.mipmap.moren, "http://stimgcn1.s-msn.com/msnportal/fashion/2012/12/24/51478f07-382d-4da9-99b5-815dd2848aa8.jpg", holder.exampleAvatar);
        holder.exampleName.setText(mList.get(position).getRealname());
        holder.exampleMoney.setText("￥" + mList.get(position).getMoney());
        holder.exampleCreateTime.setText("申请时间:" + TimeUtils.fullTimeAndDay(mList.get(position).getApply_success_time()));
        holder.exampleRightIv.setImageResource(R.mipmap.fanhui);
        return convertView;
    }

    class ViewHolder {
        private ImageView exampleAvatar;
        private TextView exampleName;
        private TextView exampleMoney;
        private TextView exampleCreateTime;
        private ImageView exampleRightIv;
    }
}
