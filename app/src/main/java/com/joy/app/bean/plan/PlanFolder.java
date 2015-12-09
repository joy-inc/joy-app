package com.joy.app.bean.plan;

import com.android.library.utils.TextUtil;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description 行程规划文件夹    <br>
 */
public class PlanFolder {
    String folder_id, folder_name, pic_url;
    int children_num;

    public String getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(String folder_id) {
        this.folder_id = TextUtil.filterNull(folder_id);
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = TextUtil.filterNull(folder_name);
    }

    public int getChildren_num() {
        return children_num;
    }

    public void setChildren_num(int children_num) {
        this.children_num = children_num;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = TextUtil.filterNull(pic_url);
    }

    //            "id": "JmwMFx+4+5o=",
    //            "folder_name": "好玩的",
    //            "children_num": 4,
}
