package com.joy.app.adapter.sample;

import android.view.View;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.sample.HotCityItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/7/10.
 */
public class CityLvAdapter extends ExAdapter<HotCityItem> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    public class ViewHolder extends ExViewHolderBase {

        @BindView(R.id.sdvPhoto) FrescoIv sdvPhoto;
        @BindView(R.id.tvName)   TextView tvName;

        @Override
        public int getConvertViewRid() {

            return R.layout.t_item_listview;
        }

        @Override
        public void initConvertView(View convertView) {

            ButterKnife.bind(this, convertView);
            convertView.setOnClickListener(v -> callbackOnItemViewClickListener(mPosition, v));
        }

        @Override
        public void invalidateConvertView() {

            HotCityItem data = getItem(mPosition);
            sdvPhoto.setImageURI(data.getPhoto());
            tvName.setText(data.getCnname());
        }
    }
}
