package com.joy.app.adapter.sample;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.recyclerview.AdapterDelegateImpl;
import com.android.library.view.recyclerview.DisplayableItem;
import com.joy.app.R;
import com.joy.app.bean.sample.DelegateBean1;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daisw on 16/8/6.
 */

public class DelegateAdapter1 extends AdapterDelegateImpl<DelegateBean1, DelegateAdapter1.ViewHolder> {

    @Override
    public boolean isForViewType(@NonNull DisplayableItem item, int position) {

        return item instanceof DelegateBean1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        return new ViewHolder(inflate(parent, R.layout.t_item_listview));
    }

    class ViewHolder extends ExRvViewHolder<DelegateBean1> {

        @BindView(R.id.tvName) TextView tvName;

        public ViewHolder(View v) {

            super(v);
            ButterKnife.bind(this, v);
        }

        @Override
        public void invalidateItemView(int position, DelegateBean1 xxxBean) {

            tvName.setText(xxxBean.getName());
        }
    }
}
