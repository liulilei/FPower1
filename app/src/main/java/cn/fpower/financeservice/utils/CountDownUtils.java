package cn.fpower.financeservice.utils;

import android.os.Handler;
import android.os.Message;

/**
 * 倒计时
 * @version 1.0
 */
public abstract class CountDownUtils {

	private static final int MSG = 1;
	/** 总的时间120000,120秒 **/
	private long mSetTotalTime;
	/** 减少的值1000,1秒 **/
	private long mSetDownValue;
	private long mStartTime;

	public CountDownUtils(long totalTime, long downValue) {
		this.mSetTotalTime = totalTime;
		this.mSetDownValue = downValue;
	}

	/**
	 * 启动
	 */
	public final synchronized CountDownUtils start() {
		if (mSetTotalTime <= 0) {
			onFinish();
			return this;
		}
		cancel();
		onStart();
		mStartTime = mSetTotalTime;
		mHandler.sendMessage(mHandler.obtainMessage(MSG));
		return this;

	};
	
	/**
	 * 倒计时开始
	 */
	public void onStart(){
		
	}

	/**
	 * 变化的值
	 * 
	 * @param remain
	 *            剩余的时间
	 */
	public abstract void onTick(long remain);

	/**
	 * 取消倒计时
	 */
	public void cancel() {
		mHandler.removeMessages(MSG);
	}

	/**
	 * 倒计时结束
	 */
	public abstract void onFinish();

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			synchronized (CountDownUtils.this) {
				if (mStartTime <= 0) {
					cancel();
					onFinish();// 完成
				} else {
					onTick(mStartTime);// 将值回调，供用户使用
					// 减少操作
					mStartTime = mStartTime - mSetDownValue;
					sendMessageDelayed(obtainMessage(MSG), mSetDownValue);
				}
			}
		};
	};
}