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

    public enum ProgressStatus {
        ALL(-2, "全部"), AUDIT(0, "待审核"), AUDIT_SUCCESS(1, "审核成功"), AUDIT_FAIL(-1, "审核失败"), APPLY_FAIL(-2, "申请失败"), APPLY_SUCCESS(2, "申请成功");
        private int progress;
        private String text;

        //构造函数初始化
        private ProgressStatus(int progress, String text) {
            this.progress = progress;
            this.text = text;
        }

        public static ProgressStatus get(int progress){
            switch (progress) {
                case 0:
                    return ProgressStatus.AUDIT;
                case 1:
                    return ProgressStatus.AUDIT_SUCCESS;
                case -1:
                    return ProgressStatus.AUDIT_FAIL;
                case -2:
                    return ProgressStatus.APPLY_FAIL;
                case 2:
                    return ProgressStatus.APPLY_SUCCESS;
                default:
                    return ProgressStatus.ALL;
            }
        }

        public final int getProgress() {
            return progress;
        }

        public String getText() {
            return text;
        }


    }


   /* public static final int PROGRESS_ALL = 0;

    public static final int PROGRESS_CHECKING = 1;

    public static final int PROGRESS_CHECKED = -1;

    public static final int PROGRESS_CHECK_OK = -2;*/

    //默认条数
    public static final int PAGE_SIZE = 10;
}
