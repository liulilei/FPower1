package cn.fpower.financeservice.manager.netmanager;

/**
 * Created by ll on 2015/9/15.
 */
public interface ManagerDataListener {
    /**
     * 返回数据
     */
    void onSuccess(Object data);

    void onError(String error);
}
