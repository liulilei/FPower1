package cn.fpower.financeservice.view.me;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.fpower.financeservice.R;
import cn.fpower.financeservice.utils.ImageUtils;
import cn.fpower.financeservice.utils.IntentUtils;
import cn.fpower.financeservice.utils.PickPhotoUtil;
import cn.fpower.financeservice.utils.ToastUtils;
import cn.fpower.financeservice.view.BaseActivity;
import cn.fpower.financeservice.view.ImgSelActivity;

/**
 * 推广员我的业绩
 */
public class PromotionResultActivity extends BaseActivity {

    @ViewInject(R.id.title_bar_back)
    private ImageView back;

    @ViewInject(R.id.title_bar_title)
    private TextView title;

    private List<String> list = new ArrayList<String>();

    @ViewInject(R.id.noScrollgridview)
    private GridView noScrollgridview;

    private GridAdapter adapter;

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            if (list.size() == 5) {
                return 5;
            }
            return list.size() + 1;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_imageview, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position == list.size()) {
                ImageUtils.displayImageRoundImg(R.mipmap.ad1, "drawable://" + R.mipmap.icon_addpic, holder.image);
                if (position == 5) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                ImageUtils.displayImageRoundImg(R.mipmap.ad1, "file://" + list.get(position), holder.image);
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PickPhotoUtil.PICKPHOTO_LOCAL:
                if (resultCode == RESULT_OK) {
                    String picturePath = PickPhotoUtil.getInstance().getPathNameFromUri(this, data.getData());
                    list.add(picturePath);
                    adapter.notifyDataSetChanged();
                }
                break;
            case PickPhotoUtil.PICKPHOTO_TAKE:
                if (resultCode == RESULT_OK) {
                    PickPhotoUtil.galleryAddPic(this, photo);
                    list.add(photo);
                    adapter.notifyDataSetChanged();
                }
                break;
            case PickPhotoUtil.PICKPHOTO_DELETE:
                if (resultCode == RESULT_OK) {
                    int position = data.getExtras().getInt("position");
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    private String photo;

    @Override
    protected int initLayout() {
        return R.layout.activity_promotion_result;
    }

    @Override
    protected void initView() {
        back.setVisibility(View.GONE);
        title.setText("店面信息");
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()) {
                    if (mDialog == null) {
                        mDialog = createDialog("选择图片");
                    }
                    mDialog.show();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(act, ImgSelActivity.class);
                    intent.putExtra("photo", list.get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, PickPhotoUtil.PICKPHOTO_DELETE);
                }
            }
        });
    }

    private Dialog mDialog;

    protected Dialog createDialog(String title) {
        return new AlertDialog.Builder(act).setCancelable(true)
                .setTitle(title)
                .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PickPhotoUtil.getInstance().localPhoto(act);
                        } else {
                            photo = PickPhotoUtil.getInstance().takePhoto(act);
                        }
                    }
                })
                .create();
    }


    @Override
    public void onClick(View v) {

    }

}