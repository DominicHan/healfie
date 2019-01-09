package com.fn.healfie.model;

import com.google.gson.annotations.SerializedName;

public class MineBean extends BaseBean {


    /**
     * item : {"id":41,"name":"测试","phoneNumber":"12345678912","userName":null,"imageObject":"2019-01-10/533de296e4d34dbdb197eb218cc83981.png","bucket":"head","password":null,"mobileId":"869111042988514","mobilePlatform":2,"otherOpenId":null,"isBindFaceBook":0,"sex":null,"status":1,"lables":null,"labelIds":null,"labelNames":null,"memberMedicalCard":null,"createTime":1547049328000,"lastLoginTime":1547055604000,"updateTime":null,"remarks":null,"registrationId":"3cc43ff387b2445fb030814ad3266185"}
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
         * id : 41
         * name : 测试
         * phoneNumber : 12345678912
         * userName : null
         * imageObject : 2019-01-10/533de296e4d34dbdb197eb218cc83981.png
         * bucket : head
         * password : null
         * mobileId : 869111042988514
         * mobilePlatform : 2
         * otherOpenId : null
         * isBindFaceBook : 0
         * sex : null
         * status : 1
         * lables : null
         * labelIds : null
         * labelNames : null
         * memberMedicalCard : null
         * createTime : 1547049328000
         * lastLoginTime : 1547055604000
         * updateTime : null
         * remarks : null
         * registrationId : 3cc43ff387b2445fb030814ad3266185
         */

        private int id;
        private String name;
        private String phoneNumber;
        private Object userName;
        private String imageObject;
        private String bucket;
        private Object password;
        private String mobileId;
        private int mobilePlatform;
        private Object otherOpenId;
        private int isBindFaceBook;
        private Object sex;
        @SerializedName("status")
        private int statusX;
        private Object lables;
        private Object labelIds;
        private Object labelNames;
        private Object memberMedicalCard;
        private long createTime;
        private long lastLoginTime;
        private Object updateTime;
        private Object remarks;
        private String registrationId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
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

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getMobileId() {
            return mobileId;
        }

        public void setMobileId(String mobileId) {
            this.mobileId = mobileId;
        }

        public int getMobilePlatform() {
            return mobilePlatform;
        }

        public void setMobilePlatform(int mobilePlatform) {
            this.mobilePlatform = mobilePlatform;
        }

        public Object getOtherOpenId() {
            return otherOpenId;
        }

        public void setOtherOpenId(Object otherOpenId) {
            this.otherOpenId = otherOpenId;
        }

        public int getIsBindFaceBook() {
            return isBindFaceBook;
        }

        public void setIsBindFaceBook(int isBindFaceBook) {
            this.isBindFaceBook = isBindFaceBook;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public Object getLables() {
            return lables;
        }

        public void setLables(Object lables) {
            this.lables = lables;
        }

        public Object getLabelIds() {
            return labelIds;
        }

        public void setLabelIds(Object labelIds) {
            this.labelIds = labelIds;
        }

        public Object getLabelNames() {
            return labelNames;
        }

        public void setLabelNames(Object labelNames) {
            this.labelNames = labelNames;
        }

        public Object getMemberMedicalCard() {
            return memberMedicalCard;
        }

        public void setMemberMedicalCard(Object memberMedicalCard) {
            this.memberMedicalCard = memberMedicalCard;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
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

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }
    }
}
