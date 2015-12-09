package com.joy.app.bean.city;

/**
 * Created by KEVIN.DAI on 15/12/9.
 */
public class CityRoute {

    /**
     * route_id : steM+pm2pOE=
     * route_name : 北京一日游路线
     * route_day : 1
     * pic_url : http://xx.com//....
     * route_url : http://xx.com//....
     */

    private String route_id;
    private String route_name;
    private String route_day;
    private String pic_url;
    private String route_url;

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public void setRoute_day(String route_day) {
        this.route_day = route_day;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setRoute_url(String route_url) {
        this.route_url = route_url;
    }

    public String getRoute_id() {
        return route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public String getRoute_day() {
        return route_day;
    }

    public String getPic_url() {
        return pic_url;
    }

    public String getRoute_url() {
        return route_url;
    }
}
