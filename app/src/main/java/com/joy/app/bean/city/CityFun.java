package com.joy.app.bean.city;

import java.util.List;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityFun {

    /**
     * description : 购物的总体描述
     * list : [{"topic_id":"QteM+pm2pOE=","topic_name":"韩国商场","en_name":"second name","pic_url":"http://xx.com/photo....","topic_url":"http://xx.com/..","recommend":"这是推荐语，很棒的商场，东西不要钱"},{"topic_id":"steM+pm2pOE=","topic_name":"首尔商场","en_name":"second name","pic_url":"http://xx.com/photo....","topic_url":"http://xx.com/..","recommend":"这是推荐语，很棒的商场，东西不要钱"}]
     */

    private String description;
    /**
     * topic_id : QteM+pm2pOE=
     * topic_name : 韩国商场
     * en_name : second name
     * pic_url : http://xx.com/photo....
     * topic_url : http://xx.com/..
     * recommend : 这是推荐语，很棒的商场，东西不要钱
     */

    private List<ListEntity> list;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public String getDescription() {
        return description;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private String topic_id;
        private String topic_name;
        private String en_name;
        private String pic_url;
        private String topic_url;
        private String recommend;

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public void setTopic_url(String topic_url) {
            this.topic_url = topic_url;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public String getEn_name() {
            return en_name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public String getTopic_url() {
            return topic_url;
        }

        public String getRecommend() {
            return recommend;
        }
    }
}
