package cn.fpower.financeservice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.mode.AchievementData;
import cn.fpower.financeservice.utils.TimeUtils;
import cn.fpower.financeservice.view.widget.MyAlertDialog;

/**
 * Created by ll on 2015/12/2.
 */
public class AchievementFragmentAdapter extends AbstractAdapter<AchievementData> {

    private MyAlertDialog dialog;

    public AchievementFragmentAdapter(Context context, List<AchievementData> datas) {
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
            convertView = View.inflate(mContext, R.layout.item_example, null);
            holder.progressName = (TextView) convertView.findViewById(R.id.item_success_example_name);
            holder.progressMoney = (TextView) convertView.findViewById(R.id.item_success_example_money);
            holder.progressCreateTime = (TextView) convertView.findViewById(R.id.item_success_example_create_time);
            holder.progressRightIv = (ImageView) convertView.findViewById(R.id.item_success_example_right_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AchievementData info = mList.get(position);
        holder.progressName.setText(info.realname);
        holder.progressMoney.setText(info.money);
        holder.progressCreateTime.setText(TimeUtils.fullTimeAndDay(info.addtime));
        return convertView;
    }

    class ViewHolder {
        private TextView progressName;
        private TextView progressMoney;
        private TextView progressCreateTime;
        private ImageView progressRightIv;
    }
}
