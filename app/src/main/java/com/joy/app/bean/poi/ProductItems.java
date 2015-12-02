package com.joy.app.bean.poi;

import com.android.library.utils.TextUtil;

/**
 * 选择的商品金额
 * Created by xiaoyu.chen on 15/11/24.
 */
public class ProductItems {


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
}
