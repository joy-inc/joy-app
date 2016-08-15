package com.joy.app.adapter.sample;

import android.view.View;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.sample.Special;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/11/23.
 */
public class LvLoadMoreAdapter extends ExAdapter<Special> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    public class ViewHolder extends ExViewHolderBase {

        @BindView(R.id.sdvPhoto)    FrescoIv    sdvPhoto;
        @BindView(R.id.tvName)      TextView    tvName;

        @Override
        public int getConvertViewRid() {

            return R.layout.t_item_listview;
        }

        @Override
        public void initConvertView(View convertView) {

            ButterKnife.bind(this, convertView);
            convertView.setOnClickListener(v -> callbackOnItemClickListener(mPosition, v));
        }

        @Override
        public void invalidateConvertView() {

            Special data = getItem(mPosition);
            sdvPhoto.setImageURI(data.getPhoto());
            tvName.setText(data.getTitle());
        }
    }
}
