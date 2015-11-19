package com.joy.app.bean.plan;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description 行程规划文件夹    <br>
 */
public class PlanFolder implements Serializable{
    String id,folder_name;
    int children_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public int getChildren_num() {
        return children_num;
    }

    public void setChildren_num(int children_num) {
        this.children_num = children_num;
    }

    //            "id": "JmwMFx+4+5o=",
//            "folder_name": "好玩的",
//            "children_num": 4,
}
