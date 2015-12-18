package cn.fpower.financeservice.mode;


public class BaseMode implements BaseEntity {
    private int code;

    private String message;

    private Page page;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Page implements BaseEntity {
        private int now_page;
        private int page_size;
        private int case_total;

        public int getNow_page() {
            return now_page;
        }

        public void setNow_page(int now_page) {
            this.now_page = now_page;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getCase_total() {
            return case_total;
        }

        public void setCase_total(int case_total) {
            this.case_total = case_total;
        }
    }


}
