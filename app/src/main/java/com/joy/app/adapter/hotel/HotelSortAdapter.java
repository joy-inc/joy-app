package com.joy.app.adapter.hotel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.hotel.FilterItems;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daisw on 16/8/3.
 */

public class HotelSortAdapter extends ExRvAdapter<HotelSortAdapter.ViewHolder, FilterItems> {

    private int select;

    public void setSelect(int select) {

        this.select = select;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_hotel_sort));
    }

    class ViewHolder extends ExRvViewHolder<FilterItems> {

        @BindView(R.id.jtv_name)    JTextView   jtvName;
        @BindView(R.id.iv_select)   ImageView   ivSelect;

        public ViewHolder(View v) {

            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(v1 -> {
                select = getLayoutPosition();
                callbackOnItemViewClickListener(select, v1);
            });
        }

        @Override
        protected void invalidateItemView(int position, FilterItems filterItems) {

            jtvName.setText(filterItems.getName());
            if (select == filterItems.getValue()) {

                showView(ivSelect);
            } else {

                hideView(ivSelect);
            }
        }
    }
}
