package cn.fpower.financeservice.net;


import java.io.File;

public interface IDownListener {

    /**
     * 返回数据
     */
    void onSuccess(File result);

    void onLoading(long total, long current,int process);

    boolean onError(String error);
}
