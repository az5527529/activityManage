package com.vo;

import java.util.List;

/**
 * Created by victor on 2018/3/2.
 */
public class Page {
    //数据
    private List rows;
    //总记录数
    private long total = 0;

    public List getRows() {
        return rows;
    }

    public void setRows(List list) {
        this.rows = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
