package com.joy.app.adapter.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.sample.Special;

import butterknife.BindView;
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

        @BindView(R.id.sdvPhoto)    FrescoIv    sdvPhoto;
        @BindView(R.id.tvName)      TextView    tvName;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void invalidateItemView(int position, Special special) {

            sdvPhoto.setImageURI(special.getPhoto());
            tvName.setText(special.getTitle());
        }
    }
}