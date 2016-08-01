package com.joy.app.adapter.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.bean.sample.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KEVIN.DAI on 15/11/12.
 * Modified by KEVIN.DAI on 16/7/6.
 */
public class CityDetailRvAdapter extends ExRvAdapter<CityDetailRvAdapter.ViewHolder, Trip> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.t_item_card_rv_detail));
    }

    static class ViewHolder extends ExRvViewHolder<Trip> {

        @BindView(R.id.sdvPhoto)
        FrescoIv sdvPhoto;

        @BindView(R.id.sdvAvatar)
        FrescoIv sdvAvatar;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvName)
        TextView tvName;

        ViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.cvLayout)
        void onItemClick() {

            if (BuildConfig.DEBUG)
                showToast("onClick--> position = " + getPosition());
        }

        @Override
        protected void invalidateItemView(int position, Trip trip) {

            sdvPhoto.setImageURI(trip.getPhoto());
            tvTitle.setText(trip.getTitle());
            sdvAvatar.setImageURI(trip.getAvatar());
            tvName.setText(trip.getUsername());
        }
    }
}
