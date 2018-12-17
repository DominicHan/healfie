package com.fn.healfie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/11/6.
 */

public class FoodListBean extends BaseBean {

    /**
     * total : 0
     * item : {}
     * stayAuditCount : 0
     * resultCode : 200
     * type : 1
     */

    private int total;
    private ItemBean item;
    private int stayAuditCount;
    private String type;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public int getStayAuditCount() {
        return stayAuditCount;
    }

    public void setStayAuditCount(int stayAuditCount) {
        this.stayAuditCount = stayAuditCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class ItemBean {
    }
}
