package cn.fpower.financeservice.mode;

import java.util.List;

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

        public String getAchievement_amount() {
            return achievement_amount;
        }

        public void setAchievement_amount(String achievement_amount) {
            this.achievement_amount = achievement_amount;
        }
    }
}
