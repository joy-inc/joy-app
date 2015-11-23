package com.joy.app.activity.discount;

import android.app.Activity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.joy.app.R;
import com.joy.app.adapter.CommentLlvAdapter;
import com.joy.app.bean.CommentAll;
import com.joy.app.view.LinearListView;
import com.joy.library.utils.CollectionUtil;
import com.joy.library.view.ExLayoutWidget;

/**
 * poi详情页的 点评列表部分
 * <p/>
 * Created by xiaoyu.chen on 15/5/7.
 */
public class PoiDetailCommentWidget extends ExLayoutWidget implements View.OnClickListener {

    private CommentLlvAdapter adapter;
    private LinearListView mLlvComment;
    private AppCompatButton acbAllComment;

    public PoiDetailCommentWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_discount_poi_detail_comment, null);

        mLlvComment = (LinearListView) contentView.findViewById(R.id.llvComment);
        acbAllComment = (AppCompatButton) contentView.findViewById(R.id.acbAllComment);
        acbAllComment.setOnClickListener(this);

        adapter = new CommentLlvAdapter();
        mLlvComment.setAdapter(adapter);

        return contentView;
    }

    protected void invalidate(final CommentAll data) {

        if (data == null)
            return;

        if (CollectionUtil.size(data.getComments()) > 3) {
            adapter.setData(data.getComments().subList(0, 3));
        } else {
            adapter.setData(data.getComments());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }

}
