package com.joy.app.bean.hotel;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class EntryEntity {

    private int city_id;
    private String cnname;
    private String enname;

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setCnname(String cnname) {
        this.cnname = cnname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public int getCity_id() {
        return city_id;
    }

    public String getCnname() {
        return cnname;
    }

    public String getEnname() {
        return enname;
    }
}
