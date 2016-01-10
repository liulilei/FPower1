package cn.fpower.financeservice.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

/**
 * Created by ll on 2015/12/3.
 */
public class IntentUtils {

    public static void startActivity(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }


    public static void startActivity(Context context, Class clazz,Bundle options) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        intent.putExtras(options);
        context.startActivity(intent);
    }

    /**
     * 安装apk应用
     */
    public static void installAPK(Context context, File apkFile) {
        if (apkFile.isFile()) {
            String fileName = apkFile.getName();
            String postfix = fileName.substring(fileName.length() - 4, fileName.length());
            if (postfix.toLowerCase().equals(".apk")) {
                String cmd = "chmod 755 " + apkFile.getAbsolutePath();
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (Exception e) {
                    ToastUtils.show(context,e.getMessage());
                }
                Uri uri = Uri.fromFile(apkFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            }
        } else if (apkFile.isDirectory()) {
            File[] files = apkFile.listFiles();
            int fileCount = files.length;
            for (int i = 0; i < fileCount; i++) {
                installAPK(context, files[i]);
            }
        }
    }
}
