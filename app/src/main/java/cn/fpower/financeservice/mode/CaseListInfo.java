package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by lll on 2015/12/17.
 */
public class CaseListInfo extends BaseMode {

    private Data data;

    private int case_total;

    private int page_size;

    private int now_page;

    public int getCase_total() {
        return case_total;
    }

    public void setCase_total(int case_total) {
        this.case_total = case_total;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<DataInfo> case_list;

        public List<DataInfo> getCase_list() {
            return case_list;
        }

        public void setCase_list(List<DataInfo> case_list) {
            this.case_list = case_list;
        }
    }
}
