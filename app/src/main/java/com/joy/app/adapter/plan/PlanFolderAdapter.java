package com.joy.app.adapter.plan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.plan.PlanFolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 添加poi dialog    <br>
 */
public class PlanFolderAdapter extends ExRvAdapter<PlanFolderAdapter.ViewHolder, PlanFolder> {
    String poiId;
    int select, normal;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        select = parent.getContext().getResources().getColor(R.color.pink_dark);
        normal = parent.getContext().getResources().getColor(R.color.black_trans70);
        return new ViewHolder(inflate(parent, R.layout.item_dialog_forder_list));

    }

    public void setName(String name) {
        this.poiId = name;
    }

    class ViewHolder extends ExRvViewHolder<PlanFolder> {
        @Bind(R.id.jtv_title)
        JTextView jtvTitle;
        @Bind(R.id.iv_select)
        ImageView ivSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbackOnItemViewClickListener(getLayoutPosition(), v);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, PlanFolder planFolder) {
            if (poiId.equals(planFolder.getFolder_id())) {
                jtvTitle.setTextColor(select);
                ViewUtil.showView(ivSelect);
            } else {
                jtvTitle.setTextColor(normal);
                ViewUtil.hideView(ivSelect);
            }
            jtvTitle.setText(planFolder.getFolder_name());
        }
    }
}
