package com.fn.healfie.model;

public class SearchContactBean extends BaseBean {

    /**
     * item : {"headImageObject":null,"name":"t**t","headBucket":null}
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
         * headImageObject : null
         * name : t**t
         * headBucket : null
         */

        private Object headImageObject;
        private String name;
        private Object headBucket;

        public Object getHeadImageObject() {
            return headImageObject;
        }

        public void setHeadImageObject(Object headImageObject) {
            this.headImageObject = headImageObject;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getHeadBucket() {
            return headBucket;
        }

        public void setHeadBucket(Object headBucket) {
            this.headBucket = headBucket;
        }
    }
}
