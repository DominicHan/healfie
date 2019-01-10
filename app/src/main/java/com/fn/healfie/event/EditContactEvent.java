package com.fn.healfie.event;

import com.fn.healfie.model.MessageBean;

public class EditContactEvent extends BaseEvent {

    private MessageBean bean;

    public MessageBean getMsg() {
        return bean;
    }

    public void setMsg(MessageBean bean) {
        this.bean = bean;
    }
}
