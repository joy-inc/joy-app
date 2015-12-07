package com.joy.app.utils.plan;

import android.os.AsyncTask;

import java.nio.channels.AsynchronousCloseException;

/**
 * @author litong  <br>
 * @Description dialog回调    <br>
 */
public interface FolderRequestListener {
    enum dialog_category {
        create,delete,add,modify
    }
    public abstract void onSuccess (dialog_category category,Object obj);
    public abstract void onfaild (dialog_category category,String msg);
}
