package cn.fpower.financeservice.net;

public class NetApi {

    public static final String URL_HTTP = "http://";

    public static final String URL = "http://a.gzweimiao.com";//测试地址

//    public static final String URL = "http://api.gzweimiao.com";//正式地址

    public static final String CHECK_MOBILE_SOLE = URL + "/check_mobile_sole";//1、验证手机号是否注册
    public static final String REGISTER = URL + "/register";//2、注册帐号
    public static final String GET_CODE = URL + "/get_code";//3、获取验证码
    public static final String LOGIN = URL + "/login";//4、会员登录
    public static final String COMPLETE_USER_INFO = URL + "/complete_user_info"; //5、完善信息
    public static final String HOME = URL + "/home";//6、首页
    public static final String CASE_LIST = URL + "/case_list";//7、案例列表
    public static final String CREATE_EMPLOYEE = URL + "/create_employee";//8、录入员工
    public static final String EMPLOYEE_LIST = URL + "/employee_list";//9、员工列表
    public static final String DELETE_EMPLOYEE = URL + "/delete_employee";//10、删除员工
    public static final String CREATE_SHOP = URL + "/create_shop";//11、录入店铺
    public static final String SHOP_LIST = URL + "/shop_list";//12、店铺列表
    public static final String SHOP_DETAIL = URL + "/shop_detail";//13、店铺详情
    public static final String CREATE_LOAN = URL + "/create_loan"; //14、录入贷款
    public static final String LOAN_LIST = URL + "/loan_list"; //15、贷款列表（进度）
    public static final String LOAN_DETAIL = URL + "/loan_detail";//16、贷款详情
    public static final String MY_ACHIEVEMENT = URL + "/my_achievement";//17、我的业绩
   // public static final String MY_ACHIEVEMENT = URL + "/my_achievement";//18、业绩列表
    public static final String ACHIEVEMENT_AMOUNT = URL + "/achievement_amount";//19、获得一段时间内的业绩总和
}
