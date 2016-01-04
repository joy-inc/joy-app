package com.joy.app.adapter.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvMultipleAdapter;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.DimenCons;
import com.android.library.utils.LogMgr;
import com.android.library.widget.FrescoImageView;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
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
public class CityDetailRvAdapter extends ExRvMultipleAdapter {

    private CityDetail mCityDetail;
    private int mPaletteColor = -1;
    private RecyclerView mAttachedView;

    public CityDetailRvAdapter(Context context) {

        super(context);
        mHeaderCount = 1;
        mBottomCount = 1;
    }

    public void setAttachedView(RecyclerView v) {

        mAttachedView = v;
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

        if (holder instanceof HeaderViewHolder) {

            final HeaderViewHolder vh = ((HeaderViewHolder) holder);
            vh.tvName.setText(mCityDetail.getChinesename() + "\n" + mCityDetail.getEnglishname());

            ArrayList<String> photos = mCityDetail.getPhotos();
            if (CollectionUtil.isEmpty(photos))
                return;

            if (mPaletteColor == -1) {

                Postprocessor processor = new BasePostprocessor() {

                    @Override
                    public CloseableReference<Bitmap> process(final Bitmap bitmap, PlatformBitmapFactory bitmapFactory) {

                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

                            @Override
                            public void onGenerated(Palette palette) {

                                if (palette != null) {

                                    mPaletteColor = palette.getMutedColor(0X00000000);
                                    mAttachedView.setBackgroundColor(mPaletteColor);
                                    vh.sdvPhoto.setImageBitmap(bitmap);
                                }
                            }
                        });
                        return null;
                    }

                    @Override
                    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {

//                        super.process(destBitmap, sourceBitmap);
                    }

                    @Override
                    public void process(Bitmap bitmap) {

//                        super.process(bitmap);
                    }
                };
                ImageRequest request = ImageRequestBuilder
                        .newBuilderWithSource(Uri.parse(photos.get(0)))
                        .setPostprocessor(processor)
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco
                        .newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(vh.sdvPhoto.getController())
                        .build();
                vh.sdvPhoto.setController(controller);
            } else {

                if (!(vh.sdvPhoto.getDrawable() instanceof BitmapDrawable))
                    vh.sdvPhoto.setImageURI(photos.get(0));
            }
            if (photos.size() > 1)
                vh.sdvSubPhoto1.setImageURI(photos.get(1));
            if (photos.size() > 2)
                vh.sdvSubPhoto2.setImageURI(photos.get(2));
            if (photos.size() > 3)
                vh.sdvSubPhoto3.setImageURI(photos.get(3));
            if (photos.size() > 4)
                vh.sdvSubPhoto4.setImageURI(photos.get(4));
        } else if (holder instanceof ContentViewHolder) {

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

        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.t_header_view_detail, parent, false));
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

        @Bind(R.id.sdvPhoto)
        FrescoImageView sdvPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.sdvSubPhoto1)
        FrescoImageView sdvSubPhoto1;
        @Bind(R.id.sdvSubPhoto2)
        FrescoImageView sdvSubPhoto2;
        @Bind(R.id.sdvSubPhoto3)
        FrescoImageView sdvSubPhoto3;
        @Bind(R.id.sdvSubPhoto4)
        FrescoImageView sdvSubPhoto4;

        HeaderViewHolder(View view) {

            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tvName)
        void onItemClick() {

            if (LogMgr.isDebug())
                LogMgr.d("HeaderViewHolder", "onClick--> position = " + getPosition());
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