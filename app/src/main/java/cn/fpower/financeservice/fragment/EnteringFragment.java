package cn.fpower.financeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.view.InfoInputActivity;
import cn.fpower.financeservice.view.widget.EnteringSettingView;

/**
 * Created by ll on 2015/11/26.
 */
public class EnteringFragment extends BaseFragment  implements View.OnClickListener{

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;
    @ViewInject(R.id.info_name)
    private EnteringSettingView info_name;
    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_entering, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.entering));
        info_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_name:
                Intent intent = new Intent();
                intent.putExtra("title",info_name.getTitle());
                intent.setClass(getContext(), InfoInputActivity.class);
                startActivity(intent);
                break;
        }
    }
}
