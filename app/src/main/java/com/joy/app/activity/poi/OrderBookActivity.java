package com.joy.app.activity.poi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.MathUtil;
import com.android.library.utils.TextUtil;
import com.android.library.utils.TimeUtil;
import com.android.library.view.dialogplus.DialogPlus;
import com.android.library.view.dialogplus.ListHolder;
import com.android.library.view.dialogplus.OnCancelListener;
import com.android.library.widget.JDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.activity.common.DayPickerActivity;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.Product;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.eventbus.PayStatusEvent;
import com.joy.app.utils.JTextSpanUtil;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 商品项目选择页
 */
public class OrderBookActivity extends BaseHttpUiActivity<Product> {

    private final int REQ_CODE_DATE = 0;

    private String mId;
    private String mPhotoUrl;
    private String mTitle;
    private String mOrderItem = TextUtil.TEXT_EMPTY;

    private SimpleDraweeView sdvPhoto;
    private TextView tvTitle;

    private TextView mTvPrice;
    private AppCompatButton mAcbNext;

    private int mSelectPosition = -1;

    private BookDateWidget mDateWidget;
    private BookSubjectWidget mSubjectWidget;
    private BookCountWidget mCountWidget;

    private DialogPlus mSubjectDialog;
    private int index = 0;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_book);
        executeRefreshOnly();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {

        super.initData();
        EventBus.getDefault().register(this);
        mId = TextUtil.filterNull(getIntent().getStringExtra("id"));
        mPhotoUrl = TextUtil.filterNull(getIntent().getStringExtra("photoUrl"));
        mTitle = TextUtil.filterNull(getIntent().getStringExtra("title"));
    }


    @Override
    protected void initTitleView() {

        super.initTitleView();
        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        initHeaderView();
        initBottomView();

        LinearLayout layout = (LinearLayout) findViewById(R.id.llContentDiv);
        mDateWidget = new BookDateWidget(this);
        mSubjectWidget = new BookSubjectWidget(this);
        mCountWidget = new BookCountWidget(this);

        layout.addView(mDateWidget.getContentView());
        layout.addView(mSubjectWidget.getContentView());
        layout.addView(mCountWidget.getContentView());

        mDateWidget.setOnBookItemClickListener(new BookDateWidget.OnBookItemClickListener() {

            @Override
            public void onClickBookItem(int position, List<LevelOptions> options) {

                mSelectPosition = position;
                startDayPickerActivity(options);
            }
        });
        mSubjectWidget.setOnBookItemClickListener(new BookSubjectWidget.OnBookItemClickListener() {

            @Override
            public void onClickBookItem(int position, final List<LevelOptions> options) {

                mSelectPosition = position;
                startSubjectPickerDialog(options);

            }
        });
        mCountWidget.setOnBookItemClickListener(new BookCountWidget.OnBookItemClickListener() {

            @Override
            public void onClickBookItem() {

                refreshTotalPrice();
            }
        });
    }

    private void initHeaderView() {

        sdvPhoto = (SimpleDraweeView) findViewById(R.id.sdvPhoto);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        sdvPhoto.setImageURI(Uri.parse(mPhotoUrl));
        tvTitle.setText(mTitle);
    }

    private void initBottomView() {

        mTvPrice = (TextView) findViewById(R.id.tvTotalPrice);
        mAcbNext = (AppCompatButton) findViewById(R.id.acbNext);
        mAcbNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkAndStartProfileActivity(v);
            }
        });

        mTvPrice.setText(JTextSpanUtil.getFormatUnitStr(getString(R.string.unit, "0")));
    }

    @Override
    protected boolean invalidateContent(Product product) {

        mCountWidget.setProduct(product);

        if (CollectionUtil.isNotEmpty(product.getLevels())) {

            List<ProductLevels> list1 = new ArrayList<>();
            List<ProductLevels> list2 = new ArrayList<>();
            ProductLevels data3 = null;

            for (ProductLevels data : product.getLevels()) {

                if ("1".equals(data.getType())) {

                    list1.add(data);

                } else if ("2".equals(data.getType())) {

                    list2.add(data);

                } else if ("3".equals(data.getType())) {

                    data3 = data;
                }
            }

            mDateWidget.invalidate(list1);
            mSubjectWidget.invalidate(list2);
            mCountWidget.setDateSubjectIds(createDateSubjectStr());
            mCountWidget.invalidate(data3);
        }

        return CollectionUtil.isNotEmpty(product.getLevels());
    }

    private void startDayPickerActivity(List<LevelOptions> listData) {

        if (CollectionUtil.isNotEmpty(listData)) {

            LevelOptions data = listData.get(0);
            long beginTime = MathUtil.parseLong(data.getStart_time(), 0) * 1000;
            long endTime = MathUtil.parseLong(data.getEnd_time(), 0) * 1000;
            DayPickerActivity.startOrderDayPickerForResult(OrderBookActivity.this, beginTime, endTime, 0, REQ_CODE_DATE);
        }
    }

    private void startSubjectPickerDialog(final List<LevelOptions> options) {

        if (CollectionUtil.isNotEmpty(options)) {

            if (mSubjectDialog == null) {
                options.get(0).setLocalCheck(true);
                final DialogSubjectAdapter adapter = new DialogSubjectAdapter();
                adapter.setData(options);
                adapter.setOnItemViewClickListener(new OnItemViewClickListener<LevelOptions>() {

                    @Override
                    public void onItemViewClick(int position, View clickView, LevelOptions option) {

                        currentIndex = position;

                        if (!adapter.getItem(position).isLocalCheck()) {

                            for (LevelOptions data : options) {
                                data.setLocalCheck(false);
                            }

                            adapter.getItem(position).setLocalCheck(!adapter.getItem(position).isLocalCheck());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                mSubjectDialog = DialogPlus.newDialog(OrderBookActivity.this)
                        .setContentHolder(new ListHolder())
                        .setHeader(R.layout.view_header_dialog_orderbook)
                        .setFooter(R.layout.view_footer_dialog_orderbook)
                        .setCancelable(true)
                        .setAdapter(adapter)
                        .setOnCancelListener(new OnCancelListener() {

                            @Override
                            public void onCancel(DialogPlus dialog) {

                                for (LevelOptions data : adapter.getData()) {
                                    data.setLocalCheck(false);
                                }

                                adapter.getItem(index).setLocalCheck(true);
                            }
                        })
                        .create();

                mSubjectDialog.getFooterView().setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        index = currentIndex;

                        for (LevelOptions data : adapter.getData()) {
                            data.setLocalCheck(false);
                        }
                        adapter.getItem(index).setLocalCheck(true);

                        mSubjectWidget.resetSelectValue(mSelectPosition, adapter.getItem(index));
                        mCountWidget.setDateSubjectIds(createDateSubjectStr());
                        mCountWidget.resetUnitPrice();
                        mSubjectDialog.dismiss();

                    }
                });
            }
            mSubjectDialog.show();
        }
    }

    private void checkAndStartProfileActivity(View v) {

        do {

            if (!mDateWidget.isAllSelect()) {
                showToast(R.string.toast_input_date);
                break;
            }

            if (CollectionUtil.isEmpty(mCountWidget.getSelectId())) {
                showToast("请选择产品数量");
                break;
            }

            mOrderItem = mCountWidget.createOrderItemStr();
            OrderBookProfileActivity.startActivity(OrderBookActivity.this, v, mPhotoUrl, mTitle, mTvPrice.getText().toString(), mOrderItem, mDateWidget.getmDateTime());

        } while (false);
    }

    private String createDateSubjectStr() {

        String itemStr = mDateWidget.getSelectId() + "_" + mSubjectWidget.getSelectId();

        return itemStr;
    }

    private void refreshTotalPrice() {

        Float totalPrice = 0f;
        for (int i = 0; i < CollectionUtil.size(mCountWidget.getSelectId()); i++) {

            LevelOptions data = mCountWidget.getSelectId().get(i);
            Float itemTotalPrice = MathUtil.parseFloat(data.getLocalPrice(), 0) * MathUtil.parseInt(data.getLocalCount(), 0);
            totalPrice = totalPrice + itemTotalPrice;
        }

        mTvPrice.setText(JTextSpanUtil.getFormatUnitStr(getString(R.string.unit, formatPrice(totalPrice))));
    }

    public static String formatPrice(double total) {

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        twoDForm.setDecimalFormatSymbols(dfs);

        return twoDForm.format(total) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        int position = mSelectPosition;

        if (requestCode == REQ_CODE_DATE) {

            long time = data.getLongExtra("beginDate", 0);
            LevelOptions options = mDateWidget.getDataItem(position);
            options.setContent(TimeUtil.getSimpleTypeChineseText(time));
            mDateWidget.resetSelectValue(position, options);
            mDateWidget.setmDateTime(String.valueOf(time / 1000));
            mCountWidget.setDateSubjectIds(createDateSubjectStr());
            mCountWidget.resetUnitPrice();
            refreshTotalPrice();
        }
    }


    @Override
    protected ObjectRequest<Product> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_OPTIONS, Product.class, OrderHtpUtil.getProductOptionListUrl(mId));

        return obj;
    }

    private void showAlertDialog() {

        new JDialog.Builder(this).setTitle(R.string.alert_drop_content).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        }).create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAlertDialog();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 支付状态的回调 关闭支付页之前的预订流程页面
     *
     * @param event
     */
    public void onEventMainThread(PayStatusEvent event) {

        finish();
    }

    /**
     * @param act
     * @param view The view which starts the transition
     */
    public static void startActivity(Activity act, View view, String... params) {

        if (act == null || view == null || params == null || params.length < 3)
            return;

        Intent intent = new Intent(act, OrderBookActivity.class);
        intent.putExtra("id", params[0]);
        intent.putExtra("photoUrl", params[1]);
        intent.putExtra("title", params[2]);

        act.startActivity(intent);
    }

    private class DialogSubjectAdapter extends ExAdapter<LevelOptions> {

        @Override
        protected ExViewHolder getViewHolder(int position) {

            return new DataViewHolder();
        }

        private class DataViewHolder extends ExViewHolderBase {

            private TextView tvTitle;
            private AppCompatCheckBox acCheckBox;
            private View vDivider;

            @Override
            public int getConvertViewRid() {

                return R.layout.item_order_dialog_subject;
            }

            @Override
            public void initConvertView(View convertView) {

                tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                acCheckBox = (AppCompatCheckBox) convertView.findViewById(R.id.acCheckBox);
                vDivider = convertView.findViewById(R.id.vDivider);
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        callbackOnItemViewClickListener(mPosition, v);
                    }
                });
            }

            @Override
            public void invalidateConvertView() {

                LevelOptions data = getItem(mPosition);

                tvTitle.setText(data.getContent());
                acCheckBox.setChecked(data.isLocalCheck());
                if (mPosition == 0)
                    goneView(vDivider);
                else
                    showView(vDivider);
            }
        }
    }

}
