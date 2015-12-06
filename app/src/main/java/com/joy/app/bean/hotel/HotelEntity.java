package com.joy.app.bean.hotel;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class HotelEntity {

    private int id;
    private String cnname;
    private String enname;
    private double ranking;
    private String star;
    private String area_name;
    private int price;
    private double lat;
    private double lon;
    private String distance;
    private boolean is_recommend;
    private String photo;
    private String link;

    public void setId(int id) {
        this.id = id;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setIs_recommend(boolean is_recommend) {
        this.is_recommend = is_recommend;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public String getCnname() {
        return cnname;
    }

    public String getEnname() {
        return enname;
    }

    public double getRanking() {
        return ranking;
    }

    public String getStar() {
        return star;
    }

    public String getArea_name() {
        return area_name;
    }

    public int getPrice() {
        return price;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getDistance() {
        return distance;
    }

    public boolean isIs_recommend() {
        return is_recommend;
    }

    public String getPhoto() {
        return photo;
    }

    public String getLink() {
        return link;
    }
}
