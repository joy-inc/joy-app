package com.joy.app.eventbus;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class DeleteEvent {
    public static final int DELETE_ORDER = 1;
    public static final int DELETE_FOLDER = 2;

    int delete = 0;

    public DeleteEvent(int delete) {
        this.delete = delete;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
