package cn.fpower.financeservice.view.me;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.view.BaseActivity;

/**
 *  我的店铺
 */
public class MeStoreActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.title_bar_title)
    private ImageView img_my_head;

    @Override
    protected int initLayout() {
        return R.layout.activity_info_list;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("店铺名称");
    }
}
