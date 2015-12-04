package com.joy.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.joy.app.R;
import com.joy.app.bean.MainOrder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单列表adapter
 * <p/>
 * Created by xiaoyu.chen on 15/11/17.
 */
public class MainOrderRvAdapter extends ExRvAdapter<MainOrderRvAdapter.ViewHolder, MainOrder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_main_order_adapter));
    }

    public class ViewHolder extends ExRvViewHolder<MainOrder> {

        @Bind(R.id.tvId)
        TextView tvId;

        @Bind(R.id.tvTitle)
        TextView tvTitle;

        @Bind(R.id.tvType)
        TextView tvType;

        @Bind(R.id.tvCount)
        TextView tvCount;

        @Bind(R.id.tvTotalPrice)
        TextView tvTotalPrice;

        @Bind(R.id.tvStatus)
        TextView tvStatus;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
            tvStatus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, MainOrder mainOrder) {

            if (mainOrder != null) {

                tvId.setText(getString(R.string.order_id, mainOrder.getOrder_id()));
                tvTitle.setText(getString(R.string.order_title, mainOrder.getProduct_title()));
                tvType.setText(getString(R.string.product_type, mainOrder.getProduct_type()));
                tvCount.setText(getString(R.string.out_num, mainOrder.getCount()));
                tvTotalPrice.setText(mainOrder.getTotal_price());

                if ("0".equals(mainOrder.getStatus())) {

                    showView(tvStatus);
                    tvStatus.setText(R.string.pay);
                } else {

                    hideView(tvStatus);
                }
            }
        }
    }
}
