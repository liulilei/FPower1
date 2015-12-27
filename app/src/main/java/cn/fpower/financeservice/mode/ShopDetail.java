package cn.fpower.financeservice.mode;

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
        private cn.fpower.financeservice.mode.ShopData shop_info;

        public ShopData getShop_info() {
            return shop_info;
        }

        public void setShop_info(ShopData shop_info) {
            this.shop_info = shop_info;
        }
    }
}
