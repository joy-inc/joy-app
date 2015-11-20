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
import com.joy.library.utils.ViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 用户行程规划    <br>
 */
public class UserPlanAdapter extends ExRvAdapter<UserPlanAdapter.ViewHolder,PlanFolder> {

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (count == 0)return count;
        else{
            int num = count/2;
            return count%2>0 ? num+1 : num;
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_plan_folder));
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlanFolder data = getLeftItem(position);

        if (data != null) {

            holder.tvLtitle.setText(data.getFolder_name());
        }
        if (getRightItem(position)!= null){

            holder.tvRtitle.setText(getRightItem(position).getFolder_name());
            ViewUtil.showView(holder.Rcardview);
        }else{
            ViewUtil.hideView(holder.Rcardview);

        }
    }

    private PlanFolder getLeftItem(int position){
        int realPosition = position*2;
        return getItem(realPosition);
    }
    private PlanFolder getRightItem(int position){
        int realPosition = position*2+1;
        return getItem(realPosition);
    }

    public class ViewHolder extends ExRvViewHolder {

        @Bind(R.id.tv_title_left)
        TextView tvLtitle;

        @Bind(R.id.tv_title_right)
        TextView tvRtitle;

        @Bind(R.id.cv_right)
        View Rcardview;


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
