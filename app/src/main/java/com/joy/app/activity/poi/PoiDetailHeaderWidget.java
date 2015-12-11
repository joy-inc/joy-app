package com.joy.app.activity.poi;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.TextView;

import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.view.ExLayoutWidget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.JTextSpanUtil;


/**
 * poi详情的头部内容（头图、国家名称、评星、去过、想去）
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailHeaderWidget extends ExLayoutWidget implements View.OnClickListener {

    private PoiDetail mPoiDetail;

    private SimpleDraweeView mSdvPhoto;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private AppCompatRatingBar mAcRatingBar;
    private TextView mTvPoiCommentNum;
    private TextView mBtnAddToPlan;

    public PoiDetailHeaderWidget(Activity activity) {
        super(activity);
    }


    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_header, null);

        initContentViews(contentView);

        return contentView;
    }

    private void initContentViews(View contentView) {

        mSdvPhoto = (SimpleDraweeView) contentView.findViewById(R.id.sdvPhoto);
        mTvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        mAcRatingBar = (AppCompatRatingBar) contentView.findViewById(R.id.acRatingBar);
        mBtnAddToPlan = (TextView) contentView.findViewById(R.id.btnAddToPlan);
        mBtnAddToPlan.setOnClickListener(this);
        mTvPrice = (TextView) contentView.findViewById(R.id.tvPrice);
        mTvPoiCommentNum = (TextView) contentView.findViewById(R.id.tvPoiCommentNum);

    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        mPoiDetail = data;

        try {
            mSdvPhoto.setImageURI(Uri.parse(mPoiDetail.getPhotos().get(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtil.isNotEmpty(data.getFolder_id()) ){

            mBtnAddToPlan.setText("已加入 "+data.getFolder_name());
        }

        mTvTitle.setText(mPoiDetail.getTitle());
        mTvPrice.setText(JTextSpanUtil.getFormatUnitStr(getActivity().getString(R.string.unit, mPoiDetail.getPrice())));
        mAcRatingBar.setRating(MathUtil.parseFloat(mPoiDetail.getComment_level(), 0));
        mTvPoiCommentNum.setText(getActivity().getResources().getString(R.string.kuohao, mPoiDetail.getComment_num()));

    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }
}
