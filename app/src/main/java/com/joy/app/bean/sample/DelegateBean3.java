package com.joy.app.bean.sample;

import com.android.library.view.recyclerview.DisplayableItem;

/**
 * Created by Daisw on 16/8/6.
 */

public class DelegateBean3 implements DisplayableItem {

    private String name;

    public DelegateBean3(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
