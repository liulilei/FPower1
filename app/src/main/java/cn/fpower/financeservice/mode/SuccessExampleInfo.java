package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by ll on 2015/11/30.
 */
public class SuccessExampleInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<SuccessExample> successExample;

        public List<SuccessExample> getSuccessExample() {
            return successExample;
        }

        public void setSuccessExample(List<SuccessExample> successExample) {
            this.successExample = successExample;
        }
    }

    public class SuccessExample implements BaseEntity {

    }


}
