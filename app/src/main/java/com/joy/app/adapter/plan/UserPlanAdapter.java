package com.joy.app.adapter.plan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.plan.PlanFolder;

import butterknife.BindView;
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

    public class ViewHolder extends ExRvViewHolder<PlanFolder> {

        @BindView(R.id.sdvBg)
        FrescoIv mPhoto;

        @BindView(R.id.jtv_number)
        TextView mCount;

        @BindView(R.id.jtv_Title)
        TextView mTtile;


        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemClickListener(getLayoutPosition(), itemView);
                }
            });
        }

        @Override
        public void invalidateItemView(int position, PlanFolder planFolder) {

            mPhoto.setImageURI(planFolder.getPic_url());
            mCount.setText(planFolder.getChildren_num() + "");
            mTtile.setText(planFolder.getFolder_name());
        }
    }
}