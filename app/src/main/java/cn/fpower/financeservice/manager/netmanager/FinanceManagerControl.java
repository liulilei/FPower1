package cn.fpower.financeservice.manager.netmanager;

/**
 * Created by ll on 2015/9/15.
 * 管理所有网络请求manager的基类
 */
public class FinanceManagerControl {

    private static FinanceServiceManager financeServiceManager;

    public static FinanceServiceManager getFinanceServiceManager() {

        if (financeServiceManager == null) {
            financeServiceManager = new FinanceServiceManager();
        }

        return financeServiceManager;
    }

}
