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
import com.joy.app.bean.city.CityFun;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/12/5.
 */
public class CityFunAdapter extends ExRvAdapter<CityFunAdapter.ViewHolder, CityFun.ListEntity> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_city_fun));
    }

    public class ViewHolder extends ExRvViewHolder<CityFun.ListEntity> {

        @Bind(R.id.sdvPhoto)          SimpleDraweeView sdvPhoto;
        @Bind(R.id.jtvName)           JTextView        jtvName;
        @Bind(R.id.jtvTitle)          JTextView        jtvTitle;
        @Bind(R.id.jtvRecommendCount) JTextView        jtvRecommendCount;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, CityFun.ListEntity entity) {

            sdvPhoto.setImageURI(Uri.parse(entity.getPic_url()));
            jtvName.setText(entity.getTopic_name() + "\n" + entity.getEn_name());
            jtvTitle.setText(entity.getRecommend());
        }
    }
}