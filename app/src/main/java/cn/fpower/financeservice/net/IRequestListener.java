package cn.fpower.financeservice.net;


public interface IRequestListener {

    /**
     * 返回数据
     */
    void onSuccess(String result);

    boolean onError(String error);
}
