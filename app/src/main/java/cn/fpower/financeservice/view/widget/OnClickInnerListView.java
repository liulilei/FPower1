package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author liulilei
 * 不能点击的全部展开的listview
 */
public class OnClickInnerListView extends InnerListView {

	public OnClickInnerListView(Context context, AttributeSet attrs,
								int defStyle) {
		super(context, attrs, defStyle);
	}

	public OnClickInnerListView(Context context) {
		super(context);
	}

	public OnClickInnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
