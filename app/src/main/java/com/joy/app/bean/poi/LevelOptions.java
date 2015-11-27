package com.joy.app.bean.poi;

import com.android.library.utils.TextUtil;

/**
 * 可选条件数据bean
 * <p/>
 * Created by xiaoyu.chen on 15/11/24.
 */
public class LevelOptions {

    private String option_id = TextUtil.TEXT_EMPTY;
    private String content = TextUtil.TEXT_EMPTY;
    private String describe = TextUtil.TEXT_EMPTY;

    private String localPrice = TextUtil.TEXT_EMPTY;
    private String localCount = TextUtil.TEXT_EMPTY;

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getLocalPrice() {
        return localPrice;
    }

    public void setLocalPrice(String localPrice) {
        this.localPrice = localPrice;
    }

    public String getLocalCount() {
        return localCount;
    }

    public void setLocalCount(String localCount) {
        this.localCount = localCount;
    }
}
