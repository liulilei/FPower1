package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by ll on 2015/12/12.
 */
public class HomeInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<AdItem> ad_item;
        private List<String> introduce;
        private List<DataInfo> loan_list;

        public List<AdItem> getAd_item() {
            return ad_item;
        }

        public void setAd_item(List<AdItem> ad_item) {
            this.ad_item = ad_item;
        }

        public List<String> getIntroduce() {
            return introduce;
        }

        public void setIntroduce(List<String> introduce) {
            this.introduce = introduce;
        }

        public List<DataInfo> getLoan_list() {
            return loan_list;
        }

        public void setLoan_list(List<DataInfo> loan_list) {
            this.loan_list = loan_list;
        }
    }

    public class AdItem implements BaseEntity {
        private String ad_img;
        private String ad_link;
        private String ad_title;
        private String addtime;
        private int id;
        private int parent_id;
        private int sorts;
        private int status;

        public String getAd_img() {
            return ad_img;
        }

        public void setAd_img(String ad_img) {
            this.ad_img = ad_img;
        }

        public String getAd_link() {
            return ad_link;
        }

        public void setAd_link(String ad_link) {
            this.ad_link = ad_link;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getSorts() {
            return sorts;
        }

        public void setSorts(int sorts) {
            this.sorts = sorts;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
