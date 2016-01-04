package com.joy.app.adapter.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.bean.sample.HotCityItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class CityRvAdapter extends ExRvAdapter<CityRvAdapter.ViewHolder, HotCityItem> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.t_item_card_rv));
    }

    public class ViewHolder extends ExRvViewHolder<HotCityItem> {

        @Bind(R.id.sdvPhoto) FrescoImageView sdvPhoto;
        @Bind(R.id.tvName)   TextView         tvName;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, HotCityItem hotCityItem) {

            sdvPhoto.setImageURI(hotCityItem.getPhoto());
            tvName.setText(hotCityItem.getCnname() + "\n" + hotCityItem.getEnname());
        }
    }
}