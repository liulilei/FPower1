package cn.fpower.financeservice.mode;

public class UserInfo extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private Long addtime;//1449074097",
        private String birthday;//生日
        private String face;//头像 "0",
        private int group_id;//"1",
        private int id;//"3",
        private String mobile;//手机号 13516599661"
        private String passwd;//密码 加密
        private int rank;//0",
        private Long reg_time;// 1449074097",
        private String region;
        private int register_source;//来源 1、IOS 2、安卓
        private int sex;//性别[0,人妖，1男，2女]
        private int status;//"1",
        private Long uptime;// 1449074097",
        private String username;//用户名 "0",

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

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
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

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public Long getReg_time() {
            return reg_time;
        }

        public void setReg_time(Long reg_time) {
            this.reg_time = reg_time;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
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
    }


}
