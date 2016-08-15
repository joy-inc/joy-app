package com.joy.app.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.MainOrder;
import com.joy.app.utils.JTextSpanUtil;

import butterknife.BindView;
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

        @BindView(R.id.sdvPhoto)
        FrescoIv sdvPhoto;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvType)
        TextView tvType;

        @BindView(R.id.tvDepartureDate)
        TextView tvDepartureDate;

        @BindView(R.id.tvCount)
        TextView tvCount;

        @BindView(R.id.rvStatus0Div)
        RelativeLayout rvStatus0Div;

        @BindView(R.id.rvStatus1Div)
        RelativeLayout rvStatus1Div;

        @BindView(R.id.rvStatus2Div)
        RelativeLayout rvStatus2Div;

        @BindView(R.id.rvStatus3Div)
        RelativeLayout rvStatus3Div;

        @BindView(R.id.tvTotalPrice)
        TextView tvTotalPrice;

        @BindView(R.id.acbPay)
        TextView acbPay;

        @BindView(R.id.acbCommenton)
        TextView acbCommenton;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemClickListener(getLayoutPosition(), itemView);
                }
            });
            acbPay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemClickListener(getLayoutPosition(), acbPay);
                }
            });
            acbCommenton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemClickListener(getLayoutPosition(), acbCommenton);
                }
            });
        }

        @Override
        public void invalidateItemView(int position, MainOrder mainOrder) {

            if (mainOrder != null) {

                sdvPhoto.setImageURI(mainOrder.getProduct_photo());
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
