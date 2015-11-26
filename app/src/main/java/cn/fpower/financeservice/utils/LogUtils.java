package cn.fpower.financeservice.utils;

import android.util.Log;

public class LogUtils {


    private static boolean sDebug = false;

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static void d(String text) {
        if (!sDebug) {
            return;
        }
        d("temp", text);
    }

    public static void d(String tag, String text) {
        if (!sDebug) {
            return;
        }
        if (tag == null || text == null) {
            e(tag, text);
        }
        Log.println(Log.DEBUG, tag, text);
    }

    public static void e(String tag, String text) {
        if (!sDebug) {
            return;
        }
        if (tag == null) {
            tag = "@null";
        }
        if (text == null) {
            text = "@null";
        }
        Log.println(Log.ERROR, tag, text);
    }

    public static void url(String text) {
        if (sDebug) {
            d("url", text);
        }
    }

    public static void val(String text) {
        if (sDebug) {
            d("value", text);
        }
    }
}
