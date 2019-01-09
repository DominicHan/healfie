package com.fn.healfie.model;

import java.util.List;

public class DrugsSearchBean extends BaseBean {


    /**
     * item : [{"takeMode":"1","name":"123(123)","id":1},{"takeMode":"1","name":"阿莫西林(amoxilin)","id":3},{"takeMode":"1","name":"阿莫西林(amoxilin)","id":4},{"takeMode":"1","name":"1(11)","id":5},{"takeMode":"1","name":"阿司匹林(Asipilin)","id":6},{"takeMode":"1","name":"感康(Gankang)","id":7},{"takeMode":"1","name":"感康(Gankanh)","id":8},{"takeMode":"1","name":"1(1)","id":9},{"takeMode":"1","name":"1(1)","id":10},{"takeMode":"1","name":"1(1)","id":11}]
     * resultCode : 200
     */

    private List<ItemBean> item;

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean {
        /**
         * takeMode : 1
         * name : 123(123)
         * id : 1
         */
        public ItemBean(String takeMode,String name){
            this.takeMode = takeMode;
            this.name = name;
        }

        private String takeMode;
        private String name;
        private int id;

        public String getTakeMode() {
            return takeMode;
        }

        public void setTakeMode(String takeMode) {
            this.takeMode = takeMode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
