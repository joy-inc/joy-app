package com.joy.app.eventbus;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class FolderEvent {
    public static final int DELETE_FOLDER = 1;
    public static final int ADD_POI = 2;
    public static final int CREATE_FOLDER = 3;
    public static final int DELETE_POI = 4;

    int delete = 0;

    public FolderEvent(int delete) {
        this.delete = delete;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
