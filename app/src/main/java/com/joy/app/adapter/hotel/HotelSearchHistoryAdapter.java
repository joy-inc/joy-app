package com.joy.app.adapter.hotel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.joy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 酒店搜索历史    <br>
 */
public class HotelSearchHistoryAdapter extends ExRvAdapter<HotelSearchHistoryAdapter.ViewHolder, String> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         return new ViewHolder(inflate(parent, R.layout.item_hotel_search_history));
    }

    class ViewHolder extends ExRvViewHolder<String> {
        @BindView(R.id.jtv_history_clear)
        JTextView jtvHistoryClear;
        @BindView(R.id.ll_history)
        LinearLayout llHistory;
        @BindView(R.id.jtv_title)
        JTextView jtvTitle;

        @Override
        public void invalidateItemView(int position, String s) {
            if (position == 0 ){
                ViewUtil.showView(llHistory);
            }else{
                ViewUtil.goneView(llHistory);
            }
            jtvTitle.setText(s);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            jtvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbackOnItemViewClickListener(getLayoutPosition(), v);
                }
            });
            jtvHistoryClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbackOnItemViewClickListener(getLayoutPosition(),null);
                }
            });
        }
    }
}
