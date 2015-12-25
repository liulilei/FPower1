package cn.fpower.financeservice.constants;

import android.os.Environment;

public class Constants {

    public static final String IDONGRI_PATH = Environment.getExternalStorageDirectory() + "/financeservice";

    public static final String IMG_PATH = IDONGRI_PATH + "/img";

    public static final String IMG_LOAD_CACHE_PATH = IMG_PATH + "/cache";

    public static final String ADDRESS = "address";//进入webview的地址

    public static final String MOBLEE = "mobile";

    public static final String PASSWD = "passwd";

    public static final String[] sexs = {"男", "女"};

    public static String[] isHas = {"有", "无"};

    public interface  Right{
        //普通用户
        int NORMAL=0;
        //店长
        int SHOP=13;
        //店员
        int EMP=14;
        //推广员
        int PROMOTER=15;
    }

    public static final int PROGRESS_ALL = 0;

    public static final int PROGRESS_CHECKING = 1;

    public static final int PROGRESS_CHECKED = -1;

    public static final int PROGRESS_CHECK_OK = -2;
}
