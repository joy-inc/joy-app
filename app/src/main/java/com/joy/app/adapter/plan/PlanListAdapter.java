package com.joy.app.adapter.plan;

import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.utils.ViewUtil;
import com.android.library.view.fresco.FrescoIv;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.map.MapPoiDetail;
import com.joy.app.bean.plan.PlanItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 行程规划列表    <br>
 */
public class PlanListAdapter extends ExRvAdapter<PlanListAdapter.ViewHolder, PlanItem> {
    ArrayList<MapPoiDetail> content = new ArrayList<>();

    @Override
    public void setData(List<PlanItem> data) {
        super.setData(data);
        content = new ArrayList<>();
        for (PlanItem item : data){
            if (item.getMapPoiDetail() == null){
                continue;
            }
            content.add(item.getMapPoiDetail());
        }
    }


    public ArrayList<MapPoiDetail> getContent() {

        return content;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_plan_list));
    }

    public class ViewHolder extends ExRvViewHolder<PlanItem> {

        @BindView(R.id.sdv_poi_photo)
        FrescoIv sdvPoiPhoto;
        @BindView(R.id.jtv_cnname)
        JTextView jtvCnname;
        @BindView(R.id.jtv_enname)
        JTextView jtvEnname;
        @BindView(R.id.jtv_price)
        JTextView jtvPrice;
        @BindView(R.id.jtv_day)
        JTextView jtvDay;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callbackOnItemViewLongClickListener(getLayoutPosition(),itemView);
                    return true;
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, PlanItem planItem) {

            sdvPoiPhoto.setImageURI(planItem.getPic_url());
            jtvCnname.setText(planItem.getCn_name());
            jtvEnname.setText(planItem.getEn_name());
            if (planItem.isEmptyPrice()){
                ViewUtil.hideView(jtvPrice);
            }else{
                ViewUtil.showView(jtvPrice);
                jtvPrice.setText(planItem.getPrice());
            }

            if (planItem.hasBefore_day()) {
                jtvDay.setText(jtvDay.getContext().getString(R.string.plan_list_before_day, planItem.getBefore_day()));
                ViewUtil.showView(jtvDay);
            } else {
                ViewUtil.hideView(jtvDay);
            }

        }
    }

    //    class CopyThread extends Thread{
    //        @Override
    //        public void run() {
    //            super.run();
    //            content = MapUtil.getMapContent(getData());
    //        }
    //    }
}