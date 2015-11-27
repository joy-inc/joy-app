package com.joy.app.bean.plan;

/**
 * @author litong  <br>
 * @Description 旅行规划    <br>
 */
public class PlanItem {
//            "plan_id":"zluoqRYxTZY=",
//            "cn_name":"清水寺",
//            "en_name":"qingshuisi",
//            "price":"134.8",
//            "before_day":"3",
//            "pic_url" :"http://xx.com/photo...",
//            "lon" : "23.22",
//            "lat" : "144.33",
    String plan_id,cn_name,en_name,before_day,price,pic_url,lon,lat;

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getBefore_day() {
        return before_day;
    }

    public void setBefore_day(String before_day) {
        this.before_day = before_day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
