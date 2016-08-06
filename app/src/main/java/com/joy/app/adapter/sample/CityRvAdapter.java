package com.joy.app.adapter.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.TextUtil;
import com.android.library.view.fresco.FrescoIv;
import com.joy.app.R;
import com.joy.app.bean.sample.HotCityItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KEVIN.DAI on 15/11/10.
 */
public class CityRvAdapter extends ExRvAdapter<CityRvAdapter.ViewHolder, HotCityItem> {

    // 更改封图的分辨率
    @Override
    public void setData(List<HotCityItem> data) {

        if (CollectionUtil.isNotEmpty(data)) {

            String photo;
            for (HotCityItem item : data) {

                photo = item.getPhoto();
                if (TextUtil.isNotEmpty(photo))
                    item.setPhoto(photo.substring(0, photo.lastIndexOf("/") + 1) + "w800");
            }
        }
        super.setData(data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.t_item_card_rv));
    }

    public class ViewHolder extends ExRvViewHolder<HotCityItem> {

        @BindView(R.id.sdvPhoto)    FrescoIv    sdvPhoto;
        @BindView(R.id.tvName)      TextView    tvName;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName.setOnClickListener(v -> callbackOnItemViewClickListener(getLayoutPosition(), itemView));
        }

        @Override
        public void invalidateItemView(int position, HotCityItem hotCityItem) {

            sdvPhoto.setImageURI(hotCityItem.getPhoto());
            tvName.setText(hotCityItem.getCnname() + "\n" + hotCityItem.getEnname());
        }
    }
}