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

    public class Data implements BaseEntity {
        private List<Shop_list> shop_list;

        public List<Shop_list> getShop_list() {
            return shop_list;
        }

        public void setShop_list(List<Shop_list> shop_list) {
            this.shop_list = shop_list;
        }
    }

    public class Shop_list implements BaseEntity {
        public Long id;// "16",
        /*imgs; [
                "a.gzweimiao.com/uploads/image/20151212/353876a5f62e8dcef112e17977cdc888.JPEG",
                "a.gzweimiao.com/uploads/image/20151212/ccb8b13ad4ee8436d6405c6c7532fb2a.JPEG"
                ],*/

        public List<String> imgs;
        public String name;// "我爱我家（华景店）",
        public Long user_id;// "25",
        public String rec_user_id;// "17",
        public String province_id;// "360000",
        public String city_id;// "360200",
        public String district_id;// "360202",
        public String longitude;// "113.33",
        public String latitude;// "23.15",
        public String address;// "0",
        public Long addtime;// "1449914639",
        public Long uptime;// "1449914639",
        public Long status;// "1",
        public String username;// "江稳",
        public String mobile;// "18110705012"
    }
}
