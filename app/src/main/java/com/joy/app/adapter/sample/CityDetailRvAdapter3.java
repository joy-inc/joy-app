package com.joy.app.adapter.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvMultipleAdapter;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.DimenCons;
import com.android.library.utils.LogMgr;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.bean.sample.CityDetail;
import com.joy.app.bean.sample.Trip;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KEVIN.DAI on 15/11/12.
 */
public class CityDetailRvAdapter3 extends ExRvMultipleAdapter {

    private CityDetail mCityDetail;

    public CityDetailRvAdapter3(Context context) {

        super(context);
        mHeaderCount = 1;
        mBottomCount = 1;
    }

    public void setData(CityDetail data) {

        mCityDetail = data;
    }

    @Override
    public int getContentItemCount() {

        return mCityDetail == null ? 0 : mCityDetail.getNew_trip() == null ? 0 : mCityDetail.getNew_trip().size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mCityDetail == null)
            return;

        if (holder instanceof ContentViewHolder) {

            ArrayList<Trip> trips = mCityDetail.getNew_trip();
            if (CollectionUtil.isEmpty(trips))
                return;

            int pos = position - mHeaderCount;
            ContentViewHolder vh = ((ContentViewHolder) holder);
            vh.sdvPhoto.setImageURI(trips.get(pos).getPhoto());
            vh.tvTitle.setText(trips.get(pos).getTitle());
            vh.sdvAvatar.setImageURI(trips.get(pos).getAvatar());
            vh.tvName.setText(trips.get(pos).getUsername());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {

        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.t_header_cover_photo3, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {

        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.t_item_card_rv_detail, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {

        View view = new View(parent.getContext());
        view.setMinimumHeight(DimenCons.DP_1_PX * 16);
        return new BottomViewHolder(view);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        HeaderViewHolder(View view) {

            super(view);
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.sdvPhoto)  FrescoImageView sdvPhoto;
        @Bind(R.id.sdvAvatar) FrescoImageView sdvAvatar;
        @Bind(R.id.tvTitle)   TextView tvTitle;
        @Bind(R.id.tvName)    TextView tvName;

        ContentViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tvName)
        void onItemClick() {

            if (LogMgr.isDebug())
                LogMgr.d("ContentViewHolder", "onClick--> position = " + getPosition());
        }
    }

    private class BottomViewHolder extends RecyclerView.ViewHolder {

        BottomViewHolder(View view) {

            super(view);
        }
    }
}