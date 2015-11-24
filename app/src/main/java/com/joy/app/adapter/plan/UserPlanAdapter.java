package com.joy.app.adapter.plan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.plan.PlanFolder;
import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanAdapter extends ExRvAdapter<UserPlanAdapter.ViewHolder, PlanFolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_plan_folder));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlanFolder data = getItem(position);

        if (data != null) {

            holder.tvLtitle.setText(data.getFolder_name());
        }
    }

    public class ViewHolder extends ExRvViewHolder {

        @Bind(R.id.jtvTitle)
        TextView tvLtitle;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
        }
    }
}