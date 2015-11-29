package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by ll on 2015/11/29.
 */
public class BannerInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<BannerList> bannerListList;

        public List<BannerList> getBannerListList() {
            return bannerListList;
        }

        public void setBannerListList(List<BannerList> bannerListList) {
            this.bannerListList = bannerListList;
        }
    }

    public class BannerList implements BaseEntity {
        private String url;
        private String picture;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }
}