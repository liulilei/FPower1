package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * 审核列表
 **/
public class LoanInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<DataInfo> loan_list;

        public List<DataInfo> getLoan_list() {
            return loan_list;
        }
        public void setLoan_list(List<DataInfo> loan_list) {
            this.loan_list = loan_list;
        }
    }

    public class DataInfo implements BaseEntity {

        private int id;// "2",
        private int user_id;// "18",
        private String realname;// "test",
        private int age;// "0",
        private String mobile;// "13516599662",
        private String money;// "11.11",
        private int province_id;// "110000",
        private int city_id;// "110100",
        private int district_id;// "110101",
        private String address;// "北京市 北京市 东城区",
        private int channel;// "0",
        private int is_fund;// "0",
        private int is_social_security;// "0",
        private int is_bank_pay;// "0",
        private String salary;// "0.00",
        private int is_credit_card;// "0",
        private String credit_card_quota;// "0.00",
        private int household;// "0",
        private int is_housing;// "1",
        private int is_car;// "0",
        private int is_loan;// "0",
        private int process;// "0",
        private int is_insurance;// "0",
        private int identity;// "0",
        private Long addtime;// "1449911529", 申请时间
        private Long uptime;// "1449911529",
        private Long audit_success_time;// "0",
        private Long audit_fail_time;// "0",
        private Long apply_success_time;// "0",
        private Long apply_fail_time;// "0",
        private int status;// "1",
        private int source;// "0"

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getProvince_id() {
            return province_id;
        }

        public void setProvince_id(int province_id) {
            this.province_id = province_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public int getIs_fund() {
            return is_fund;
        }

        public void setIs_fund(int is_fund) {
            this.is_fund = is_fund;
        }

        public int getIs_social_security() {
            return is_social_security;
        }

        public void setIs_social_security(int is_social_security) {
            this.is_social_security = is_social_security;
        }

        public int getIs_bank_pay() {
            return is_bank_pay;
        }

        public void setIs_bank_pay(int is_bank_pay) {
            this.is_bank_pay = is_bank_pay;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public int getIs_credit_card() {
            return is_credit_card;
        }

        public void setIs_credit_card(int is_credit_card) {
            this.is_credit_card = is_credit_card;
        }

        public String getCredit_card_quota() {
            return credit_card_quota;
        }

        public void setCredit_card_quota(String credit_card_quota) {
            this.credit_card_quota = credit_card_quota;
        }

        public int getHousehold() {
            return household;
        }

        public void setHousehold(int household) {
            this.household = household;
        }

        public int getIs_housing() {
            return is_housing;
        }

        public void setIs_housing(int is_housing) {
            this.is_housing = is_housing;
        }

        public int getIs_car() {
            return is_car;
        }

        public void setIs_car(int is_car) {
            this.is_car = is_car;
        }

        public int getIs_loan() {
            return is_loan;
        }

        public void setIs_loan(int is_loan) {
            this.is_loan = is_loan;
        }

        public int getProcess() {
            return process;
        }

        public void setProcess(int process) {
            this.process = process;
        }

        public int getIs_insurance() {
            return is_insurance;
        }

        public void setIs_insurance(int is_insurance) {
            this.is_insurance = is_insurance;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public Long getAddtime() {
            return addtime;
        }

        public void setAddtime(Long addtime) {
            this.addtime = addtime;
        }

        public Long getUptime() {
            return uptime;
        }

        public void setUptime(Long uptime) {
            this.uptime = uptime;
        }

        public Long getAudit_success_time() {
            return audit_success_time;
        }

        public void setAudit_success_time(Long audit_success_time) {
            this.audit_success_time = audit_success_time;
        }

        public Long getAudit_fail_time() {
            return audit_fail_time;
        }

        public void setAudit_fail_time(Long audit_fail_time) {
            this.audit_fail_time = audit_fail_time;
        }

        public Long getApply_success_time() {
            return apply_success_time;
        }

        public void setApply_success_time(Long apply_success_time) {
            this.apply_success_time = apply_success_time;
        }

        public Long getApply_fail_time() {
            return apply_fail_time;
        }

        public void setApply_fail_time(Long apply_fail_time) {
            this.apply_fail_time = apply_fail_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
