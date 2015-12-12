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
import cn.fpower.financeservice.mode.LoanInfo;
import cn.fpower.financeservice.utils.ImageUtils;

/**
 * Created by ll on 2015/12/2.
 */
public class AllProgressFragmentAdapter extends AbstractAdapter<LoanInfo> {

    private Intent intent;

    public AllProgressFragmentAdapter(Context Context, List<LoanInfo> datas) {
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
        LoanInfo info=mList.get(0);
        holder.progressName.setText(info.getData().getRealname());
        holder.progressMoney.setText(MappingManager.getProcess(info.getData().getProcess()));
        holder.progressCreateTime.setText("申请时间:"+info.getData().getAddtime());
        // ImageUtils.displayImageRoundImg(R.mipmap.ad1, "http://stimgcn1.s-msn.com/msnportal/fashion/2012/12/24/51478f07-382d-4da9-99b5-815dd2848aa8.jpg", holder.progressAvatar);
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
