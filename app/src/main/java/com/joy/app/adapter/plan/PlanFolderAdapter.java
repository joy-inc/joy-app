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
    String name;
    int select ,normal;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        select = parent.getContext().getResources().getColor(R.color.pink_dark);
        normal = parent.getContext().getResources().getColor(R.color.black_trans70);
        return new ViewHolder(inflate(parent,R.layout.item_dialog_forder_list));

    }

    public void setName(String name) {
        this.name = name;
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
            if (name.equals(planFolder.getFolder_name())){
                jtvTitle.setTextColor(select);
            }else{
                jtvTitle.setTextColor(normal);
            }
            jtvTitle.setText(planFolder.getFolder_name());
        }
    }
}
