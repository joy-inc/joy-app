package com.joy.app.adapter.city;

import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.utils.TextUtil;
import com.android.library.widget.FrescoImageView;
import com.android.library.widget.JTextView;
import com.joy.app.R;
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

        @Bind(R.id.sdvPhoto)          FrescoImageView  sdvPhoto;
        @Bind(R.id.jtvCnName)         JTextView        jtvCnName;
        @Bind(R.id.jtvEnName)         JTextView        jtvEnName;
        @Bind(R.id.jtvTitle)          JTextView        jtvTitle;
        @Bind(R.id.jtvRecNum)         JTextView        jtvRecNum;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, CityFun.ListEntity entity) {

            sdvPhoto.setImageURI(entity.getPic_url());
            jtvCnName.setText(entity.getTopic_name());
            jtvEnName.setText(entity.getEn_name());
            jtvTitle.setText(entity.getRecommend());
            if (TextUtil.isEmpty(entity.getRecom_num())) {

                goneView(jtvRecNum);
            } else {

                jtvRecNum.setText(getString(R.string.fmt_recommend_num, entity.getRecom_num()));
                showView(jtvRecNum);
            }
        }
    }
}