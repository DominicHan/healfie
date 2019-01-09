package com.fn.healfie.model;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DrugsBean {


    /**
     * id : 2
     * memberId : 15
     * drugsId : 7
     * cnName : 1(1)
     * nameHeadChar : 1
     * enName : 1
     * takeMode : 1
     * takeUnit : 粒
     * takeDose : 1
     * shape : null
     * number : null
     * showLimit : 1
     * eatDate : 2019-01-08
     * eatTime : 17:25:00
     * src : 2
     * status : 1
     * imageObject : 2019-01-08/ca1f84190baa4768aef2a60cdeb12c97.jpg
     * bucket : userdrugs
     * isOtherAdd : 0
     * otherMemberId : null
     * aduitStatus : 1
     * createTime : 1546939537000
     * createDate : 1546876800000
     * updateTime : null
     * remarks : null
     * needRemind : null
     */

    private int id;
    private int memberId;
    private int drugsId;
    private String cnName;
    private String nameHeadChar;
    private String enName;
    private String takeMode;
    private String takeUnit;
    private String takeDose;
    private Object shape;
    private Object number;
    private int showLimit;
    private String eatDate;
    private String eatTime;
    private int src;
    private int status;
    private String imageObject;
    private String bucket;
    private int isOtherAdd;
    private Object otherMemberId;
    private int aduitStatus;
    private long createTime;
    private long createDate;
    private Object updateTime;
    private Object remarks;
    private Object needRemind;

    public DrugsBean(int id, String cnName) {
        this.id = id;
        this.cnName = cnName;
        this.enName = cnName;
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

    public int getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(int drugsId) {
        this.drugsId = drugsId;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getNameHeadChar() {
        return nameHeadChar;
    }

    public void setNameHeadChar(String nameHeadChar) {
        this.nameHeadChar = nameHeadChar;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getTakeMode() {
        return takeMode;
    }

    public void setTakeMode(String takeMode) {
        this.takeMode = takeMode;
    }

    public String getTakeUnit() {
        return takeUnit;
    }

    public void setTakeUnit(String takeUnit) {
        this.takeUnit = takeUnit;
    }

    public String getTakeDose() {
        return takeDose;
    }

    public void setTakeDose(String takeDose) {
        this.takeDose = takeDose;
    }

    public Object getShape() {
        return shape;
    }

    public void setShape(Object shape) {
        this.shape = shape;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }

    public int getShowLimit() {
        return showLimit;
    }

    public void setShowLimit(int showLimit) {
        this.showLimit = showLimit;
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

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Object getNeedRemind() {
        return needRemind;
    }

    public void setNeedRemind(Object needRemind) {
        this.needRemind = needRemind;
    }
}
