package com.fn.healfie.model;

public class MessageBean {

    //审核
    private String headImageBucket;
    private String headImageObject;
    private long createTime;
    private String name;
    private int auditStatus;
    private String remark;
    private int memberId;
    private int type;

    //联系人
    private int showLimit;
    private int status;

    public int getShowLimit() {
        return showLimit;
    }

    public void setShowLimit(int showLimit) {
        this.showLimit = showLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeadImageBucket() {
        return headImageBucket;
    }

    public void setHeadImageBucket(String headImageBucket) {
        this.headImageBucket = headImageBucket;
    }

    public String getHeadImageObject() {
        return headImageObject;
    }

    public void setHeadImageObject(String headImageObject) {
        this.headImageObject = headImageObject;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
