package com.fn.healfie.model;

import java.util.List;

public class MessageListBean extends BaseBean {

    private ItemBean item;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public static class ItemBean {
        private List<MessageBean> audited;
        private List<MessageBean> unaudited;

        public List<MessageBean> getAudited() {
            return audited;
        }

        public void setAudited(List<MessageBean> audited) {
            this.audited = audited;
        }

        public List<MessageBean> getUnaudited() {
            return unaudited;
        }

        public void setUnaudited(List<MessageBean> unaudited) {
            this.unaudited = unaudited;
        }
    }
}
