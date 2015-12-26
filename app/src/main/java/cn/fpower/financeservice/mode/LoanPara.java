package cn.fpower.financeservice.mode;

import android.text.TextUtils;

/**
 * 11、录入贷款 请求参数
 */
public class LoanPara {
    public int user_id;//	 当前登录用户id	整型
    public String realname;//	真实姓名	字符串
    public String username;//
    public String longitude;
    public String latitude;
    public String name;//
    public int age;//	年龄	整型
    public String mobile;//	联系电话	长整
    public String money;//	借款金额	浮点
    public String province_id;//	省份id	整型
    public String city_id;//	城市id	整型
    public String district_id;//	地区id	整型
    public String address;//	地址	字符串
    public String channel;//	来源那个店铺（渠道）
    public int is_fund;//	是否有公积金	整型；0否，1是
    public int is_social_security;//	是否有社保	整型；0否，1是
    public int is_bank_pay;//	是否银行代发工资	整型；0否，1是
    public double salary;//	薪水	浮点
    public int is_credit_card;//	是否有信用卡	整型；0否，1是
    public double credit_card_quota;//	信用卡额度	浮点
    public String household;//	户口	字符串
    public String is_housing;//	是否有房产	整型；0否，1是
    public int is_car;//	是否有车	整型；0否，1是
    public String is_loan;//	是否贷款	整型；0否，1是
    public int is_insurance;//	是否有保险	整型；0否，1是
    public int identity;//	身份	整型；1公务员', 2事业单位，3国企,4央企,5医生,6上市公司, 7教师, 8个体户, 9企业老板
    public int source = 2;//	来源	0网站，1ios， 2android

    public void checkLoan() throws Exception {
        if (TextUtils.isEmpty(realname)) {
            throw new Exception("请输入姓名");
        }
        if (TextUtils.isEmpty(mobile)) {
            throw new Exception("请输入电话");
        }
        if (TextUtils.isEmpty(money)) {
            throw new Exception("请输入金额");
        }
        if (TextUtils.isEmpty(province_id)) {
            throw new Exception("请选择地址");
        }
        if (TextUtils.isEmpty(address)) {
            throw new Exception("请输入详细地址");
        }
        if (TextUtils.isEmpty(is_housing)) {
            throw new Exception("请选择房产");
        }
        if (TextUtils.isEmpty(is_loan)) {
            throw new Exception("请选择贷款");
        }
    }

    public void checkShop() throws Exception {
        if (TextUtils.isEmpty(name)) {
            throw new Exception("请输入店名");
        }
        if (TextUtils.isEmpty(province_id)) {
            throw new Exception("请选择地址");
        }
        if (TextUtils.isEmpty(address)) {
            throw new Exception("请输入详细地址");
        }
        if(TextUtils.isEmpty(longitude)){
            throw new Exception("请刷新经纬度");
        }
        if (TextUtils.isEmpty(username)) {
            throw new Exception("请输入店长名称");
        }
        if (TextUtils.isEmpty(mobile)) {
            throw new Exception("请输入电话");
        }
    }


}
