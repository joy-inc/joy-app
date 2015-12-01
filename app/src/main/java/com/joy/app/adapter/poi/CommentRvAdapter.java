package com.joy.app.adapter.poi;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvMultipleAdapter;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.DimenCons;
import com.joy.app.R;
import com.joy.app.activity.poi.CommentScoresWidget;
import com.joy.app.bean.poi.CommentItem;
import com.joy.app.bean.poi.CommentScores;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品评论列表adapter
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentRvAdapter extends ExRvMultipleAdapter {

    private CommentScores mScore;
    private Activity mActivity;

    public CommentRvAdapter(Context context, Activity activity) {

        super(context);
        mHeaderCount = 1;
        mBottomCount = 1;
        mActivity = activity;
    }

    public void setScoreData(CommentScores data) {

        mScore = data;
    }

    @Override
    public int getContentItemCount() {

        return CollectionUtil.size(getData());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder vh = ((HeaderViewHolder) holder);
            vh.scoresWidget.invalidate(mScore);

        } else if (holder instanceof ContentViewHolder) {

            ArrayList<CommentItem> datas = (ArrayList<CommentItem>) getData();
            if (CollectionUtil.isEmpty(datas))
                return;

            int pos = position - mHeaderCount;
            ContentViewHolder vh = ((ContentViewHolder) holder);

            CommentItem data = datas.get(pos);

            vh.acRatingBar.setRating(Float.parseFloat(data.getComment_level()));
            vh.tvComment.setText(data.getComment());
            vh.tvCommentUserDate.setText(data.getComment_user() + " - " + data.getComment_date());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {

        CommentScoresWidget data = new CommentScoresWidget(mActivity);

        return new HeaderViewHolder(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {

        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_poi_comment, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {

        View view = new View(parent.getContext());
        view.setMinimumHeight(DimenCons.DP_1_PX * 16);
        return new BottomViewHolder(view);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private CommentScoresWidget scoresWidget;

        public HeaderViewHolder(CommentScoresWidget view) {

            super(view.getContentView());
            scoresWidget = view;
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.acRatingBar)
        AppCompatRatingBar acRatingBar;

        @Bind(R.id.tvComment)
        TextView tvComment;

        @Bind(R.id.tvCommentUserDate)
        TextView tvCommentUserDate;

        ContentViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class BottomViewHolder extends RecyclerView.ViewHolder {

        BottomViewHolder(View view) {

            super(view);
        }
    }
}
