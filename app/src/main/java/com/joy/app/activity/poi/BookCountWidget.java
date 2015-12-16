package com.joy.app.activity.poi;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.Product;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.utils.JTextSpanUtil;
import com.joy.app.view.LinearListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu.chen on 15/12/2.
 */
public class BookCountWidget extends ExLayoutWidget {

    private TextView tvTitle;
    private LinearListView mLinearLv;
    private LevelAdapter mAdapter;
    private String mDateSubjectIds = TextUtil.TEXT_EMPTY;

    private Product mProduct;

    public void setProduct(Product product) {

        mProduct = product;
    }

    public BookCountWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_order_book_level_count, null);

        tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
        mLinearLv = (LinearListView) contentView.findViewById(R.id.linearLv);

        return contentView;
    }

    protected void invalidate(ProductLevels data) {

        if (data != null) {

            tvTitle.setText(data.getTitle());
            mAdapter = new LevelAdapter(data.getOptions());
            mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

                @Override
                public void onItemViewClick(int position, View clickView, Object o) {

                    LevelOptions data = mAdapter.getItem(position);

                    if (data != null) {

                        int count = MathUtil.parseInt(data.getLocalCount(), 0);

                        if (clickView.getId() == R.id.acbMinus) {

                            callbackOnChildMinusClick(position, count);
                            mOnBookItemClickListener.onClickBookItem();

                        } else if (clickView.getId() == R.id.acbPlus) {

                            callbackOnChildPlusClick(position, count);
                            mOnBookItemClickListener.onClickBookItem();
                        }
                    }
                }
            });

            mLinearLv.setAdapter(mAdapter);
            resetUnitPrice();
        }
    }

    private void callbackOnChildMinusClick(int position, int count) {

        if (count > 0) {
            count = count - 1;
            mAdapter.getItem(position).setLocalCount(count + "");
            ((TextView) mLinearLv.getChildAt(position).findViewById(R.id.tvCount)).setText(count + "");
        }
    }

    private final int MAX_COUNT = 99;

    private void callbackOnChildPlusClick(int position, int count) {

        if (count < MAX_COUNT) {
            count = count + 1;
            mAdapter.getItem(position).setLocalCount(count + "");
            ((TextView) mLinearLv.getChildAt(position).findViewById(R.id.tvCount)).setText(count + "");
        } else {
            ToastUtil.showToast(R.string.toast_over_limit);
        }
    }

    protected List<LevelOptions> getSelectId() {

        List<LevelOptions> list = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(mAdapter.getData())) {

            for (LevelOptions data : mAdapter.getData()) {

                if (!"0".equals(data.getLocalCount())) {
                    list.add(data);
                }
            }
        }

        return list;
    }

    protected String createOrderItemStr() {

        if (CollectionUtil.isNotEmpty(getSelectId())) {

            List<String> list = new ArrayList<>();

            for (LevelOptions data : getSelectId()) {

                Product.Item item = getItemObject(data.getOption_id());
                if (item != null)
                    list.add(item.getItem_id() + "-" + data.getLocalCount());
            }

            if (CollectionUtil.isEmpty(list))
                return TextUtil.TEXT_EMPTY;

            return list.toString().trim().replace("[", "").replace("]", "");

        } else {
            return TextUtil.TEXT_EMPTY;
        }
    }

    private Product.Item getItemObject(String key) {

        if (TextUtil.isNotEmpty(mDateSubjectIds))
            key = "_" + key;

        return mProduct.getItems().get(mDateSubjectIds + key);
    }

    // 重新选择日期、项目时，重置每个产品的单价
    protected void resetUnitPrice() {

        if (CollectionUtil.isNotEmpty(mAdapter.getData())) {

            for (int i = 0; i < mAdapter.getCount(); i++) {

                Product.Item item = getItemObject(mAdapter.getItem(i).getOption_id());

                if (item != null)
                    refreshUnitPrice(i, item.getPrice());
                else
                    refreshUnitPrice(i, "0");// 找不到匹配项，金额置0
            }
        }
    }

    private void refreshUnitPrice(int position, String unitPrice) {

        mAdapter.getItem(position).setLocalPrice(unitPrice);
        ((TextView) mLinearLv.getChildAt(position).findViewById(R.id.tvPrice)).setText(JTextSpanUtil.getFormatUnitStr(getActivity().getString(R.string.unit, unitPrice)));
    }

    protected void setDateSubjectIds(String dateSubjectIds) {

        mDateSubjectIds = dateSubjectIds;
    }

// =================================

    private class LevelAdapter extends ExAdapter<LevelOptions> {

        public LevelAdapter(List<LevelOptions> data) {

            super(data);
        }

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataHolder();
        }

        class DataHolder extends ExViewHolderBase {

            TextView tvContent;
            TextView tvDesc;
            TextView tvPrice;
            TextView tvCount;
            TextView acbMinus;
            TextView acbPlus;

            @Override
            public int getConvertViewRid() {

                return R.layout.item_order_level_count_option;
            }

            @Override
            public void initConvertView(View convertView) {

                tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
                tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                tvCount = (TextView) convertView.findViewById(R.id.tvCount);
                acbMinus = (TextView) convertView.findViewById(R.id.acbMinus);
                acbPlus = (TextView) convertView.findViewById(R.id.acbPlus);

                acbMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        callbackOnItemViewClickListener(mPosition, v);
                    }
                });

                acbPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        callbackOnItemViewClickListener(mPosition, v);
                    }
                });
            }

            @Override
            public void invalidateConvertView() {

                LevelOptions data = getItem(mPosition);

                if (data != null) {

                    tvContent.setText(data.getContent());
                    tvDesc.setText(data.getDescribe());
                    tvDesc.setVisibility(TextUtil.isEmpty(data.getDescribe()) ? View.GONE : View.VISIBLE);

                    if (TextUtil.isEmpty(data.getLocalPrice()))
                        data.setLocalPrice("0");

                    if (mPosition == 0) {
                        data.setLocalCount("1");
                    } else if (TextUtil.isEmpty(data.getLocalCount())) {
                        data.setLocalCount("0");
                    }

                    tvPrice.setText(JTextSpanUtil.getFormatUnitStr(getActivity().getString(R.string.unit, data.getLocalPrice())));
                    tvCount.setText(data.getLocalCount());
                }
            }
        }
    }


// =================================

    private OnBookItemClickListener mOnBookItemClickListener;

    public interface OnBookItemClickListener {

        void onClickBookItem();
    }

    public void setOnBookItemClickListener(OnBookItemClickListener onBookItemClickListener) {
        this.mOnBookItemClickListener = onBookItemClickListener;
    }
}
