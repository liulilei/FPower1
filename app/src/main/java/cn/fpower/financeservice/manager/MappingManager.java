package cn.fpower.financeservice.manager;

/**
 * Created by Administrator on 2015/12/12.
 */
public class MappingManager {

    // 0待审核 1审核成功 -1审核失败 -2申请失败 2申请成功
    public static String getProcess(int process) {
        switch (process) {
            case 0:
                return "待审核";
            case 1:
                return "审核成功";
            case -1:
                return "审核失败";
            case -2:
                return "申请失败";
            case 2:
                return "申请成功";
            default:
                return "全部";
        }
    }

    public  static String getSex(int sex) {
        switch (sex) {
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "";
        }
    }

}
