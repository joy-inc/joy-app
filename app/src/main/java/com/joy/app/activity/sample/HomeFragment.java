package com.joy.app.activity.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.ui.fragment.BaseHttpRvFragment2;
import com.android.library.view.recyclerview.AdapterDelegateCombine;
import com.android.library.view.recyclerview.AdapterDelegateManager;
import com.android.library.view.recyclerview.DisplayableItem;
import com.joy.app.adapter.sample.DelegateAdapter1;
import com.joy.app.adapter.sample.DelegateAdapter2;
import com.joy.app.adapter.sample.DelegateAdapter3;
import com.joy.app.adapter.sample.DelegateTypeAdapter1;
import com.joy.app.adapter.sample.DelegateTypeAdapter2;
import com.joy.app.adapter.sample.DelegateTypeAdapter3;
import com.joy.app.bean.sample.DelegateBean1;
import com.joy.app.bean.sample.DelegateBean2;
import com.joy.app.bean.sample.DelegateBean3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daisw on 16/8/5.
 */

public class HomeFragment extends BaseHttpRvFragment2 {

    public static HomeFragment instantiate(Context context) {

        return (HomeFragment) Fragment.instantiate(context, HomeFragment.class.getName(), new Bundle());
    }

    @Override
    protected void initContentView() {

        setSwipeRefreshEnable(false);

        List<DisplayableItem> items = new ArrayList<>();
        items.addAll(Arrays.asList(
                new DelegateBean1(1, "DelegateBean1 with type"),
                new DelegateBean1(2, "DelegateBean2 with type"),
                new DelegateBean1(3, "DelegateBean3 with type"),
                new DelegateBean1(1, "DelegateBean1 with type"),
                new DelegateBean1(2, "DelegateBean2 with type"),
                new DelegateBean1(3, "DelegateBean3 with type"),
                new DelegateBean1(1, "DelegateBean1 with type"),
                new DelegateBean1(2, "DelegateBean2 with type"),
                new DelegateBean1(3, "DelegateBean3 with type"),

                new DelegateBean1("DelegateBean1 without type"),
                new DelegateBean1("DelegateBean1 without type"),
                new DelegateBean1("DelegateBean1 without type"),
                new DelegateBean2("DelegateBean2 without type"),
                new DelegateBean2("DelegateBean2 without type"),
                new DelegateBean2("DelegateBean2 without type"),
                new DelegateBean3("DelegateBean3 without type"),
                new DelegateBean3("DelegateBean3 without type"),
                new DelegateBean3("DelegateBean3 without type")));

        AdapterDelegateManager delegateManager = new AdapterDelegateManager();
        delegateManager.addDelegate(DelegateTypeAdapter1.TYPE_VALUE, new DelegateTypeAdapter1());
        delegateManager.addDelegate(DelegateTypeAdapter2.TYPE_VALUE, new DelegateTypeAdapter2());
        delegateManager.addDelegate(DelegateTypeAdapter3.TYPE_VALUE, new DelegateTypeAdapter3());

        delegateManager.addDelegate(new DelegateAdapter1());
        delegateManager.addDelegate(new DelegateAdapter2());
        delegateManager.addDelegate(new DelegateAdapter3());

        setAdapter(new AdapterDelegateCombine(items, delegateManager));
    }

    @Override
    protected void doOnRetry() {

    }
}
