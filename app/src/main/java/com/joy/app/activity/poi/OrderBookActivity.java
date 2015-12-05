package com.joy.app.activity.poi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.Product;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品项目选择页
 */
public class OrderBookActivity extends BaseHttpUiActivity<Product> {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_book);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {

        super.initData();
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
                // todo open date picker activity for result
                Intent intent = new Intent(OrderBookActivity.this, PoiDetailActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 0);

                mSelectPosition = position;
            }
        });
        mSubjectWidget.setOnBookItemClickListener(new BookSubjectWidget.OnBookItemClickListener() {

            @Override
            public void onClickBookItem(int position, List<LevelOptions> options) {
                // todo open date picker activity for result
                Intent intent = new Intent(OrderBookActivity.this, PoiDetailActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 0);

                mSelectPosition = position;
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

                LogMgr.w("item ids~~~=====" + createOrderItemStr());
                checkAndStartProfileActivity(v);
            }
        });

        mTvPrice.setText("0");
    }

    @Override
    protected boolean invalidateContent(Product product) {

        if (CollectionUtil.isNotEmpty(product.getLevels())) {

            List<ProductLevels> list1 = new ArrayList<>();
            List<ProductLevels> list2 = new ArrayList<>();
//            List<ProductLevels> list3 = new ArrayList<>();
            ProductLevels data3 = null;

            for (ProductLevels data : product.getLevels()) {

                if ("1".equals(data.getType())) {

                    list1.add(data);

                } else if ("2".equals(data.getType())) {

                    list2.add(data);

                } else if ("3".equals(data.getType())) {

//                    list3.add(data);
                    data3 = data;
                }
            }

            mDateWidget.invalidate(list1);
            mSubjectWidget.invalidate(list2);
//            mCountWidget.invalidate(list3);
            mCountWidget.invalidate(data3);
        }

        return CollectionUtil.isNotEmpty(product.getLevels());
    }

    private void checkAndStartProfileActivity(View v) {

        if (mDateWidget.isAllSelect()) {
            mOrderItem = createOrderItemStr();
            OrderBookProfileActivity.startActivity(OrderBookActivity.this, v, mPhotoUrl, mTitle, mTvPrice.getText().toString(), mOrderItem);
        } else {
            showToast(R.string.toast_input_date);
        }
    }

    private String createOrderItemStr() {

        List<String> list = new ArrayList<>();

        for (LevelOptions data : mCountWidget.getSelectId()) {

            String itemStr = createDateSubjectStr();

            itemStr = itemStr + "_" + data.getOption_id() + "-" + data.getLocalCount();

            list.add(itemStr);
        }

        return list.toString();
    }

    private String createDateSubjectStr() {

        String itemStr = mDateWidget.getSelectId() + "_" + mSubjectWidget.getSelectId();

        return itemStr;
    }

    private void refreshUnitPrice() {


    }

    private void refreshTotalPrice() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 15/11/26 选择日期、项目后 重置金额
        // 假装么接收日期选择参数

        int position = mSelectPosition;
        LevelOptions options = new LevelOptions();
        options.setOption_id("11");
        options.setContent("成人");
        options.setDescribe("8-13岁");

        mDateWidget.resetSelectValue(position, options);
        mSubjectWidget.resetSelectValue(position, options);
    }


    @Override
    protected ObjectRequest<Product> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_OPTIONS, Product.class, OrderHtpUtil.getProductOptionListUrl(mId));

        if (BuildConfig.DEBUG) {

            Product data = new Product();
            ArrayList listdata = new ArrayList();
            ProductLevels levels = null;
            for (int i = 0; i < 6; i++) {
                levels = new ProductLevels();

                levels.setLevel_id((i + 1) + "");

                if (i == 0 || i == 1) {
                    levels.setType(1 + "");
                    levels.setTitle("我是日期：type为1");
                } else if (i == 2 || i == 3) {
                    levels.setType(2 + "");
                    levels.setTitle("我是可选项目：type为2");
                } else if (i == 4 || i == 5) {
                    levels.setType(3 + "");
                    levels.setTitle("我是可选条件：type为3");
                }

                ArrayList list = new ArrayList();

                for (int j = 0; j < 5; j++) {
                    LevelOptions options = new LevelOptions();
                    options.setOption_id(i + "" + j);
                    options.setContent("成人" + i + ", 儿童" + j);
                    options.setDescribe("13-99岁 " + j);

                    list.add(options);
                }

                levels.setOptions(list);
                listdata.add(levels);
            }

            data.setLevels(listdata);

            obj.setData(data);
        }

        return obj;
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
//            act.startActivity(intent, options.toBundle());
//        } else {

            act.startActivity(intent);
//        }
    }

}
