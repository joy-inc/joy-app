package com.joy.app.bean.plan;

/**
 * @author litong  <br>
 * @Description 旅行规划    <br>
 */
public class PlanItem {
//    "plan_id":"bTwr5b0c8Ow=",
//            "cn_name":"伏见稻荷大社",
//            "en_name":"fjdhds",
//            "poi_name":"京都古寺",
//            "pic_url" :"http://xx.com/photo...",
//            "lon" : "33.22",
//            "lat" : "101.33",
    String plan_id,cn_name,en_name,poi_name,pic_url,lon,lat;

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

    public String getPoi_name() {
        return poi_name;
    }

    public void setPoi_name(String poi_name) {
        this.poi_name = poi_name;
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
}
