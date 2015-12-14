package com.joy.app.bean.hotel;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 酒店    <br>
 */
public class HotelList {


    /**
     * total : 293
     * city_photo : http://static.qyer.com/city/map/area_1.jpg
     * area : {"id":10474,"name":"铜锣湾","intro":"这里显示的铜锣湾的介绍信息","prices":[{"name":"经济型","price":364},{"name":"3星级","price":500},{"name":"4星级","price":600},{"name":"5星级","price":899}]}
     * hotel : [{"id":342124,"cnname":"布达佩斯大饭店","enname":"Budapest Hotel","ranking":9.2,"star":"五星级","area_name":"尖沙咀","price":939,"lat":83.212,"lon":112.5423,"distance":"0.4千米","is_recommend":true,"photo":"http://static.qyer.com/hotel/2342342/img/222.jpg","link":"http://www.booking.com/hotel/fr/belambra-city-magendie.html?aid=378575"},{"id":342124,"cnname":"布达佩斯大饭店","enname":"Budapest Hotel","ranking":9.2,"star":"五星级","area_name":"尖沙咀","price":939,"lat":83.212,"lon":112.5423,"distance":"0.4千米","is_recommend":true,"photo":"http://static.qyer.com/hotel/2342342/img/222.jpg","link":"http://www.booking.com/hotel/fr/belambra-city-magendie.html?aid=378575"},{"id":342124,"cnname":"布达佩斯大饭店","enname":"Budapest Hotel","ranking":9.2,"star":"五星级","area_name":"尖沙咀","price":939,"lat":83.212,"lon":112.5423,"distance":"0.4千米","is_recommend":true,"photo":"http://static.qyer.com/hotel/2342342/img/222.jpg","link":"http://www.booking.com/hotel/fr/belambra-city-magendie.html?aid=378575"},{"id":342124,"cnname":"布达佩斯大饭店","enname":"Budapest Hotel","ranking":9.2,"star":"五星级","area_name":"尖沙咀","price":939,"lat":83.212,"lon":112.5423,"distance":"0.4千米","is_recommend":true,"photo":"http://static.qyer.com/hotel/2342342/img/222.jpg","link":"http://www.booking.com/hotel/fr/belambra-city-magendie.html?aid=378575"}]
     */

    private int total,has_area;
    private String city_photo;
    private String city_name;
    /**
     * id : 10474
     * name : 铜锣湾
     * intro : 这里显示的铜锣湾的介绍信息
     * prices : [{"name":"经济型","price":364},{"name":"3星级","price":500},{"name":"4星级","price":600},{"name":"5星级","price":899}]
     */

    private AreaEntity area;
    /**
     * id : 342124
     * cnname : 布达佩斯大饭店
     * enname : Budapest Hotel
     * ranking : 9.2
     * star : 五星级
     * area_name : 尖沙咀
     * price : 939
     * lat : 83.212
     * lon : 112.5423
     * distance : 0.4千米
     * is_recommend : true
     * photo : http://static.qyer.com/hotel/2342342/img/222.jpg
     * link : http://www.booking.com/hotel/fr/belambra-city-magendie.html?aid=378575
     */

    private List<HotelEntity> hotel;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCity_photo(String city_photo) {
        this.city_photo = city_photo;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public void setHotel(List<HotelEntity> hotel) {
        this.hotel = hotel;
    }

    public int getTotal() {
        return total;
    }

    public int getHas_area() {
        return has_area;
    }

    public void setHas_area(int has_area) {
        this.has_area = has_area;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_photo() {
        return city_photo;
    }

    public AreaEntity getArea() {
        return area;
    }

    public List<HotelEntity> getHotel() {
        return hotel;
    }

    public static class AreaEntity {
        private int id;
        private String name;
        private String intro;
        /**
         * name : 经济型
         * price : 364
         */

        private List<PricesEntity> prices;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setPrices(List<PricesEntity> prices) {
            this.prices = prices;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getIntro() {
            return intro;
        }

        public List<PricesEntity> getPrices() {
            return prices;
        }

        public static class PricesEntity {
            private String name;
            private int price;

            public void setName(String name) {
                this.name = name;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public int getPrice() {
                return price;
            }
        }
    }

}
