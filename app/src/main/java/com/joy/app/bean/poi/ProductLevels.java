package com.joy.app.bean.poi;

import com.android.library.utils.TextUtil;

import java.util.ArrayList;

/**
 * 商品需要选择的列表
 * Created by xiaoyu.chen on 15/11/24.
 */
public class ProductLevels {

    private String level_id = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String type = TextUtil.TEXT_EMPTY;
    private ArrayList<LevelOptions> options;

    private String localContent = TextUtil.TEXT_EMPTY;
    private String localSelectId = TextUtil.TEXT_EMPTY;

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<LevelOptions> getOptions() {
        return options == null ? new ArrayList<LevelOptions>() : options;
    }

    public void setOptions(ArrayList<LevelOptions> options) {
        this.options = options;
    }

    public String getLocalContent() {
        return localContent;
    }

    public void setLocalContent(String localContent) {
        this.localContent = localContent;
    }

    public String getLocalSelectId() {
        return localSelectId;
    }

    public void setLocalSelectId(String localSelectId) {
        this.localSelectId = localSelectId;
    }
}
