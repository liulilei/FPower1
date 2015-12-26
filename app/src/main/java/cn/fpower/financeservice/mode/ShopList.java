package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by Administrator on 2015/12/20.
 */
public class ShopList extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {

        private int shop_total;
        private int page_size;
        private int now_page;

        public int getShop_total() {
            return shop_total;
        }

        public void setShop_total(int shop_total) {
            this.shop_total = shop_total;
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

        private List<ShopInfo> shop_list;

        public List<ShopInfo> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<ShopInfo> shop_list) {
            this.shop_list = shop_list;
        }
    }
}
