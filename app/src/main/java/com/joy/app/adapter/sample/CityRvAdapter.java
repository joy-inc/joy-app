package com.joy.app.adapter.sample;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.sample.HotCityItem;
import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;

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

        @Bind(R.id.sdvPhoto) SimpleDraweeView sdvPhoto;
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

            sdvPhoto.setImageURI(Uri.parse(hotCityItem.getPhoto()));
            tvName.setText(hotCityItem.getCnname() + "\n" + hotCityItem.getEnname());
        }
    }
}