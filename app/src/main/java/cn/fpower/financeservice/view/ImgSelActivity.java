package cn.fpower.financeservice.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.KeyBoardUtils;
import cn.fpower.financeservice.view.widget.ClearEditText;

public class ImgSelActivity extends BaseActivity implements OnClickListener {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    @ViewInject(R.id.title_bar_save)
    private TextView save;

    @ViewInject(R.id.iv_selimg)
    private ImageView iv_selimg;

    int position;

    @Override
    protected int initLayout() {
        return R.layout.activity_gallery_select;
    }

    @Override
    protected void initView() {
        back.setOnClickListener(this);
        title.setText("查看照片");
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String photo = b.getString("photo");
            position = b.getInt("position");
            ImageUtils.displayImageRoundImg(R.mipmap.ad1, "file://" + photo, iv_selimg);
        }
        save.setText("删除");
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back:
                this.setResult(RESULT_CANCELED);
                this.finish();
                break;
            case R.id.title_bar_save:
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("position", position);
                //设置返回数据
                this.setResult(RESULT_OK, intent);
                //关闭Activity
                this.finish();
                break;
        }
    }

}
