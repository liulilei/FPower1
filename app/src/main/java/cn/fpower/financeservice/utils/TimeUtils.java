package cn.fpower.financeservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ll on 2015/12/12.
 */
public class TimeUtils {

    public static String fullTimeAndDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return format.format(calendar.getTime());
    }

}
