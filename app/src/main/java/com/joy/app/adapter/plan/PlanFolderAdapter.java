package com.joy.app.adapter.plan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(parent,R.layout.item_dialog_forder_list));
    }


    class ViewHolder extends ExRvViewHolder<PlanFolder>{
        @Bind(R.id.jtv_title)
        JTextView jtvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callbackOnItemViewClickListener(getLayoutPosition(),v);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, PlanFolder planFolder) {
            jtvTitle.setText(planFolder.getFolder_name());
        }
    }
}
