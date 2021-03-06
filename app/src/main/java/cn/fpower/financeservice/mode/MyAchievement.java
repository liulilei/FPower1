package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * 我的业绩
 */
public class MyAchievement extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data extends BaseEntity {
        private List<AchievementData> achievement_list;
        private int achievement_total;
        private String month_amount;

        public List<AchievementData> getAchievement_list() {
            return achievement_list;
        }

        public void setAchievement_list(List<AchievementData> achievement_list) {
            this.achievement_list = achievement_list;
        }

        public int getAchievement_total() {
            return achievement_total;
        }

        public void setAchievement_total(int achievement_total) {
            this.achievement_total = achievement_total;
        }

        public String getMonth_amount() {
            return month_amount;
        }

        public void setMonth_amount(String month_amount) {
            this.month_amount = month_amount;
        }
    }
}
