package com.joy.app.bean.sample;

import com.android.library.utils.TextUtil;

import java.util.ArrayList;

/**
 * 目的地详情
 * <p/>
 * Created by xiaoyu.chen on 15/11/11.
 */
public class PoiDetail {

    private static final long serialVersionUID = 1L;

    private String product_id = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String sub_title = TextUtil.TEXT_EMPTY;
    private String comment_level = TextUtil.TEXT_EMPTY;
    private String comment_num = TextUtil.TEXT_EMPTY;
    private String price = TextUtil.TEXT_EMPTY;
    private String lat = TextUtil.TEXT_EMPTY;
    private String lon = TextUtil.TEXT_EMPTY;
    private ArrayList<String> highlights;
    private String description = TextUtil.TEXT_EMPTY;
    private String is_book = TextUtil.TEXT_EMPTY;
    private ArrayList<String> photos;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getComment_level() {
        return comment_level;
    }

    public void setComment_level(String comment_level) {
        this.comment_level = comment_level;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public ArrayList<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(ArrayList<String> highlights) {
        this.highlights = highlights;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIs_book() {
        return "1".equals(is_book);
    }

    public void setIs_book(String is_book) {
        this.is_book = is_book;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}
