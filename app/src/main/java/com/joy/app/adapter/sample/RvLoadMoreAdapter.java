package com.joy.app.adapter.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.bean.sample.Special;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/12/2.
 */
public class RvLoadMoreAdapter extends ExRvAdapter<RvLoadMoreAdapter.ViewHolder, Special> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.t_item_listview));
    }

    public class ViewHolder extends ExRvViewHolder<Special> {

        @Bind(R.id.sdvPhoto) FrescoImageView sdvPhoto;
        @Bind(R.id.tvName)   TextView tvName;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void invalidateItemView(int position, Special special) {

            sdvPhoto.setImageURI(special.getPhoto());
            tvName.setText(special.getTitle());
        }
    }
}