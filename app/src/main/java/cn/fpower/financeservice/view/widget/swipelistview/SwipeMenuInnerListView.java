package cn.fpower.financeservice.view.widget.swipelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * 
 * @author baoyz
 * @date 2014-8-18
 * 
 */
public class SwipeMenuInnerListView extends SwipeMenuListView {

	public SwipeMenuInnerListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public SwipeMenuInnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SwipeMenuInnerListView(Context context) {
		super(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 设置滚动条不滚动
		 */
		int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				View.MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
