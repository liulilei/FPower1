/*
*BaseEntity.java
*Created on 2014-10-10 上午9:58 by Ivan
*Copyright(c)2014 Guangzhou Onion Information Technology Co., Ltd.
*http://www.cniao5.com
*/
package cn.fpower.financeservice.mode;

import java.io.Serializable;

/**
 * Created by Ivan on 14-10-10.
 * Copyright(c)2014 Guangzhou Onion Information Technology Co., Ltd.
 * http://www.cniao5.com
 */
public class BaseEntity implements Serializable {
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
