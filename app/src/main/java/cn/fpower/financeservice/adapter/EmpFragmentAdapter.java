package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.mode.EmployeeList;
import cn.fpower.financeservice.net.NetApi;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.StringUtils;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * Created by ll on 2015/12/2.
 */
public class EmpFragmentAdapter extends AbstractAdapter<EmployeeList.EmpInfo> {

    private Intent intent;
    private MyAlertDialog dialog;
    public EmpFragmentAdapter(Context context, List<EmployeeList.EmpInfo> datas) {
        super(context, datas);
        dialog = new MyAlertDialog(context);
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
        EmployeeList.EmpInfo info = mList.get(position);
        holder.progressName.setText(info.getUsername());
        holder.progressMoney.setVisibility(View.INVISIBLE);
        String content="他的业绩:￥" + info.getMonth_amount();
        holder.progressCreateTime.setText(StringUtils.getHighLightText(content, 0xFFF84431, 5, content.length()));
        ImageUtils.displayImageRoundImg(R.mipmap.moren, TextUtils.isEmpty(info.getFace()) ? "" : NetApi.URL_HTTP + info.getFace(), holder.progressAvatar);
        holder.progressRightIv.setImageResource(R.mipmap.phone);
        holder.progressRightIv.setOnClickListener(new cn.fpower.financeservice.adapter.CallPhoneClickListener(info.getMobile(), dialog, mContext));
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
