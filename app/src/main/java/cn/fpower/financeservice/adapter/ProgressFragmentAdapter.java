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
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.TimeUtils;

/**
 * Created by ll on 2015/12/2.
 */
public class ProgressFragmentAdapter extends AbstractAdapter<DataInfo> {

    private Intent intent;

    public ProgressFragmentAdapter(Context Context, List<DataInfo> datas) {
        super(Context, datas);
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
            holder.progressCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.progressRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.progressRightIv.setImageResource(R.mipmap.phone);
        holder.progressRightIv.setOnClickListener(new CallPhoneClickListener(mList.get(position).getMobile()));
        ImageUtils.displayImageRoundImg(R.mipmap.moren, "", holder.progressAvatar);
        holder.progressName.setText(mList.get(position).getRealname());
        holder.progressMoney.setText(MappingManager.getProcess(Integer.parseInt(mList.get(position).getProcess())));

        /*if (mList.get(position).getProcess()==Constants.ProgressStatus.) {
            holder.progressMoney.setTextColor(mContext.getResources().getColor(R.color.progress_checking));
        } else if (mList.get(position).getProcess().equals(Constants.PROGRESS_CHECKED)) {
            holder.progressMoney.setTextColor(mContext.getResources().getColor(R.color.progress_checked));
        } else if (mList.get(position).getProcess().equals(Constants.PROGRESS_CHECK_OK)) {
           // holder.progressMoney.setText("￥" + mList.get(position).getMoney());
            holder.progressMoney.setTextColor(mContext.getResources().getColor(R.color.progress_check_ok));
        } else {
            holder.progressMoney.setTextColor(mContext.getResources().getColor(R.color.progress_check_ok));
        }*/
        holder.progressCreateTime.setText("申请时间:" + TimeUtils.fullTimeAndDay(mList.get(position).getAddtime()));
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
