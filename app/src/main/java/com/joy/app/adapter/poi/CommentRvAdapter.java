package com.joy.app.adapter.poi;

import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.poi.CommentItem;
import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品评论列表adapter
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentRvAdapter extends ExRvAdapter<CommentRvAdapter.ViewHolder, CommentItem> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        return new ViewHolder(inflate(parent, R.layout.item_poi_comment));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CommentItem data = getItem(position);

        if (data != null) {

            holder.acRatingBar.setRating(Float.parseFloat(data.getComment_level()));
            holder.tvComment.setText(data.getComment());
            holder.tvCommentUserDate.setText(data.getComment_user() + " - " + data.getComment_date());

        }
    }

    public class ViewHolder extends ExRvViewHolder {

        @Bind(R.id.acRatingBar)
        AppCompatRatingBar acRatingBar;

        @Bind(R.id.tvComment)
        TextView tvComment;

        @Bind(R.id.tvCommentUserDate)
        TextView tvCommentUserDate;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
