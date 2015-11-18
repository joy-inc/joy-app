package com.joy.app.bean.plan;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description 行程规划文件夹    <br>
 */
public class PlanFolder implements Serializable{
    String id,folde_rname;
    int children_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolde_rname() {
        return folde_rname;
    }

    public void setFolde_rname(String folde_rname) {
        this.folde_rname = folde_rname;
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
