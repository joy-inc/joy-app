package com.joy.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 线性布局构建成的listview
 * <p/>
 * create by xiaoyu.chen at 11/23/2015
 */
public class LinearListView extends LinearLayout {

    private OnItemClickListener mOnItemClickLisn;

    public LinearListView(Context context) {
        super(context);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(BaseAdapter adapter) {

        if (getChildCount() != 0)
            removeAllViews();

        if (adapter == null)
            return;

        View convertView = null;
        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {

            convertView = adapter.getView(i, null, this);
            final int position = i;
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLisn != null)
                        mOnItemClickLisn.onItemClick(position);
                }
            });

            addView(convertView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

        }
    }

    public void setOnItemClickListener(OnItemClickListener lisn) {
        mOnItemClickLisn = lisn;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
