package cn.fpower.financeservice.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 * 
 * @author 黄俊峰
 * @phone 13516599661
 * @email 1018527259@qq.com
 * @time 2015-02-02 17:30:00
 * @version 2.0
 */
public class KeyBoardUtils {
	/**
	 * 打开软键盘（2.0后不用再调用方法关掉，自动会关的）
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext) {
		if(null==mContext||null==mEditText)
		return;	
		mEditText.setFocusable(true);
		mEditText.setFocusableInTouchMode(true);
		mEditText.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) mEditText
				.getContext().getSystemService(mContext.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(mEditText, 0);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext) {
		if(null==mContext||null==mEditText)
		return;	
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
}
