package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by lll on 2015/12/17.
 */
public class CaseListInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private List<DataInfo> case_list;

        public List<DataInfo> getCase_list() {
            return case_list;
        }

        public void setCase_list(List<DataInfo> case_list) {
            this.case_list = case_list;
        }
    }
}
