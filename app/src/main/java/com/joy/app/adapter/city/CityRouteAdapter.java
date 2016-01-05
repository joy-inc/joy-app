package com.joy.app.adapter.city;

import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.FrescoImageView;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.city.CityRoute;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityRouteAdapter extends ExRvAdapter<CityRouteAdapter.ViewHolder, CityRoute> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_city_route));
    }

    public class ViewHolder extends ExRvViewHolder<CityRoute> {

        @Bind(R.id.sdvPhoto) FrescoImageView  sdvPhoto;
        @Bind(R.id.jtvDays)  JTextView        jtvDays;
        @Bind(R.id.jtvTitle) JTextView        jtvTitle;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, CityRoute cityRoute) {

            sdvPhoto.setImageURI(cityRoute.getPic_url());
            jtvDays.setText(cityRoute.getRoute_day());
            jtvTitle.setText(cityRoute.getRoute_name());
        }
    }
}