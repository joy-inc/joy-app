package com.joy.app.bean.sample;

import com.android.library.utils.TextUtil;

/**
 * 专题
 * Created by KEVIN.DAI on 15/11/23.
 */
public class Special {

    /**
     * title : 嫌签证麻烦？全球免签海岛，挑一个走！
     * url : http://appview.qyer.com/op/zt/20150729.html
     * photo : http://pic.qyer.com/public/mobileapp/appsubject/2015/08/20/14400616667661/930x380
     */

    private String title = TextUtil.TEXT_EMPTY;
    private String url = TextUtil.TEXT_EMPTY;
    private String photo = TextUtil.TEXT_EMPTY;

    public void setTitle(String title) {

        this.title = TextUtil.filterNull(title);
    }

    public void setUrl(String url) {

        this.url = TextUtil.filterNull(url);
    }

    public void setPhoto(String photo) {

        this.photo = TextUtil.filterNull(photo);
    }

    public String getTitle() {

        return title;
    }

    public String getUrl() {

        return url;
    }

    public String getPhoto() {

        return photo;
    }
}
