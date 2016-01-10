package cn.fpower.financeservice.manager.netmanager;

import java.io.File;

/**
 * Created by ll on 2015/9/15.
 */
public interface DownListener {
    /**
     * 返回数据
     */
    void onSuccess(File result);

    void onLoading(long total, long current,int process);

    void onError(String error);
}
