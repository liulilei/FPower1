package cn.fpower.financeservice.manager.netmanager;

import android.content.Context;
import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;

import cn.fpower.financeservice.constants.Constants;
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
     * 18310705012 123456 推广员
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

    /**
     * 完善信息
     *
     * @param context
     * @param user_id
     * @param face
     * @param username
     * @param birthday
     * @param sex
     * @param province_id
     * @param city_id
     * @param district_id
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void complete_user_info(Context context, int code, String user_id, String face,
                                   String username, String birthday, String sex, String province_id,
                                   String city_id, String district_id,
                                   boolean hasDialog, Class clazz,
                                   ManagerDataListener listener) {
        params = new RequestParams();//user_id，face, username, birthday, sex, province_id, city_id, district_id
        params.addBodyParameter("user_id", user_id);
        if (face != null) {
            params.addBodyParameter("face", face);
        }
        params.addBodyParameter("username", username);
        params.addBodyParameter("birthday", birthday);
        params.addBodyParameter("sex", sex);
        params.addBodyParameter("province_id", province_id);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("district_id", district_id);
        String url;
        if (code == 200) {//修改信息
            url = NetApi.USER_INFO_EDIT;
        } else {
            url = NetApi.COMPLETE_USER_INFO;//修改信息
        }
        if (hasDialog) {
            getDataFromNetHasDialog(context, url, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, url, params, clazz, listener);
        }
    }

    public void create_loan(Context context, LoanPara loanPara,
                            boolean hasDialog,
                            ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", loanPara.user_id + "");
        params.addBodyParameter("realname", loanPara.realname);
        params.addBodyParameter("mobile", loanPara.mobile);
        params.addBodyParameter("money", loanPara.money + "");
        params.addBodyParameter("province_id", loanPara.province_id);
        params.addBodyParameter("city_id", loanPara.city_id);
        params.addBodyParameter("district_id", loanPara.district_id);
        params.addBodyParameter("is_housing", loanPara.is_housing);
        params.addBodyParameter("is_loan", loanPara.is_loan);
        params.addBodyParameter("address", loanPara.address);
        params.addBodyParameter("source", "2");
        if (!TextUtils.isEmpty(loanPara.channel)) {
            params.addBodyParameter("channel", loanPara.channel);
        }
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.CREATE_LOAN, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.CREATE_LOAN, params, listener);
        }
    }

    /**
     * 15、贷款列表（进度） 我的审核
     *
     * @param context
     * @param user_id
     * @param process
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void loan_list(Context context, String user_id, int process, int now_page, boolean hasDialog, Class clazz,
                          ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id);
        if (process != Constants.ProgressStatus.ALL) {
            params.addQueryStringParameter("process", process + "");
        }
        if (now_page >= 1) {
            params.addQueryStringParameter("now_page", now_page + "");//当前所在页
            params.addQueryStringParameter("page_size", Constants.PAGE_SIZE + "");//默认10条
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.LOAN_LIST, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.LOAN_LIST, params, clazz, listener);
        }
    }

    public void home(Context context, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.HOME, null, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.HOME, null, clazz, listener);
        }
    }

    /**
     * 案例列表
     *
     * @param context
     * @param user_id   如果不传入就是所有案例，传入则为当前用户的案例
     * @param now_page
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void case_list(Context context, int user_id,
                          int now_page, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        if (user_id > 0) {
            params.addQueryStringParameter("user_id", user_id + "");
        }
        if (now_page >= 1) {
            params.addQueryStringParameter("now_page", now_page + "");//当前所在页
            params.addQueryStringParameter("page_size", Constants.PAGE_SIZE + "");//默认10条
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.CASE_LIST, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.CASE_LIST, params, clazz, listener);
        }
    }

    /**
     * 8、录入员工
     *
     * @param context
     * @param loanPara
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void create_employee(Context context, LoanPara loanPara,
                                boolean hasDialog, Class clazz,
                                ManagerDataListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", loanPara.user_id + "");
        params.addBodyParameter("mobile", loanPara.mobile);
        params.addBodyParameter("username", loanPara.username);
       /* params.addBodyParameter("province_id", loanPara.province_id);
        params.addBodyParameter("city_id", loanPara.city_id);
        params.addBodyParameter("district_id", loanPara.district_id);
        params.addBodyParameter("address", loanPara.address);*/
        params.addBodyParameter("source", "2");
        if (hasDialog) {
            getDataFromNetHasDialog(context, NetApi.CREATE_EMPLOYEE, params, clazz, listener);
        } else {
            getDataFromNetNoDialog(context, NetApi.CREATE_EMPLOYEE, params, clazz, listener);
        }
    }

    /**
     * 员工列表
     *
     * @param context
     * @param user_id
     * @param now_page
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void employee_list(Context context, int user_id,
                              int now_page, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id + "");
        if (now_page >= 1) {
            params.addQueryStringParameter("now_page", now_page + "");//当前所在页
            params.addQueryStringParameter("page_size", Constants.PAGE_SIZE + "");//默认10条
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.EMPLOYEE_LIST, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.EMPLOYEE_LIST, params, clazz, listener);
        }
    }

    /**
     * 12、店铺列表
     *
     * @param context
     * @param user_id
     * @param now_page
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void shop_list(Context context, int user_id,
                          int now_page, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id + "");
        if (now_page >= 1) {
            params.addQueryStringParameter("now_page", now_page + "");//当前所在页
            params.addQueryStringParameter("page_size", Constants.PAGE_SIZE + "");//默认10条
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.SHOP_LIST, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.SHOP_LIST, params, clazz, listener);
        }
    }

    /***
     * 店铺详情
     *
     * @param context
     * @param shop_id
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void shop_detail(Context context, int shop_id, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("shop_id", shop_id + "");
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.SHOP_DETAIL, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.SHOP_DETAIL, params, clazz, listener);
        }
    }


    /**
     * 删除员工
     *
     * @param context
     * @param user_id
     * @param employee_id
     * @param hasDialog
     * @param listener
     */
    public void delete_employee(Context context, int user_id, int employee_id,
                                boolean hasDialog, ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("employee_id", employee_id + "");
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.DELETE_EMPLOYEE, params, listener);
        } else {
            getJsonStringFromNetNoDialog(context, NetApi.DELETE_EMPLOYEE, params, listener);
        }
    }

    /**
     * 11、录入店铺 图片（多张用#####分隔）
     *
     * @param context
     * @param loanPara
     * @param hasDialog
     * @param listener
     */
    //TODO 图片Base64
    public void create_shop(Context context, LoanPara loanPara, String imgs,
                            boolean hasDialog,
                            ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", loanPara.user_id + "");
        params.addBodyParameter("mobile", loanPara.mobile);
        params.addBodyParameter("username", loanPara.username);
        params.addBodyParameter("name", loanPara.name);
        if (!TextUtils.isEmpty(imgs)) {
            params.addBodyParameter("imgs", imgs);
        }
        params.addBodyParameter("province_id", loanPara.province_id);
        params.addBodyParameter("city_id", loanPara.city_id);
        params.addBodyParameter("district_id", loanPara.district_id);
        params.addBodyParameter("address", loanPara.address);
        params.addBodyParameter("latitude", loanPara.latitude);
        params.addBodyParameter("longitude", loanPara.longitude);
        params.addBodyParameter("source", "2");
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.CREATE_SHOP, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.CREATE_SHOP, params, listener);
        }
    }


    public void achievement_amount(Context context, int user_id,
                                   String start, String end, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id + "");
        if (!TextUtils.isEmpty(start)) {
            params.addQueryStringParameter("start", start);
        }
        if (!TextUtils.isEmpty(end)) {
            params.addQueryStringParameter("end", end);
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.ACHIEVEMENT_AMOUNT, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.ACHIEVEMENT_AMOUNT, params, clazz, listener);
        }
    }

    /*public void my_achievement(Context context, int user_id,
                               int achievement_size, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id + "");
        if (achievement_size >= 1) {
            params.addQueryStringParameter("achievement_size", achievement_size + "");
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.MY_ACHIEVEMENT, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.MY_ACHIEVEMENT, params, clazz, listener);
        }
    }*/

    public void achievement_list(Context context, int user_id, int now_page, long start, long end,
                                 boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("user_id", user_id + "");
        if (start > 0) {
            params.addQueryStringParameter("start", start + "");
        }
        if (end > 0) {
            params.addQueryStringParameter("end", end + "");
        }
        if (now_page >= 1) {
            params.addQueryStringParameter("now_page", now_page + "");//当前所在页
            params.addQueryStringParameter("page_size", Constants.PAGE_SIZE + "");//默认10条
        }
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.ACHIEVEMENT_LIST, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.ACHIEVEMENT_LIST, params, clazz, listener);
        }
    }

    /**
     * 16、贷款详情
     *
     * @param context
     * @param loan_id
     * @param hasDialog
     * @param clazz
     * @param listener
     */
    public void loan_detail(Context context, int loan_id, boolean hasDialog, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("loan_id", loan_id + "");
        if (hasDialog) {
            getDataFromNetHasDialogGet(context, NetApi.LOAN_DETAIL, params, clazz, listener);
        } else {
            getDataFromNetNoDialogGet(context, NetApi.LOAN_DETAIL, params, clazz, listener);
        }
    }

    /**
     * 忘记密码
     *
     * @param context
     * @param mobile
     * @param passwd
     * @param verify
     * @param hasDialog
     * @param listener
     */
    public void forget_password(Context context, String mobile, String verify, String passwd,
                                boolean hasDialog,
                                ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("verify", verify);
        params.addBodyParameter("passwd", passwd);
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.FORGET_PASSWORD, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.FORGET_PASSWORD, params, listener);
        }
    }

    /**
     * 修改密码
     *
     * @param context
     * @param user_id
     * @param passwd    新密码
     * @param passwd_re 旧密码
     * @param hasDialog
     * @param listener
     */
    public void user_info_edit_passwd(Context context, String user_id, String passwd, String passwd_re,
                                      boolean hasDialog,
                                      ManagerStringListener listener) {
        params = new RequestParams();
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("passwd", passwd_re);
        params.addBodyParameter("passwd_re", passwd);
        if (hasDialog) {
            getJsonStringFromNetHasDialog(context, NetApi.USER_INFO_EDIT_PASSWD, params, listener);
        } else {
            getJsonStringFromNetHasDialog(context, NetApi.USER_INFO_EDIT_PASSWD, params, listener);
        }
    }

    /**检查版本**/
    public void version(Context context, Class clazz, ManagerDataListener listener) {
        params = new RequestParams();
        params.addQueryStringParameter("pt", "android");
        getDataFromNetNoDialogGet(context, NetApi.VERSION, params, clazz, listener);
    }

    /**下载**/
    public void update(Context context,String netApi, String tag, DownListener listener) {
        downloadFile(context, netApi, tag, listener);
    }
}
