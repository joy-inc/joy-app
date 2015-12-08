package com.joy.app.bean.hotel;

import java.util.List;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class AutoComplete  {


    /**
     * keyword : mangu
     * entry : [{"city_id":55,"cnname":"曼谷黄家酒店","enname":"Bangkok Gold Hotel"},{"city_id":55,"cnname":"曼谷黄家酒店","enname":"Bangkok Gold Hotel"}]
     */

    private String keyword;
    /**
     * city_id : 55
     * cnname : 曼谷黄家酒店
     * enname : Bangkok Gold Hotel
     */

    private List<EntryEntity> entry;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setEntry(List<EntryEntity> entry) {
        this.entry = entry;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<EntryEntity> getEntry() {
        return entry;
    }


}
