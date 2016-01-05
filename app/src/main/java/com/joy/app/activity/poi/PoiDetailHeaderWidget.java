package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.CollectionUtil;
import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExLayoutWidget;
import com.android.library.view.banner.BannerAdapter;
import com.android.library.view.banner.BannerImage;
import com.android.library.view.banner.BannerWidget;
import com.android.library.widget.FrescoImageView;
import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.utils.JTextSpanUtil;


/**
 * poi详情的头部内容（头图、国家名称、评星、去过、想去）
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailHeaderWidget extends ExLayoutWidget implements View.OnClickListener {

    private BannerAdapter<String> mAdapter;
    private BannerWidget mBannerWidget;
    private LinearLayout mLlBannerDiv;
    private TextView mTvTitle;
    private TextView mTvBookBefore;
    private TextView mTvPrice;
    private AppCompatRatingBar mAcRatingBar;
    private TextView mTvPoiCommentNum;
    private LinearLayout llAddPlanDiv;
    private TextView mBtnAddToPlan;
    private TextView mTvName;

    public PoiDetailHeaderWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_header, null);

        initContentViews(contentView);

        return contentView;
    }

    @Override
    public void onResume() {

        super.onResume();
        if (mBannerWidget != null)
            mBannerWidget.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
        if (mBannerWidget != null)
            mBannerWidget.onPause();
    }

    private void initContentViews(View contentView) {

        mLlBannerDiv = (LinearLayout) contentView.findViewById(R.id.llBannerDiv);
        mTvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        mTvBookBefore = (TextView) contentView.findViewById(R.id.tvBookBefore);
        mAcRatingBar = (AppCompatRatingBar) contentView.findViewById(R.id.acRatingBar);
        llAddPlanDiv = (LinearLayout) contentView.findViewById(R.id.llAddPlanDiv);
        llAddPlanDiv.setOnClickListener(this);
        mBtnAddToPlan = (TextView) contentView.findViewById(R.id.tvAddToPlan);
        mTvName = (TextView) contentView.findViewById(R.id.tvName);
        mTvPrice = (TextView) contentView.findViewById(R.id.tvPrice);
        mTvPoiCommentNum = (TextView) contentView.findViewById(R.id.tvPoiCommentNum);

    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        if (CollectionUtil.isEmpty(data.getPhotos()))
            data.getPhotos().add(""); // 没有数据时，加一个空占位

        mAdapter = new BannerAdapter(new BannerImage<String>() {

            @Override
            protected FrescoImageView onCreateView(Context context) {

                return (FrescoImageView) getActivity().getLayoutInflater().inflate(R.layout.item_banner, null);
            }
        });
        mAdapter.setData(data.getPhotos());
        mBannerWidget = new BannerWidget(getActivity(), mAdapter);

        if (mLlBannerDiv.getChildCount() <= 0)
            mLlBannerDiv.addView(mBannerWidget.getContentView());

        if (TextUtil.isNotEmpty(data.getFolder_id())) {

            mBtnAddToPlan.setText(R.string.added);
            mBtnAddToPlan.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_poi_add_plan_selected, 0, 0, 0);
            mTvName.setText(data.getFolder_name());
            llAddPlanDiv.setSelected(true);
        }

        if (data.getIs_book() && TextUtil.isNotEmpty(data.getPrice()) && !"0".equals(data.getPrice())) {
            mTvPrice.setText(JTextSpanUtil.getFormatUnitStr(getActivity().getString(R.string.fmt_unit, data.getPrice())));
            ViewUtil.showView(mTvPrice);
        }

        if (TextUtil.isNotEmpty(data.getBooking_before()) && !"0".equals(data.getBooking_before())) {
            mTvBookBefore.setText(getActivity().getString(R.string.fmt_poi_before, data.getBooking_before()));
            ViewUtil.showView(mTvBookBefore);
        }

        mTvTitle.setText(data.getTitle());

        if (TextUtil.isNotEmpty(data.getComment_num()) && !"0".equals(data.getComment_num())) {

            mAcRatingBar.setRating(MathUtil.parseFloat(data.getComment_level(), 0));
            mTvPoiCommentNum.setText(getActivity().getResources().getString(R.string.fmt_kuohao, TextUtil.isEmpty(data.getComment_num()) ? "0" : data.getComment_num()));
            ViewUtil.showView(mAcRatingBar);
            ViewUtil.showView(mTvPoiCommentNum);
        }

    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }
}
