package com.joy.app.activity.poi;

import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.CommentAll;
import com.joy.app.bean.CommentItem;
import com.joy.app.view.LinearListView;
import com.joy.library.adapter.frame.ExAdapter;
import com.joy.library.adapter.frame.ExViewHolder;
import com.joy.library.adapter.frame.ExViewHolderBase;
import com.joy.library.utils.CollectionUtil;
import com.joy.library.utils.ViewUtil;
import com.joy.library.view.ExLayoutWidget;

/**
 * poi详情页的 点评列表部分
 * <p/>
 * Created by xiaoyu.chen on 15/5/7.
 */
public class PoiDetailCommentWidget extends ExLayoutWidget implements View.OnClickListener {

    private AppCompatRatingBar mAcRatingBar;
    private TextView mTvPoiCommentNum;
    private TextView tvCommentLevel;
    private CommentLlvAdapter adapter;
    private LinearListView mLinearLv;
    private AppCompatButton acbSeeAll;

    public PoiDetailCommentWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        adapter = new CommentLlvAdapter();

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_comment, null);

        mAcRatingBar = (AppCompatRatingBar) contentView.findViewById(R.id.acRatingBar);
        mTvPoiCommentNum = (TextView) contentView.findViewById(R.id.tvPoiCommentNum);
        tvCommentLevel = (TextView) contentView.findViewById(R.id.tvCommentLevel);

        mLinearLv = (LinearListView) contentView.findViewById(R.id.linearLv);
        acbSeeAll = (AppCompatButton) contentView.findViewById(R.id.acbSeeAll);
        acbSeeAll.setOnClickListener(this);

        return contentView;
    }

    protected void invalidate(final CommentAll data) {

        if (data == null && data.getScores() != null && data.getComments() != null)
            return;

        mAcRatingBar.setRating(Float.parseFloat(data.getScores().getComment_level()));
        mTvPoiCommentNum.setText(getActivity().getResources().getString(R.string.kuohao, data.getScores().getComment_num()));
        tvCommentLevel.setText(data.getScores().getComment_level());

        if (CollectionUtil.size(data.getComments()) > 3) {
            adapter.setData(data.getComments().subList(0, 3));
            ViewUtil.showView(acbSeeAll);
        } else {
            adapter.setData(data.getComments());
            ViewUtil.goneView(acbSeeAll);
        }
        mLinearLv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
