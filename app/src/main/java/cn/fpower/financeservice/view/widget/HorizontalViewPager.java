package cn.fpower.financeservice.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalViewPager extends ViewPager {
	
	private float downX;
    private float downY;

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public HorizontalViewPager(Context context) {
		super(context);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            downX = ev.getX();
            downY = ev.getY();
            getParent().requestDisallowInterceptTouchEvent(true);
            break;

        case MotionEvent.ACTION_MOVE:
            if (Math.abs(ev.getX() - downX) > Math.abs(ev.getY() - downY)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
            break;
        case MotionEvent.ACTION_UP:
        default:
            getParent().requestDisallowInterceptTouchEvent(false);
            break;
        }
		return super.dispatchTouchEvent(ev);
	}

	
}
