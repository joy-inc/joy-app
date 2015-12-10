package com.joy.app.adapter.order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.poi.OrderDetail;

import butterknife.Bind;
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

        @Bind(R.id.jtv_title)
        TextView Title;//状态

        @Bind(R.id.jtv_infor)
        TextView Infor;//状态详情

        @Bind(R.id.sdv_order_photo)
        SimpleDraweeView photo;//图片

        @Bind(R.id.jtv_order_title)
        TextView orderTitle;//订单名称

        @Bind(R.id.jtv_order_id)
        TextView orderId;//订单编号

        @Bind(R.id.jtv_order_item)
        TextView orderItem;//项目

        @Bind(R.id.jtv_order_count)
        TextView orderCount;//数量

        @Bind(R.id.jtv_order_name)
        TextView orderName;//姓名

        @Bind(R.id.jtv_order_phone)
        TextView orderPhone;//电话

        @Bind(R.id.jtv_order_email)
        TextView orderEmail;//Email

        @Bind(R.id.jtv_order_total)
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
            orderCount.setText(orderDetail.getFormatCountStr());
        }
    }
}