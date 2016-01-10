package cn.fpower.financeservice.mode;

import java.util.List;

/**
 *  业绩列表
 */
public class AchievementList extends BaseMode {

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
        private int page_size;
        private int now_page;
        private UserInfo.Data user_info;
        public UserInfo.Data getUser_info() {
            return user_info;
        }
        public void setUser_info(UserInfo.Data user_info) {
            this.user_info = user_info;
        }
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
    }
}
