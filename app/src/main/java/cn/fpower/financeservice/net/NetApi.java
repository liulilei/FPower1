package cn.fpower.financeservice.net;

public class NetApi {

    public static final String URL = "http://a.gzweimiao.com";//测试地址

//    public static final String URL = "http://api.gzweimiao.com";//正式地址

    public static final String CHECK_MOBILE_SOLE = URL + "/check_mobile_sole";//验证手机号是否注册

    public static final String GET_CODE = URL + "/get_code";//获取验证码

    public static final String REGISTER = URL + "/register";//注册

    public static final String LOGIN = URL + "/login";//登陆

    public static final String COMPLETE_USER_INFO = URL + "/complete_user_info";// 完善用户信息

}
