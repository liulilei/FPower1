package cn.fpower.financeservice.constants;

import android.os.Environment;

public class Constants {

    public static final String IDONGRI_PATH = Environment.getExternalStorageDirectory() + "/financeservice";

    public static final String IMG_PATH = IDONGRI_PATH + "/img";

    public static final String IMG_LOAD_CACHE_PATH = IMG_PATH + "/cache";

    public static final String ADDRESS = "address";//进入webview的地址

    public static final String MOBLEE = "mobile";

    public static final String PASSWD = "passwd";
}
