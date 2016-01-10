package cn.fpower.financeservice.constants;

import android.os.Environment;

public class Constants {

    public static final String IDONGRI_PATH = Environment.getExternalStorageDirectory() + "/financeservice";

    public static final String IMG_PATH = IDONGRI_PATH + "/img";

    public static final String APK_PATH = IDONGRI_PATH + "/apk/financeservice.apk";

    public static final String IMG_LOAD_CACHE_PATH = IMG_PATH + "/cache";

    public static final String ADDRESS = "address";//进入webview的地址

    public static final String MOBLEE = "mobile";

    public static final String PASSWD = "passwd";

    public static final String[] sexs = {"男", "女"};

    public static String[] isHas = {"有", "无"};

    public interface Right {
        //普通用户
        int NORMAL = 0;
        //店长
        int SHOP = 13;
        //店员
        int EMP = 14;
        //推广员
        int PROMOTER = 15;
    }

    /**
     * 0 待审核 1审核成功 -1审核失败 -2申请失败 2申请成功
     **/

    public interface ProgressStatus {

        public static final int ALL = -10;

        public static final int AUDIT = 0;

        public static final int AUDIT_SUCCESS = 1;

        public static final int AUDIT_FAIL = -1;

        public static final int APPLY_FAIL = -2;

        public static final int APPLY_SUCCESS = 2;
    }


    public final static int CODE_NAME = 1000;
    public final static int CODE_MONEY = 1001;
    public final static int CODE_MOBILE = 1002;
    public final static int CODE_ADDRDETAIL = 1003;
    public final static int CODE_QUDAO = 1004;
    public final static int CODE_USERNAME = 1005;

    //默认条数
    public static final int PAGE_SIZE = 10;
}
