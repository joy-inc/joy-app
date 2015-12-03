package com.joy.app.activity.poi;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.TextUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.view.LinearListView;

import java.util.List;

/**
 * Created by xiaoyu.chen on 15/12/2.
 */
public class BookDateWidget extends ExLayoutWidget {

    private LinearListView mLinearLv;
    private LevelAdapter mAdapter;

    public BookDateWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_order_book_level, null);

        mLinearLv = (LinearListView) contentView.findViewById(R.id.linearLv);

        mLinearLv.setOnItemClickListener(new LinearListView.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

                callbackOnItemClick(position);
            }
        });

        return contentView;
    }

    protected void invalidate(List<ProductLevels> listData) {

        if (!CollectionUtil.isEmpty(listData)) {

            mAdapter = new LevelAdapter(listData);
            mLinearLv.setAdapter(mAdapter);
        }
    }

    private void callbackOnItemClick(int position) {

        mOnBookItemClickListener.onClickBookItem(position, mAdapter.getItem(position).getOptions());
    }

    protected void resetSelectValue(int position, LevelOptions data) {

        mAdapter.getItem(position).setLocalSelectId(data.getOption_id());
        ((TextView) mLinearLv.getChildAt(position).findViewById(R.id.tvContent)).setText(data.getContent());
    }

    protected boolean isAllSelect() {

        for (ProductLevels data : mAdapter.getData()) {

            if (TextUtil.isEmpty(data.getLocalSelectId()))
                return false;
        }

        return true;
    }

    protected String getSelectId() {

        String ids = TextUtil.TEXT_EMPTY;

        for (ProductLevels data : mAdapter.getData()) {
            ids = ids + (TextUtil.isEmpty(ids) ? data.getLocalSelectId() : "_" + data.getLocalSelectId());
        }

        return ids;
    }

// =================================

    private class LevelAdapter extends ExAdapter<ProductLevels> {

        public LevelAdapter(List<ProductLevels> data) {

            super(data);
        }

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataViewHolder();
        }

        private class DataViewHolder extends ExViewHolderBase {

            private TextView tvTitle;
            private TextView tvContent;

            @Override
            public void invalidateConvertView() {

                ProductLevels data = getItem(mPosition);

                if (data != null) {
                    tvTitle.setText(data.getTitle());
                    tvContent.setText(data.getLocalContent());
                }
            }

            @Override
            public int getConvertViewRid() {

                return R.layout.item_order_level_date;
            }

            @Override
            public void initConvertView(View convertView) {

                tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            }
        }
    }

// =================================

    private OnBookItemClickListener mOnBookItemClickListener;

    public interface OnBookItemClickListener {

        void onClickBookItem(int position, List<LevelOptions> options);

    }

    public void setOnBookItemClickListener(OnBookItemClickListener onBookItemClickListener) {
        this.mOnBookItemClickListener = onBookItemClickListener;
    }
}
