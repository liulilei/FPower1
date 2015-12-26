package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * 15、贷款列表（进度）
 **/
public class LoanInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private List<DataInfo> loan_list;
        private int loan_total;
        private int page_size;
        private int now_page;

        public void setLoan_list(List<DataInfo> loan_list) {
            this.loan_list = loan_list;
        }

        public int getLoan_total() {
            return loan_total;
        }

        public void setLoan_total(int loan_total) {
            this.loan_total = loan_total;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getNow_page() {
            return now_page;
        }

        public void setNow_page(int now_page) {
            this.now_page = now_page;
        }

        public List<DataInfo> getLoan_list() {
            return loan_list;
        }

    }
}
