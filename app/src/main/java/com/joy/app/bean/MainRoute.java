package com.joy.app.bean;

import com.joy.library.utils.TextUtil;

/**
 * 首页的路线列表
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainRoute {

    private String imageUrl = TextUtil.TEXT_EMPTY;
    private String id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
