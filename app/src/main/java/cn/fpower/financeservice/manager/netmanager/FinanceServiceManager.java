package cn.fpower.financeservice.manager.netmanager;

import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;

import cn.fpower.financeservice.mode.LoanPara;
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
        params.addBodyParameter("code_type", type + "");
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
     *
     * @param context   上下文
     * @param mobile    手机号
     * @param passwd    密码
     * @param hasDialog 是否需要dialog
     * @param listener  回调监听
     */
    public void login(Context context, String mobile, String passwd, boolean hasDialog, Class clazz,
                      ManagerDataListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("passwd", passwd);
        if (hasDialog) {
            getDataFromNetHasDialog(context, NetApi.LOGIN, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, NetApi.LOGIN, params, clazz, listener);
        }
    }


    public void complete_user_info(Context context, String user_id, String face,
                                   String username, String birthday, String sex, String province_id,
                                   String city_id, String district_id,
                                   boolean hasDialog, Class clazz,
                                   ManagerDataListener listener) {
        params = new RequestParams();//user_id，face, username, birthday, sex, province_id, city_id, district_id
        params.addBodyParameter("user_id", user_id);
        if(!TextUtils.isEmpty(face)) {
            params.addBodyParameter("face", face);
        }
        params.addBodyParameter("username", username);
        params.addBodyParameter("birthday", birthday);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("district_id", district_id);
        if (hasDialog) {
            getDataFromNetHasDialog(context, NetApi.COMPLETE_USER_INFO, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, NetApi.COMPLETE_USER_INFO, params, clazz, listener);
        }
    }


    public void create_loan(Context context, LoanPara loanPara,
                                   boolean hasDialog, Class clazz,
                                   ManagerDataListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", loanPara.user_id+"");
        params.addBodyParameter("realname", loanPara.realname);
        params.addBodyParameter("mobile", loanPara.mobile);
        params.addBodyParameter("money", loanPara.money+"");
        params.addBodyParameter("province_id", loanPara.province_id);
        params.addBodyParameter("city_id", loanPara.city_id);
        params.addBodyParameter("district_id", loanPara.district_id);
        params.addBodyParameter("is_housing", loanPara.is_housing);
        params.addBodyParameter("is_loan", loanPara.is_loan);
        params.addBodyParameter("address", loanPara.address);
        params.addBodyParameter("source", "2");
        if(!TextUtils.isEmpty(loanPara.channel)){
            params.addBodyParameter("channel", loanPara.channel);
        }
        if (hasDialog) {
            getDataFromNetHasDialog(context, NetApi.CREATE_LOAN, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, NetApi.CREATE_LOAN, params, clazz, listener);
        }
    }

    public void loan_list(Context context, String user_id, String process, boolean hasDialog, Class clazz,
                      ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id);
        if(!TextUtils.isEmpty(process)) {
            params.addQueryStringParameter("process", process);
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.LOAN_LIST, params, clazz, listener);
        } else {
            getDataFromNetHasDialogGet(context, NetApi.LOAN_LIST, params, clazz, listener);
        }
    }
}
