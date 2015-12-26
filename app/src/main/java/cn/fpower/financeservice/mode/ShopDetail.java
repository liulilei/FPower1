package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by Administrator on 2015/12/26.
 */
public class ShopDetail extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private ShopInfo shop_info;

        public ShopInfo getShop_info() {
            return shop_info;
        }

        public void setShop_info(ShopInfo shop_info) {
            this.shop_info = shop_info;
        }
    }
}
