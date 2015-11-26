package cn.fpower.financeservice.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ll on 2015/11/26.
 */
public class EnteringFragment extends BaseFragment {
    @Override
    protected ViewGroup onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        LinearLayout ll = new LinearLayout(getActivity());
        TextView tv = new TextView(getActivity());
        tv.setText("我是录入");
        tv.setTextSize(50);
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);
        return ll;
    }
}
