package com.joy.app.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.MainOrder;
import com.joy.app.utils.JTextSpanUtil;

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

        @Bind(R.id.sdvPhoto)
        SimpleDraweeView sdvPhoto;

        @Bind(R.id.tvTitle)
        TextView tvTitle;

        @Bind(R.id.tvType)
        TextView tvType;

        @Bind(R.id.tvDepartureDate)
        TextView tvDepartureDate;

        @Bind(R.id.tvCount)
        TextView tvCount;

        @Bind(R.id.rvStatus0Div)
        RelativeLayout rvStatus0Div;

        @Bind(R.id.rvStatus1Div)
        RelativeLayout rvStatus1Div;

        @Bind(R.id.rvStatus2Div)
        RelativeLayout rvStatus2Div;

        @Bind(R.id.rvStatus3Div)
        RelativeLayout rvStatus3Div;

        @Bind(R.id.tvTotalPrice)
        TextView tvTotalPrice;

        @Bind(R.id.acbPay)
        TextView acbPay;

        @Bind(R.id.acbCommenton)
        TextView acbCommenton;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
            acbPay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), acbPay);
                }
            });
            acbCommenton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), acbCommenton);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, MainOrder mainOrder) {

            if (mainOrder != null) {

                sdvPhoto.setImageURI(Uri.parse(mainOrder.getProduct_photo()));
                tvTitle.setText(mainOrder.getProduct_title());
                tvType.setText(mainOrder.getSelected_item());
                tvDepartureDate.setText(mainOrder.getTravel_date());
                tvCount.setText(getString(R.string.fmt_unit_ge, mainOrder.getCount()));
                tvTotalPrice.setText(JTextSpanUtil.getFormatUnitStr(getString(R.string.fmt_unit, mainOrder.getTotal_price())));

                if ("0".equals(mainOrder.getOrder_status())) {

                    showView(rvStatus0Div);
                    hideView(rvStatus1Div);
                    hideView(rvStatus2Div);
                    hideView(rvStatus3Div);

                } else if ("1".equals(mainOrder.getOrder_status())) {

                    hideView(rvStatus0Div);
                    showView(rvStatus1Div);
                    hideView(rvStatus2Div);
                    hideView(rvStatus3Div);

                } else if ("2".equals(mainOrder.getOrder_status())) {

                    hideView(rvStatus0Div);
                    hideView(rvStatus1Div);
                    showView(rvStatus2Div);
                    hideView(rvStatus3Div);

                } else if ("3".equals(mainOrder.getOrder_status())) {

                    hideView(rvStatus0Div);
                    hideView(rvStatus1Div);
                    hideView(rvStatus2Div);
                    showView(rvStatus3Div);
                } else {

                    hideView(rvStatus0Div);
                    hideView(rvStatus1Div);
                    hideView(rvStatus2Div);
                    hideView(rvStatus3Div);
                }
            }
        }
    }
}
