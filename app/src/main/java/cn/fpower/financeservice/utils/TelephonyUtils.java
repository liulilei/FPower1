package cn.fpower.financeservice.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 电话操作相关类
 */
public class TelephonyUtils {

	/** 检测手机是否已插入SIM卡 */
	public static boolean isCheckSimCardAvailable(Context context) {
		if (null == context) {
			return false;
		}
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
	}

	/**
	 * 直接拨打电话
	 */
	public static void callPhone(Context context, String phoneNum) {
		if(isCheckSimCardAvailable(context)){
			if (phoneNum != null && phoneNum.trim().length() > 0) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				Uri uri = Uri.parse("tel:" + phoneNum);
				intent.setData(uri);
				context.startActivity(intent);
			}
		}else{
			Toast.makeText(context, "请确认手机是否已插入SIM卡", 0).show();
		}
	}
}
