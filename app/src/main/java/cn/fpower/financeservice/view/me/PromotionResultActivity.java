package cn.fpower.financeservice.view.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;

/**
 * 推广员我的业绩
 */
public class PromotionResultActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;




    @Override
    protected int initLayout() {
        return R.layout.activity_promotion_result;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("店面信息");

    }

    @Override
    public void onClick(View v) {

    }


}