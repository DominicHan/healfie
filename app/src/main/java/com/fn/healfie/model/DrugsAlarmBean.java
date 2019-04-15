package com.fn.healfie.model;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DrugsAlarmBean {
    private int drugsId;
    private String startDate;
    private String endDate;
    private String eatTime;
    private int remindMode;
    private int remindValue;

    public int getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(int drugsId) {
        this.drugsId = drugsId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEatTime() {
        return eatTime;
    }

    public void setEatTime(String eatTime) {
        this.eatTime = eatTime;
    }

    public int getRemindMode() {
        return remindMode;
    }

    public void setRemindMode(int remindMode) {
        this.remindMode = remindMode;
    }

    public int getRemindValue() {
        return remindValue;
    }

    public void setRemindValue(int remindValue) {
        this.remindValue = remindValue;
    }
}
