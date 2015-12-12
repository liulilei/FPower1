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
}
