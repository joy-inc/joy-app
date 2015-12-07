package com.joy.app.bean.hotel;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 筛选条件    <br>
 */
public class HotelSearchFilters {
    //价格区间
    private List<Item> prices;
    //星级
    private List<Item> stars;
    //设施
    private List<Item> facilities;
    //排序
    private List<Item> orderby;

    public List<Item> getPrices() {
        return prices;
    }

    public void setPrices(List<Item> prices) {
        this.prices = prices;
    }

    public List<Item> getStars() {
        return stars;
    }

    public void setStars(List<Item> stars) {
        this.stars = stars;
    }

    public List<Item> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Item> facilities) {
        this.facilities = facilities;
    }

    public List<Item> getOrderby() {
        return orderby;
    }

    public void setOrderby(List<Item> orderby) {
        this.orderby = orderby;
    }

    class Item {
        String name;
        int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}

