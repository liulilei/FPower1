package cn.fpower.financeservice.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
}
