package cn.fpower.financeservice.mode;

/**
 * 检查版本
 */
public class Version extends BaseMode {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        public String url;//: "http://a.gzweimiao.com/android.apk",
        public double version;//: "1.0",
        public String message;//: "更多惊喜赶快更新吧~~"
    }
}
