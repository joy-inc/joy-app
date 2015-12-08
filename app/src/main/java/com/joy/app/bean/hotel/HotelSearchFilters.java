package com.joy.app.bean.hotel;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 筛选条件    <br>
 */
public class HotelSearchFilters {
    //价格区间
    private List<FilterItems> prices;
    //星级
    private List<FilterItems> stars;
    //设施
    private List<FilterItems> facilities;
    //排序
    private List<FilterItems> orderby;

    public List<FilterItems> getPrices() {
        return prices;
    }

    public void setPrices(List<FilterItems> prices) {
        this.prices = prices;
    }

    public List<FilterItems> getStars() {
        return stars;
    }

    public void setStars(List<FilterItems> stars) {
        this.stars = stars;
    }

    public List<FilterItems> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<FilterItems> facilities) {
        this.facilities = facilities;
    }

    public List<FilterItems> getOrderby() {
        return orderby;
    }

    public void setOrderby(List<FilterItems> orderby) {
        this.orderby = orderby;
    }

}

