package com.joy.app.bean.sample;

import com.android.library.utils.TextUtil;

import java.util.ArrayList;

/**
 * 城市详情
 */
public class CityDetail {

    private String id = TextUtil.TEXT_EMPTY;
    private String country_id = TextUtil.TEXT_EMPTY;
    private String chinesename = TextUtil.TEXT_EMPTY;
    private String englishname = TextUtil.TEXT_EMPTY;
    private ArrayList<String> photos;
    private ArrayList<Trip> new_trip;

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = TextUtil.filterNull(id);
    }

    public String getCountry_id() {

        return country_id;
    }

    public void setCountry_id(String country_id) {

        this.country_id = TextUtil.filterNull(country_id);
    }

    public String getChinesename() {

        return chinesename;
    }

    public void setChinesename(String chinesename) {

        this.chinesename = TextUtil.filterNull(chinesename);
    }

    public String getEnglishname() {

        return englishname;
    }

    public void setEnglishname(String englishname) {

        this.englishname = TextUtil.filterNull(englishname);
    }

    public ArrayList<String> getPhotos() {

        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {

        this.photos = photos;
    }

    public ArrayList<Trip> getNew_trip() {

        return new_trip;
    }

    public void setNew_trip(ArrayList<Trip> new_trip) {

        this.new_trip = new_trip;
    }

    @Override
    public String toString() {
        return "CityDetail{" +
                "id='" + id + '\'' +
                ", country_id='" + country_id + '\'' +
                ", chinesename='" + chinesename + '\'' +
                ", englishname='" + englishname + '\'' +
                ", photos=" + photos +
                ", new_trip=" + new_trip +
                '}';
    }
}