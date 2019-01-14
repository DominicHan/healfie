package com.fn.healfie.model;

import java.util.List;

/**
 * author: sail
 * date :  2019/1/14
 * desc :  NewsListBean
 */
public class NewsListBean extends BaseBean {

    private List<NewsBean> item;

    public List<NewsBean> getItem() {
        return item;
    }

    public void setItem(List<NewsBean> item) {
        this.item = item;
    }
}
