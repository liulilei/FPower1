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
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.TimeUtils;

/**
 * Created by ll on 2015/12/2.
 */
public class AllProgressFragmentAdapter extends AbstractAdapter<DataInfo> {

    private Intent intent;

    public AllProgressFragmentAdapter(Context Context, List<DataInfo> datas) {
        super(Context, datas);
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
            convertView = View.inflate(mContext, R.layout.item_example, null);
            holder.item_qian = (ImageView) convertView.findViewById(R.id.item_qian);
            holder.progressName = (TextView) convertView.findViewById(R.id.item_success_example_name);
            holder.progressMoney = (TextView) convertView.findViewById(R.id.item_success_example_money);
            holder.progressCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.progressRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DataInfo info=mList.get(position);
        holder.item_qian.setImageResource(R.mipmap.shenhe);
        holder.progressRightIv.setImageResource(R.mipmap.phone);
        holder.progressRightIv.setOnClickListener(new CallPhoneClickListener(mList.get(position).getMobile()));
        holder.progressName.setText(info.getRealname());
        holder.progressMoney.setText(MappingManager.getProcess(Integer.parseInt(info.getProcess())));
        holder.progressCreateTime.setText("申请时间:" + TimeUtils.fullTimeAndDay(info.getAddtime()));
        return convertView;
    }

    class ViewHolder {
        private ImageView item_qian;
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
