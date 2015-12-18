package com.joy.app.bean.hotel;

import com.android.library.utils.TextUtil;

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

    public String getFacilitiesIds(String str){
        String temp[] = str.split(",");
        StringBuilder builder = new StringBuilder();
        for (String s1 : temp){
            String id =  getFid(s1);
            if (TextUtil.isEmpty(id))continue;
            builder.append(",");
            builder.append(id);
        }
        if (builder.length()>1){

            return builder.substring(1);
        }else {
            return TextUtil.TEXT_EMPTY;
        }
    }

    public String getStarIds(String str){
        String temp[] = str.split(",");
        StringBuilder builder = new StringBuilder();
        for (String s1 : temp){
            String id =  getSid(s1);
            if (TextUtil.isEmpty(id))continue;
            builder.append(",");
            builder.append(id);
        }
        if(builder.length()>1){

            return builder.substring(1);
        }else return TextUtil.TEXT_EMPTY;

    }

    private String getFid(String name){
       for (FilterItems item:facilities){
           if (item.getName().equals(name))return String.valueOf(item.getValue());
       }
        return TextUtil.TEXT_EMPTY;
    }

    private String getSid(String name){
       for (FilterItems item:stars){
           if (item.getName().equals(name))return String.valueOf(item.getValue());
       }
        return TextUtil.TEXT_EMPTY;
    }


}

