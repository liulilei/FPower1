package cn.fpower.financeservice.manager.netmanager;

import android.content.Context;

import com.lidroid.xutils.http.RequestParams;

import cn.fpower.financeservice.net.NetApi;

/**
 * Created by ll on 2015/11/24.
 */
public class FinanceServiceManager extends BaseManager {

    private RequestParams params;


    /**
     * 验证手机号是否注册
     *
     * @param context   上下文
     * @param mobile    手机号
     * @param hasDialog 是否需要dialog
     * @param clazz     接受类
     * @param listener  回调监听
     */
    public void check_mobile_sole(Context context, int mobile, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile + "");

        if (hasDialog) {
            getDataFromNetHasDialog(context, NetApi.CHECK_MOBILE_SOLE, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, NetApi.CHECK_MOBILE_SOLE, params, clazz, listener);
        }
    }

}
