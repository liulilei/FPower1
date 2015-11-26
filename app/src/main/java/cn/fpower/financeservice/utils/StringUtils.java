package cn.fpower.financeservice.utils;


import android.content.Context;

public final class StringUtils {
    private static final String TAG = "StringUtils";

    /**
     * Judge if a String with a null value.
     *
     * @param src
     * @return
     */
    public static boolean isNull(String src) {
        return src == null;
    }

    /**
     * Judge if a String with a null value or with zero length.
     *
     * @param src
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || src.length() == 0;
    }

    /**
     * Judge if a String with all whitespace.
     *
     * @param src
     * @return
     */
    public static boolean isBlank(String src) {
        return src != null && "".equals(trimAll(src));
    }

    /**
     * Judge if a String equals value "null".
     *
     * @param src
     * @return
     */
    public static boolean equalsNull(String src) {
        return src != null && "null".equalsIgnoreCase(trimAll(src));
    }

    /**
     * Ignore all whitespace in a String.
     *
     * @param src
     * @return
     */
    public static String trimAll(String src) {
        if (src == null) {
            return null;
        }
        return src.replaceAll(" ", "");
    }

    /**
     * Judge if a String with a detail meaning.
     *
     * @param src
     * @return
     */
    public static boolean isMeaningful(String src) {
        return !isNull(src) && !isBlank(src) && !equalsNull(src);
    }

    /**
     * Change bytes to Hex String in lowercase.
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        return byte2Hex(bytes);
    }

    public static String byte2Hex(byte[] src) {
        if (src == null) {   //remove src.length<=0, just for new byte[0] -> ""
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * Parse a String into an int value, with default value 0 if exception(s) occur.
     *
     * @param input
     * @return
     */
    public static int toInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return 0;
    }

    /**
     * Parse a String into a long value, with default value 0L if exception(s) occur.
     *
     * @param input
     * @return
     */
    public static long toLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 如果价格 小数点后面全为0 则去掉0
     *
     * @return
     */
    public static String getPrice(double price) {
        String pricestr = price + "";
        String[] x = pricestr.split("\\.");
        if (x.length == 2) {
            if (x[1].equals("0") || x[1].equals("00")) {
                pricestr = x[0];
            }
        }
        return pricestr;
    }
}
