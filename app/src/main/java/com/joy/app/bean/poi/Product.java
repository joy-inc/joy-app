package com.joy.app.bean.poi;

import java.util.ArrayList;

/**
 * 商品详情
 * Created by xiaoyu.chen on 15/11/24.
 */
public class Product {

    private ArrayList<ProductLevels> levels;
    private ProductItems items;

    public ArrayList<ProductLevels> getLevels() {

        return levels;
    }

    public void setLevels(ArrayList<ProductLevels> levels) {

        this.levels = levels;
    }

    public ProductItems getItems() {

        return items;
    }

    public void setItems(ProductItems items) {

        this.items = items;
    }
}
