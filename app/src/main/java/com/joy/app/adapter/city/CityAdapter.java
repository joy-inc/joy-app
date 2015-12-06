package com.joy.app.adapter.city;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.city.City;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityAdapter extends ExRvAdapter<CityAdapter.ViewHolder, City> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_city_route));
    }

    public class ViewHolder extends ExRvViewHolder<City> {

        @Bind(R.id.sdvPhoto) SimpleDraweeView sdvPhoto;
        @Bind(R.id.jtvDays)  JTextView        jtvDays;
        @Bind(R.id.jtvTitle) JTextView        jtvTitle;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, City city) {

            sdvPhoto.setImageURI(Uri.parse(city.getPic_url()));
            jtvDays.setText(10 + position + "");
            jtvTitle.setText("新西兰米尔福德峡湾一日游的很多行程之一");
        }
    }
}