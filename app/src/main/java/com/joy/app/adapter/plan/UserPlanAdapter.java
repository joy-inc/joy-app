package com.joy.app.adapter.plan;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.plan.PlanFolder;

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

            holder.initData(data);
        }
    }

    public class ViewHolder extends ExRvViewHolder {

        @Bind(R.id.sdvBg)
        SimpleDraweeView mPhoto;

        @Bind(R.id.jtv_number)
        TextView mCount;

        @Bind(R.id.jtv_Title)
        TextView mTtile;


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

        public void initData(PlanFolder folder) {
            mPhoto.setImageURI(Uri.parse(folder.getPic_url()));
            mCount.setText(folder.getChildren_num() + "");
            mTtile.setText(folder.getFolder_name());
        }


    }
}