package cn.fpower.financeservice.mode;

/**
 * 16、贷款详情
 **/
public class LoanDetail extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        public String id;//5",
        public String user_id;//18",
        public String realname;//tttt",
        public String age;//0",
        public String mobile;//13516599663",
        public String money;//125.00",
        public String province_id;//410000",
        public String city_id;//410100",
        public String district_id;//410182",
        public String address;//ttg",
        public String channel;//0",
        public String is_fund;//0",
        public String is_social_security;//0",
        public String is_bank_pay;//0",
        public String salary;//0.00",
        public String is_credit_card;//0",
        public String credit_card_quota;//0.00",
        public String household;//0",
        public String is_housing;//1",
        public String is_car;//0",
        public String is_loan;//0",
        public String process;//0",
        public String is_insurance;//0",
        public String identity;//0",
        public String addtime;//1450594309",
        public String uptime;//1450594309",
        public Long audit_success_time;//0",
        public Long audit_fail_time;//0",
        public Long apply_success_time;//0",
        public Long apply_fail_time;//0",
        public String status;//1",
        public String source;//2"
    }
}
