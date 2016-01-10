package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.constants.Constants;
import cn.fpower.financeservice.manager.MappingManager;
import cn.fpower.financeservice.mode.DataInfo;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * 我的审核全部进度
 */
public class AllProgressFragmentAdapter extends AbstractAdapter<DataInfo> {

    private MyAlertDialog dialog;
    Resources resources;
    private Map<Integer, Integer> colors=new HashMap<Integer, Integer>();

    public AllProgressFragmentAdapter(Context context, List<DataInfo> datas) {
        super(context, datas);
        dialog = new MyAlertDialog(context);
        resources = mContext.getResources();
        colors.put(Constants.ProgressStatus.AUDIT,R.color.progress_audit);
        colors.put(Constants.ProgressStatus.AUDIT_FAIL,R.color.progress_audit_fail);
        colors.put(Constants.ProgressStatus.AUDIT_SUCCESS,R.color.progress_audit_success);
        colors.put(Constants.ProgressStatus.APPLY_FAIL, R.color.progress_apply_fail);
        colors.put(Constants.ProgressStatus.APPLY_SUCCESS,R.color.progress_apply_success);
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
        holder.progressRightIv.setOnClickListener(new CallPhoneClickListener(info.getMobile(), dialog, mContext));
        holder.progressName.setText(info.getRealname());
        holder.progressMoney.setText(MappingManager.getProcess(info.getProcess()));
        holder.progressMoney.setTextColor(resources.getColor(colors.get(info.getProcess())));
        holder.progressCreateTime.setText(TimeUtils.fullTimeAndDay(info.getAddtime()));
        return convertView;
    }

    class ViewHolder {
        private ImageView item_qian;
        private TextView progressName;
        private TextView progressMoney;
        private TextView progressCreateTime;
        private ImageView progressRightIv;
    }
}
