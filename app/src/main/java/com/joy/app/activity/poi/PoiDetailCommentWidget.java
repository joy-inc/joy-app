package com.joy.app.activity.poi;

import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;
import com.joy.app.bean.CommentAll;
import com.joy.app.bean.CommentItem;
import com.joy.app.bean.CommentScores;
import com.joy.app.view.LinearListView;

/**
 * poi详情页的 点评列表部分
 * <p/>
 * Created by xiaoyu.chen on 15/5/7.
 */
public class PoiDetailCommentWidget extends ExLayoutWidget implements View.OnClickListener {

    private CommentLlvAdapter mAdapter;
    private LinearListView mLinearLv;
    private AppCompatButton mAcbSeeAll;
    private CommentScoresWidget mScoreWidget;

    public PoiDetailCommentWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        mAdapter = new CommentLlvAdapter();

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_comment, null);

        LinearLayout rootDiv = (LinearLayout) contentView.findViewById(R.id.llRootDiv);
        mScoreWidget = new CommentScoresWidget(getActivity());
        rootDiv.addView(mScoreWidget.getContentView(), 0);

        mLinearLv = (LinearListView) contentView.findViewById(R.id.linearLv);
        mAcbSeeAll = (AppCompatButton) contentView.findViewById(R.id.acbSeeAll);
        mAcbSeeAll.setOnClickListener(this);

        return contentView;
    }

    protected void invalidate(final CommentAll data) {

        if (data == null && data.getComments() != null)
            return;

        mScoreWidget.invalidate(data.getScores());

        if (CollectionUtil.size(data.getComments()) > 3) {
            mAdapter.setData(data.getComments().subList(0, 3));
            ViewUtil.showView(mAcbSeeAll);
        } else {
            mAdapter.setData(data.getComments());
            ViewUtil.goneView(mAcbSeeAll);
        }
        mLinearLv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }

    // ============= 内部类：LinearListView的adapter ==============

    class CommentLlvAdapter extends ExAdapter<CommentItem> {

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataHolder();
        }

        private class DataHolder extends ExViewHolderBase {

            private AppCompatRatingBar acRatingBar;
            private TextView tvComment;
            private TextView tvCommentUserDate;

            @Override
            public int getConvertViewRid() {

                return R.layout.item_poi_comment;
            }

            @Override
            public void initConvertView(View convertView) {

                acRatingBar = (AppCompatRatingBar) convertView.findViewById(R.id.acRatingBar);
                tvComment = (TextView) convertView.findViewById(R.id.tvComment);
                tvCommentUserDate = (TextView) convertView.findViewById(R.id.tvCommentUserDate);
            }

            @Override
            public void invalidateConvertView() {

                CommentItem data = getItem(mPosition);

                if (data != null) {

                    acRatingBar.setRating(Float.parseFloat(data.getComment_level()));
                    tvComment.setText(data.getComment());
                    tvCommentUserDate.setText(data.getComment_user() + " - " + data.getComment_date());
                }
            }
        }
    }
}
