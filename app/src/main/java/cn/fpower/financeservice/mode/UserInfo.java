package cn.fpower.financeservice.mode;

import android.text.TextUtils;

public class UserInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private int id; //18,
        private int tissue_id;//0
        private int shop_id;//"0",
        private int entering_uid;//"0",
        private String mobile; //"13516599661",
        private String username;//"uhb",
        private String passwd;// "Z3p3ZWltaWFvZjYzYTVmYmViNjQyMWU1NTQ5NzBiYTA2MDk4NjAyZjA=",
        private String face;// "/uploads/avatar/default_face.png",
        private String birthday;// "1989-09-27",
        private int sex;//"1",
        private long province_id;// "110000",
        private int city_id;//"110100",
        private int district_id;//"110101",
        private String address;// "",
        private int register_source;// "2",
        private Long addtime; //"1449898335",
        private Long uptime;// "1449903335",
        private Long reg_time;// "1449898335",
        private int status;//"1"
        private String shop_name;
        private String shop_img;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDistrict_id() {
            return district_id;
        }

        public void setDistrict_id(int district_id) {
            this.district_id = district_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public long getProvince_id() {
            return province_id;
        }

        public void setProvince_id(long province_id) {
            this.province_id = province_id;
        }

        public int getEntering_uid() {
            return entering_uid;
        }

        public void setEntering_uid(int entering_uid) {
            this.entering_uid = entering_uid;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getTissue_id() {
            return tissue_id;
        }

        public void setTissue_id(int tissue_id) {
            this.tissue_id = tissue_id;
        }

        public Long getAddtime() {
            return addtime;
        }

        public void setAddtime(Long addtime) {
            this.addtime = addtime;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public Long getReg_time() {
            return reg_time;
        }

        public void setReg_time(Long reg_time) {
            this.reg_time = reg_time;
        }

        public int getRegister_source() {
            return register_source;
        }

        public void setRegister_source(int register_source) {
            this.register_source = register_source;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Long getUptime() {
            return uptime;
        }

        public void setUptime(Long uptime) {
            this.uptime = uptime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getShop_name() {
            if(TextUtils.isEmpty(shop_name)){
                return "无";
            }
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_img() {
            return shop_img;
        }

        public void setShop_img(String shop_img) {
            this.shop_img = shop_img;
        }
    }
}
