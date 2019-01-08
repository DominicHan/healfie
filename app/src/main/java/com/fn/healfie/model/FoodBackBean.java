package com.fn.healfie.model;

import com.google.gson.annotations.SerializedName;

public class FoodBackBean extends BaseBean {

    /**
     * item : {"id":9,"memberId":15,"foodCode":null,"name":"1","unit":null,"unitValue":null,"eatNumber":null,"eatDate":"2019-01-02","eatTime":"14:57:00","showLimit":1,"calorie":1,"fat":1,"sodium":1,"carbohydrate":1,"src":2,"status":1,"imageObject":"2019-01-02/d0bf688ba1e14dd2bf7cbfe9b54265aa.jpg","bucket":"food","isOtherAdd":0,"otherMemberId":null,"aduitStatus":1,"createTime":1546412243302,"createDate":1546412243302,"updateTime":null,"remarks":null}
     * resultCode : 200
     */

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * id : 9
         * memberId : 15
         * foodCode : null
         * name : 1
         * unit : null
         * unitValue : null
         * eatNumber : null
         * eatDate : 2019-01-02
         * eatTime : 14:57:00
         * showLimit : 1
         * calorie : 1
         * fat : 1
         * sodium : 1
         * carbohydrate : 1
         * src : 2
         * status : 1
         * imageObject : 2019-01-02/d0bf688ba1e14dd2bf7cbfe9b54265aa.jpg
         * bucket : food
         * isOtherAdd : 0
         * otherMemberId : null
         * aduitStatus : 1
         * createTime : 1546412243302
         * createDate : 1546412243302
         * updateTime : null
         * remarks : null
         */

        private int id;
        private int memberId;
        private Object foodCode;
        private String name;
        private Object unit;
        private Object unitValue;
        private Object eatNumber;
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
        private Object otherMemberId;
        private int aduitStatus;
        private long createTime;
        private long createDate;
        private Object updateTime;
        private Object remarks;

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

        public Object getFoodCode() {
            return foodCode;
        }

        public void setFoodCode(Object foodCode) {
            this.foodCode = foodCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public Object getUnitValue() {
            return unitValue;
        }

        public void setUnitValue(Object unitValue) {
            this.unitValue = unitValue;
        }

        public Object getEatNumber() {
            return eatNumber;
        }

        public void setEatNumber(Object eatNumber) {
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

        public Object getOtherMemberId() {
            return otherMemberId;
        }

        public void setOtherMemberId(Object otherMemberId) {
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

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }
    }
}
