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
     * @param listener  回调监听
     */
    public void check_mobile_sole(Context context, String mobile, boolean hasDialog, ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);

        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.CHECK_MOBILE_SOLE, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.CHECK_MOBILE_SOLE, params, listener);
        }
    }


    /**
     * 获取验证码
     *
     * @param context   上下文
     * @param mobile    手机号
     * @param hasDialog 是否需要dialog
     * @param listener  回调监听
     */
    public void get_code(Context context, String mobile, int type, boolean hasDialog, ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("type", type + "");
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.GET_CODE, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.GET_CODE, params, listener);
        }
    }

    /**
     * 注册帐号
     *
     * @param context   上下文
     * @param mobile    手机号
     * @param verify    验证码
     * @param passwd    密码
     * @param hasDialog 是否需要dialog
     * @param listener  回调监听
     */
    public void register(Context context, String mobile, String verify, String passwd, boolean hasDialog, ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("passwd", passwd);
        params.addBodyParameter("register_source", 2 + "");//注册来源，1ios，2安卓
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.REGISTER, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.REGISTER, params, listener);
        }
    }

    /**
     * 登录
     * @param context   上下文
     * @param mobile    手机号
     * @param passwd    密码
     * @param hasDialog 是否需要dialog
     * @param listener  回调监听
     */
    public void login(Context context, String mobile, String passwd, boolean hasDialog, ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("passwd", passwd);
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.LOGIN, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.LOGIN, params, listener);
        }
    }




}
