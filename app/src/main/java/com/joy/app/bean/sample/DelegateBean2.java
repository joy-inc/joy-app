package com.joy.app.bean.sample;

import com.android.library.view.recyclerview.DisplayableItem;

/**
 * Created by Daisw on 16/8/6.
 */

public class DelegateBean2 implements DisplayableItem {

    private int type;
    private String name;

    public DelegateBean2(String name) {
        this.name = name;
    }

    public DelegateBean2(int type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
