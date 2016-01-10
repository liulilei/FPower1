package cn.fpower.financeservice.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import cn.fpower.financeservice.R;

public class MyAlertDialog {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private TextView txt_title;
	private TextView txt_msg;
	private Button btn_left;
	private Button btn_right;
	private ImageView img_line;
	private Display display;
	private boolean showTitle = false;
	private boolean showMsg = false;
	private boolean showLeftBtn = false;
	private boolean showRightBtn = false;

	public MyAlertDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
		initView();
	}

	void initView(){
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_alertdialog, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_title.setVisibility(View.GONE);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		txt_msg.setVisibility(View.GONE);
		btn_left = (Button) view.findViewById(R.id.btn_left);
		btn_left.setVisibility(View.GONE);
		btn_right = (Button) view.findViewById(R.id.btn_right);
		btn_right.setVisibility(View.GONE);
		img_line = (ImageView) view.findViewById(R.id.img_line);
		img_line.setVisibility(View.GONE);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.75), LayoutParams.WRAP_CONTENT));
	}


	public MyAlertDialog setTitle(String title) {
		showTitle = true;
		if ("".equals(title)) {
			txt_title.setText("标题");
		} else {
			txt_title.setText(title);
		}
		return this;
	}

	public MyAlertDialog setMsg(String msg) {
		showMsg = true;
		if ("".equals(msg)) {
			txt_msg.setText("内容");
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}

	public MyAlertDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public MyAlertDialog setRightButton(String text,
										 final OnClickListener listener) {
		showLeftBtn = true;
		if ("".equals(text)) {
			btn_right.setText("确定");
		} else {
			btn_right.setText(text);
		}
		btn_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	public MyAlertDialog setLeftButton(String text,
										 final OnClickListener listener) {
		showRightBtn = true;
		if ("".equals(text)) {
			btn_left.setText("取消");
		} else {
			btn_left.setText(text);
		}
		btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onClick(v);
				dialog.dismiss();
			}
		});
		return this;
	}

	private void setLayout() {
		if (!showTitle && !showMsg) {
			txt_title.setText("提示");
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showTitle) {
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showMsg) {
			txt_msg.setVisibility(View.VISIBLE);
		}

		if (!showLeftBtn && !showRightBtn) {
			btn_right.setText("确定");
			btn_right.setVisibility(View.VISIBLE);
			btn_right.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}

		if (showLeftBtn && showRightBtn) {
			btn_right.setVisibility(View.VISIBLE);
			btn_left.setVisibility(View.VISIBLE);
			img_line.setVisibility(View.VISIBLE);
		}
		if (showLeftBtn && !showRightBtn) {
			btn_right.setVisibility(View.VISIBLE);
		}
		if (!showLeftBtn && showRightBtn) {
			btn_left.setVisibility(View.VISIBLE);
		}
	}

	public void show() {
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		setLayout();
		dialog.show();
	}
}
