package com.joy.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joy.app.JoyApplication;
import com.joy.app.R;
import com.joy.app.bean.MainOrder;
import com.joy.library.adapter.frame.ExRvAdapter;
import com.joy.library.adapter.frame.ExRvViewHolder;
import com.joy.library.utils.ViewUtil;

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MainOrder data = getItem(position);

        if (data != null) {

            holder.tvId.setText(JoyApplication.getContext().getString(R.string.order_id, data.getOrder_id()));
            holder.tvTitle.setText(JoyApplication.getContext().getString(R.string.order_title, data.getProduct_title()));
            holder.tvType.setText(JoyApplication.getContext().getString(R.string.product_type, data.getProduct_type()));
            holder.tvCount.setText(JoyApplication.getContext().getString(R.string.out_num, data.getCount()));
            holder.tvTotalPrice.setText(data.getTotal_price());

            if ("0".equals(data.getStatus())) {
                ViewUtil.showView(holder.tvStatus);
                holder.tvStatus.setText(R.string.pay);
            } else {
                ViewUtil.hideView(holder.tvStatus);
            }
        }
    }

    public class ViewHolder extends ExRvViewHolder {

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
    }
}
