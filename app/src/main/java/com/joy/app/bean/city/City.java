package com.joy.app.bean.city;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class City {

    /**
     * place_id : JmwMFx+4+5o=
     * city_id : 12
     * cn_name : 东京
     * en_name : Tokyo
     * pic_url : http://xx.com/pic...
     * ticket_url : http://xx.com/..
     * visa_url : http://xx.com/....
     * traffic_url : http://xx.com/....
     * wifi_url : http://xx.com/....
     */

    private String place_id;
    private int city_id;
    private String cn_name;
    private String en_name;
    private String pic_url;
    private String ticket_url;
    private String visa_url;
    private String traffic_url;
    private String wifi_url;

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setTicket_url(String ticket_url) {
        this.ticket_url = ticket_url;
    }

    public void setVisa_url(String visa_url) {
        this.visa_url = visa_url;
    }

    public void setTraffic_url(String traffic_url) {
        this.traffic_url = traffic_url;
    }

    public void setWifi_url(String wifi_url) {
        this.wifi_url = wifi_url;
    }

    public String getPlace_id() {
        return place_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCn_name() {
        return cn_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getTicket_url() {
        return ticket_url;
    }

    public String getVisa_url() {
        return visa_url;
    }

    public String getTraffic_url() {
        return traffic_url;
    }

    public String getWifi_url() {
        return wifi_url;
    }
}