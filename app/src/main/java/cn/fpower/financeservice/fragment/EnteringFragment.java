package cn.fpower.financeservice.fragment;

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

/**
 * Created by ll on 2015/11/26.
 */
public class EnteringFragment extends BaseFragment {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;
    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_entering, null);
        return rootView;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText(getString(R.string.entering));
    }
}
