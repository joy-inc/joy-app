package com.joy.app.adapter.hotel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.joy.app.R;
import com.joy.app.bean.hotel.EntryEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 酒店搜索联想    <br>
 */
public class AutoCompleteAdapter extends ExRvAdapter<AutoCompleteAdapter.ViewHolder, EntryEntity> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(parent, R.layout.item_search_hotel_autocomplete));
    }

    public class ViewHolder extends ExRvViewHolder<EntryEntity>{
        @BindView(R.id.tv_cnname)
        TextView tvCnname;
        @BindView(R.id.tv_enname)
        TextView tvEnname;
        @BindView(R.id.vline)
        View vline;

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
        protected void invalidateItemView(int position, EntryEntity entryEntity) {
            tvCnname.setText(entryEntity.getCnname());
            tvEnname.setText(entryEntity.getEnname());
        }
    }
}
