package com.joy.app.bean;

import com.android.library.utils.TextUtil;

/**
 * 首页的路线列表
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainRoute {

    private String place_id = TextUtil.TEXT_EMPTY;
    private String cn_name = TextUtil.TEXT_EMPTY;//中文名称
    private String en_name = TextUtil.TEXT_EMPTY;//第二名称
    private String tags = TextUtil.TEXT_EMPTY;//标签
    private int type = 1;//1城市 2专题
    private String place_url = TextUtil.TEXT_EMPTY;//旅游地的详情url
    private String pic_url = TextUtil.TEXT_EMPTY;//图片地址


    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = TextUtil.filterNull(place_id);
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = TextUtil.filterNull(cn_name);
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = TextUtil.filterNull(en_name);
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = TextUtil.filterNull(tags);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlace_url() {
        return place_url;
    }

    public void setPlace_url(String place_url) {
        this.place_url = TextUtil.filterNull(place_url);
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = TextUtil.filterNull(pic_url);
    }

    /**
     * 判断是否是城市
     *
     * @return
     */
    public boolean isCity() {
        return type == 1;
    }
}
