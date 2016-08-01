package com.joy.app.adapter.order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 不用了，    <br>
 */
public class OrderDetailAdapter extends ExRvAdapter <OrderDetailAdapter.ViewHolder,OrderDetail>{

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_order_view));
    }

    public class ViewHolder extends ExRvViewHolder<OrderDetail> {

        @BindView(R.id.jtv_title)
        TextView Title;//状态

        @BindView(R.id.jtv_infor)
        TextView Infor;//状态详情

        @BindView(R.id.sdv_order_photo)
        FrescoIv photo;//图片

        @BindView(R.id.jtv_order_title)
        TextView orderTitle;//订单名称

        @BindView(R.id.jtv_order_id)
        TextView orderId;//订单编号

        @BindView(R.id.jtv_order_item)
        TextView orderItem;//项目

        @BindView(R.id.jtv_order_count)
        TextView orderCount;//数量

        @BindView(R.id.jtv_order_name)
        TextView orderName;//姓名

        @BindView(R.id.jtv_order_phone)
        TextView orderPhone;//电话

        @BindView(R.id.jtv_order_email)
        TextView orderEmail;//Email

        @BindView(R.id.jtv_order_total)
        TextView orderTotal;//总额

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, OrderDetail orderDetail) {

            Title.setText(orderDetail.getOrderTitle());
            Infor.setText(orderDetail.getOrderInfor());
//            photo.setImageURI(URI.create(orderDetail.get));
            orderName.setText(orderDetail.getContact_name());
            orderId.setText(orderDetail.getOrder_id());
            orderTitle.setText(orderDetail.getProduct_title());
            orderItem.setText(orderDetail.getSelected_item());
            orderEmail.setText(orderDetail.getContact_email());
            orderPhone.setText(orderDetail.getContact_phone());
            orderTotal.setText(orderDetail.getTotal_price_Str());
            orderCount.setText(orderDetail.getItem_count());
        }
    }
}