package com.fn.healfie.model;

/**
 * Created by Administrator on 2018/11/2.
 */

public class RegisterBean extends BaseBean {

    /**
     * item : {"authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0LDAiLCJleHAiOjE1NDExNTEyODF9.8ywwcZLLQ_VkOJwIagPq5qODM_RG_g7gAEf5tpr2TtZhIe4s2ABsCW7UmBgljfbmn0Z089ry5jNBZtURcUiTlQ","name":null}
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
         * authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0LDAiLCJleHAiOjE1NDExNTEyODF9.8ywwcZLLQ_VkOJwIagPq5qODM_RG_g7gAEf5tpr2TtZhIe4s2ABsCW7UmBgljfbmn0Z089ry5jNBZtURcUiTlQ
         * name : null
         */

        private String authorization;
        private Object name;

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }
    }
}
