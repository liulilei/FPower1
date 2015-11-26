package cn.fpower.financeservice.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    private static final String SP_FILE_NAME = "FinanceService";
    private static SharedPreferences sp;

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static Long getLong(Context context, String key, Long defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

    public static void putLong(Context context, String key, Long value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).commit();
    }

}
