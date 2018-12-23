package com.fn.healfie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/11/6.
 */

public class FoodBean {

    /**
     * id : 2
     * memberId : 15
     * foodCode : null
     * name : 1
     * unit : null
     * unitValue : null
     * eatNumber : null
     * eatDate : 2018-12-21
     * eatTime : 19:07:00
     * showLimit : 1
     * calorie : 1
     * fat : 1
     * sodium : 1
     * carbohydrate : 1
     * src : 2
     * status : 1
     * imageObject : 2018-12-21/00ab862eb3f54687bb458d303104f9fe.jpg
     * bucket : food
     * isOtherAdd : 0
     * otherMemberId : null
     * aduitStatus : 1
     * createTime : 1545390451000
     * createDate : 1545350400000
     * updateTime : null
     * remarks : null
     */

    private int id;
    private int memberId;
    private String foodCode;
    private String name;
    private String unit;
    private String unitValue;
    private String eatNumber;
    private String eatDate;
    private String eatTime;
    private int showLimit;
    private int calorie;
    private int fat;
    private int sodium;
    private int carbohydrate;
    private int src;
    @SerializedName("status")
    private int statusX;
    private String imageObject;
    private String bucket;
    private int isOtherAdd;
    private String otherMemberId;
    private int aduitStatus;
    private long createTime;
    private long createDate;
    private String updateTime;
    private String remarks;

    public FoodBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getEatNumber() {
        return eatNumber;
    }

    public void setEatNumber(String eatNumber) {
        this.eatNumber = eatNumber;
    }

    public String getEatDate() {
        return eatDate;
    }

    public void setEatDate(String eatDate) {
        this.eatDate = eatDate;
    }

    public String getEatTime() {
        return eatTime;
    }

    public void setEatTime(String eatTime) {
        this.eatTime = eatTime;
    }

    public int getShowLimit() {
        return showLimit;
    }

    public void setShowLimit(int showLimit) {
        this.showLimit = showLimit;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public String getImageObject() {
        return imageObject;
    }

    public void setImageObject(String imageObject) {
        this.imageObject = imageObject;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public int getIsOtherAdd() {
        return isOtherAdd;
    }

    public void setIsOtherAdd(int isOtherAdd) {
        this.isOtherAdd = isOtherAdd;
    }

    public String getOtherMemberId() {
        return otherMemberId;
    }

    public void setOtherMemberId(String otherMemberId) {
        this.otherMemberId = otherMemberId;
    }

    public int getAduitStatus() {
        return aduitStatus;
    }

    public void setAduitStatus(int aduitStatus) {
        this.aduitStatus = aduitStatus;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
