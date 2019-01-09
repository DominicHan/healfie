package com.fn.healfie.model;

import java.util.List;

public class ContactListBean extends BaseBean {
    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        private List<MessageBean> list;
        private int auditNumber;

        public List<MessageBean> getList() {
            return list;
        }

        public void setList(List<MessageBean> list) {
            this.list = list;
        }

        public int getAuditNumber() {
            return auditNumber;
        }

        public void setAuditNumber(int auditNumber) {
            this.auditNumber = auditNumber;
        }
    }
}
