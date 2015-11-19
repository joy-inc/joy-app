package com.joy.app.adapter.plan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.MainOrder;
import com.joy.app.bean.plan.PlanFolder;
import com.joy.library.adapter.frame.ExAdapter;
import com.joy.library.adapter.frame.ExRvAdapter;
import com.joy.library.adapter.frame.ExRvViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class UserPlanAdapter extends ExRvAdapter<UserPlanAdapter.ViewHolder,PlanFolder> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_main_order_adapter));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlanFolder data = getItem(position);

        if (data != null) {

//            holder.tvId.setText(data.getOrder_id());
//            holder.tvTitle.setText(data.getProduct_title());
//            holder.tvType.setText(data.getProduct_type());
//            holder.tvCount.setText(data.getCount());
//            holder.tvTotalPrice.setText(data.getTotal_price());
//            holder.tvStatus.setText(data.getStatus());
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
            tvTitle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
        }
    }

}
