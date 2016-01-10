package cn.fpower.financeservice.mode;

/**
 * 19、获得一段时间内的业绩总和
 */
public class AchievementAmount extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private String achievement_amount;
        private UserInfo.Data user_info;

        public UserInfo.Data getUser_info() {
            return user_info;
        }
        public void setUser_info(UserInfo.Data user_info) {
            this.user_info = user_info;
        }

        public String getAchievement_amount() {
            return achievement_amount;
        }

        public void setAchievement_amount(String achievement_amount) {
            this.achievement_amount = achievement_amount;
        }
    }
}
