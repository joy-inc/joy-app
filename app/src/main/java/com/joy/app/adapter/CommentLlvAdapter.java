package com.joy.app.adapter;

import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.CommentItem;
import com.joy.library.adapter.frame.ExAdapter;
import com.joy.library.adapter.frame.ExViewHolder;
import com.joy.library.adapter.frame.ExViewHolderBase;

/**
 * 商品评论列表adapter
 * Created by xiaoyu.chen on 15/11/20.
 */
public class CommentLlvAdapter extends ExAdapter<CommentItem> {

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

            return R.layout.item_comment;
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

            acRatingBar.setRating(Float.parseFloat(data.getComment_level()));
            tvComment.setText(data.getComment());
            tvCommentUserDate.setText(data.getComment_user() + " - " + data.getComment_date());
        }

    }

}
