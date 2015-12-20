package cn.fpower.financeservice.mode;

import java.util.List;

/**
 * Created by Administrator on 2015/12/20.
 */
public class MyAchievement extends BaseMode {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements BaseEntity {
        private List<Achievement> achievement_list;
        private int achievement_total;
        private String month_amount;
        public List<Achievement> getAchievement_list() {
            return achievement_list;
        }
        public void setAchievement_list(List<Achievement> achievement_list) {
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
