package com.joy.app.bean.poi;

import com.android.library.utils.TextUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * 商品详情
 * Created by xiaoyu.chen on 15/11/24.
 * Modify by Daisw, support json2map 15/12/7.
 */
public class Product {

    private ArrayList<ProductLevels> levels;
    private Map<String, Item> items;

    public ArrayList<ProductLevels> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<ProductLevels> levels) {
        this.levels = levels;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }

    public static class Item {

        /**
         * item_id : 111
         * price : 76.99
         */

        private String item_id = TextUtil.TEXT_EMPTY;
        private String price = TextUtil.TEXT_EMPTY;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "item_id='" + item_id + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }
}