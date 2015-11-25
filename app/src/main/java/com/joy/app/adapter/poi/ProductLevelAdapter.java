package com.joy.app.adapter.poi;

import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.utils.LogMgr;
import com.joy.app.R;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.view.LinearListView;

/**
 * Created by xiaoyu.chen on 15/11/24.
 */
public class ProductLevelAdapter extends ExAdapter<ProductLevels> {

    public enum ITEM_TYPE {

        ITEM_TYPE_DATE(1),
        ITEM_TYPE_CONTENT(2),
        ITEM_TYPE_NUMBER(3);

        private final int code;

        ITEM_TYPE(int code) {
            this.code = code;
        }
    }

    class OrderOptionAdapter extends ExAdapter<LevelOptions> {

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataHolder();
        }

        class DataHolder extends ExViewHolderBase {

            TextView tvContent;
            TextView tvDesc;
            TextView tvPrice;
            TextView tvCount;
            AppCompatButton acbMinus;
            AppCompatButton acbPlus;

            @Override
            public void invalidateConvertView() {

                LevelOptions data = getItem(mPosition);

                if (data != null) {

                    tvContent.setText(data.getContent());
                    tvDesc.setText(data.getDescribe());
                    tvPrice.setText(data.getLocalPrice());
                    tvCount.setText(data.getLocalCount());
                }
            }

            @Override
            public int getConvertViewRid() {

                return R.layout.item_poi_level_option;
            }

            @Override
            public void initConvertView(View convertView) {

                tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
                tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                tvCount = (TextView) convertView.findViewById(R.id.tvCount);
                acbMinus = (AppCompatButton) convertView.findViewById(R.id.acbMinus);
                acbPlus = (AppCompatButton) convertView.findViewById(R.id.acbPlus);

                acbMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (LogMgr.isDebug())
                            LogMgr.d("ContentViewHolder", "onClick-->acbMinus position = " + mPosition);
                    }
                });

                acbPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (LogMgr.isDebug())
                            LogMgr.d("ContentViewHolder", "onClick-->acbPlus position = " + mPosition);
                    }
                });
            }
        }
    }

    @Override
    public int getViewTypeCount() {

        return 3;
    }

    @Override
    public int getItemViewType(int position) {

        int type = Integer.parseInt(getItem(position).getType());
        return type;
    }

    @Override
    protected ExViewHolder getViewHolder(int position) {

        ExViewHolderBase viewHolder = null;
        int type = getItemViewType(position);

        if (type == ITEM_TYPE.ITEM_TYPE_DATE.code) {

            viewHolder = new DateViewHolder();

        } else if (type == ITEM_TYPE.ITEM_TYPE_CONTENT.code) {

            viewHolder = new ContentViewHolder();

        } else if (type == ITEM_TYPE.ITEM_TYPE_NUMBER.code) {

            viewHolder = new NumberViewHolder();

        }

        return viewHolder;
    }

    class DateViewHolder extends ExViewHolderBase {

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

            return R.layout.item_poi_level_date;
        }

        @Override
        public void initConvertView(View convertView) {

            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        }
    }

    class ContentViewHolder extends ExViewHolderBase {

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

            return R.layout.item_poi_level_content;
        }

        @Override
        public void initConvertView(View convertView) {

            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        }
    }

    class NumberViewHolder extends ExViewHolderBase {

        private TextView tvTitle;
        private LinearListView linearLv;

        @Override
        public void invalidateConvertView() {

            ProductLevels data = getItem(mPosition);

            if (data != null) {
                tvTitle.setText(data.getTitle());

                OrderOptionAdapter adapter = new OrderOptionAdapter();
                adapter.setData(data.getOptions());

                linearLv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public int getConvertViewRid() {

            return R.layout.item_poi_level_number;
        }

        @Override
        public void initConvertView(View convertView) {

            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            linearLv = (LinearListView) convertView.findViewById(R.id.linearLv);
        }
    }
}
