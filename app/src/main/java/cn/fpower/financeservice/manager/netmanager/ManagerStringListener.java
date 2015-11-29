package cn.fpower.financeservice.manager.netmanager;

/**
 * Created by Administrator on 2015/10/7.
 */
public interface ManagerStringListener {
    /**
     * 返回数据
     */
    void onSuccess(String result);

    void onError(String error);
}
