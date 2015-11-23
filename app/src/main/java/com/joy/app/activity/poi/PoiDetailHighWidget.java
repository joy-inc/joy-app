package com.joy.app.activity.poi;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.view.LinearListView;
import com.joy.library.adapter.frame.ExAdapter;
import com.joy.library.adapter.frame.ExViewHolder;
import com.joy.library.adapter.frame.ExViewHolderBase;
import com.joy.library.view.ExLayoutWidget;

/**
 * 项目亮点
 * <p/>
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailHighWidget extends ExLayoutWidget {

    private HighlightAdapter mAdapter;
    private LinearListView mLinearListview;

    public PoiDetailHighWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        mAdapter = new HighlightAdapter();

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_high, null);

        mLinearListview = (LinearListView) contentView.findViewById(R.id.linearLv);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        mAdapter.setData(data.getHighlights());
        mLinearListview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    class HighlightAdapter extends ExAdapter<String> {

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataHolder();
        }

        private class DataHolder extends ExViewHolderBase {

            private TextView tvHighDesc;

            @Override
            public void invalidateConvertView() {

                String data = getItem(mPosition);

                if (data != null) {

                    tvHighDesc.setText(data);
                }
            }

            @Override
            public int getConvertViewRid() {

                return R.layout.item_product_highlight;
            }

            @Override
            public void initConvertView(View convertView) {

                tvHighDesc = (TextView) convertView.findViewById(R.id.tvHighDesc);
            }
        }
    }
}
