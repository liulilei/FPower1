package cn.fpower.financeservice.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 */
public class TimeUtils {

    private static SimpleDateFormat YYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat YYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd");

    public static String fullTimeAndDay(long time) {
		if(time<=0){
			return "";
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time*1000);
        return YYMMDDHHMMSS.format(calendar.getTime());
    }

    /**获取当前年**/
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**获取当前月**/
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**DATA转String**/
    public static String getTime(Date date) {
        return YYMMDD.format(date);
    }

    /**获取当前月第一天**/
    public static String getMonthStart() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return YYMMDD.format(c.getTime());
    }

    /**获取当前月最后一天**/
    public static String getMonthEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return YYMMDD.format(c.getTime());
    }

    /**获取指定年月第一天**/
    public static String getMouthStart(int year,int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, 1);//设置为1号,当前日期既为本月第一天
        return YYMMDD.format(c.getTime());
    }

    /**获取指定年月最后一天**/
    public static String getMouthEnd(int year,int month) {
        Calendar c = Calendar.getInstance();
        //设置年份
        c.set(Calendar.YEAR,year);
        //设置月份
        c.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        return YYMMDD.format(c.getTime());
    }
}
