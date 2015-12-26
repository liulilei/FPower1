package cn.fpower.financeservice.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import cn.fpower.financeservice.constants.Constants;

public class PickPhotoUtil {
	public final static String FILEDIR = Constants.IMG_PATH;

	private static PickPhotoUtil instance;

	public static PickPhotoUtil getInstance() {
		if (instance == null) {
			instance = new PickPhotoUtil();
		}
		return instance;
	}

	private void initPath() {
		File dir_camera = new File(FILEDIR);
		if (!dir_camera.exists() && !dir_camera.isDirectory()) {
			dir_camera.mkdir();
		}
	}

	/**
	 * 拍照
	 */
	public static final int PICKPHOTO_TAKE = 2001;
	/**
	 * 本地相册
	 */
	public static final int PICKPHOTO_LOCAL = 2002;

	public static final int PICKPHOTO_DELETE = 2003;

	private PickPhotoUtil() {
		initPath();
	}

	/**
	 * 是否有SD卡
	 * 
	 * @return
	 */
	private boolean isStorageState() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从相册选择照片
	 * 
	 * @param mActivity
	 */
	public void localPhoto(Activity mActivity) {
		if (isStorageState()) {
			Intent mIntent = new Intent(Intent.ACTION_PICK);
			mIntent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			mActivity.startActivityForResult(mIntent, PICKPHOTO_LOCAL);
		} else {
			Toast.makeText(mActivity, "未找到SD卡", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 拍照选取
	 * 
	 * @param mActivity
	 */
	public String takePhoto(Activity mActivity) {
		if (isStorageState()) {
			String path = System.currentTimeMillis() + ".png";
			File imgFile = new File(FILEDIR, path);
			if (null != imgFile) {
				Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
				mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
				mActivity.startActivityForResult(mIntent, PICKPHOTO_TAKE);
				return imgFile.getAbsolutePath();
			} else {
				Toast.makeText(mActivity, "创建图片对象有误", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(mActivity, "未找到SD卡", Toast.LENGTH_LONG).show();
		}
		return null;
	}

	/**
	 * 根据选择相片的uri返回对应的bitmap路径
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public String getPathNameFromUri(Context context, Uri uri) {
		String scheme = uri.getScheme();
		String pathName = "";
		if ("file".equalsIgnoreCase(scheme)) {
			pathName = uri.getPath();
		} else{
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(uri,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			pathName = cursor.getString(columnIndex);
			cursor.close();
		}
		return pathName;
	}

	/**
	 * 添加到图库
	 */
	public static void galleryAddPic(Context context, String path) {
		if (!TextUtils.isEmpty(path)) {
			Intent mediaScanIntent = new Intent(
					Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			File f = new File(path);
			Uri contentUri = Uri.fromFile(f);
			mediaScanIntent.setData(contentUri);
			context.sendBroadcast(mediaScanIntent);
		}
	}
}
