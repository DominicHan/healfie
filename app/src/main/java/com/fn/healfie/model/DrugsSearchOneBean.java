package com.fn.healfie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugsSearchOneBean extends BaseBean {

    /**
     * total : 1
     * item : [{"id":5,"drugsCode":"M1000005","cnName":"asda22222","enName":"","code":"","takeMode":"","takeDesc":"","saveMode":"","avoid":"","status":1,"src":1,"createUserId":13,"createUserName":"healfie","aduitStatus":1,"aduitDesc":null,"orderNum":5,"aduitTime":1540281875000,"createTime":1540281875000,"updateTime":1540281875000,"remarks":"","logs":null,"imageUrls":null}]
     * resultCode : 200
     */

    private int total;
    private List<ItemBean> item;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * id : 5
         * drugsCode : M1000005
         * cnName : asda22222
         * enName :
         * code :
         * takeMode :
         * takeDesc :
         * saveMode :
         * avoid :
         * status : 1
         * src : 1
         * createUserId : 13
         * createUserName : healfie
         * aduitStatus : 1
         * aduitDesc : null
         * orderNum : 5
         * aduitTime : 1540281875000
         * createTime : 1540281875000
         * updateTime : 1540281875000
         * remarks :
         * logs : null
         * imageUrls : null
         */

        private int id;
        private String drugsCode;
        private String cnName;
        private String enName;
        private String code;
        private String takeMode;
        private String takeDesc;
        private String saveMode;
        private String avoid;
        @SerializedName("status")
        private int statusX;
        private int src;
        private int createUserId;
        private String createUserName;
        private int aduitStatus;
        private Object aduitDesc;
        private int orderNum;
        private long aduitTime;
        private long createTime;
        private long updateTime;
        private String remarks;
        private Object logs;
        private Object imageUrls;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDrugsCode() {
            return drugsCode;
        }

        public void setDrugsCode(String drugsCode) {
            this.drugsCode = drugsCode;
        }

        public String getCnName() {
            return cnName;
        }

        public void setCnName(String cnName) {
            this.cnName = cnName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTakeMode() {
            return takeMode;
        }

        public void setTakeMode(String takeMode) {
            this.takeMode = takeMode;
        }

        public String getTakeDesc() {
            return takeDesc;
        }

        public void setTakeDesc(String takeDesc) {
            this.takeDesc = takeDesc;
        }

        public String getSaveMode() {
            return saveMode;
        }

        public void setSaveMode(String saveMode) {
            this.saveMode = saveMode;
        }

        public String getAvoid() {
            return avoid;
        }

        public void setAvoid(String avoid) {
            this.avoid = avoid;
        }

        public int getStatusX() {
            return statusX;
        }

        public void setStatusX(int statusX) {
            this.statusX = statusX;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public int getAduitStatus() {
            return aduitStatus;
        }

        public void setAduitStatus(int aduitStatus) {
            this.aduitStatus = aduitStatus;
        }

        public Object getAduitDesc() {
            return aduitDesc;
        }

        public void setAduitDesc(Object aduitDesc) {
            this.aduitDesc = aduitDesc;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public long getAduitTime() {
            return aduitTime;
        }

        public void setAduitTime(long aduitTime) {
            this.aduitTime = aduitTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public Object getLogs() {
            return logs;
        }

        public void setLogs(Object logs) {
            this.logs = logs;
        }

        public Object getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(Object imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}
