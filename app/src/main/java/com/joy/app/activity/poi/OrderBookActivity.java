package com.joy.app.activity.poi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.BuildConfig;
import com.joy.app.R;
import com.joy.app.adapter.poi.ProductLevelAdapter;
import com.joy.app.bean.poi.LevelOptions;
import com.joy.app.bean.poi.Product;
import com.joy.app.bean.poi.ProductLevels;
import com.joy.app.utils.http.OrderHtpUtil;
import com.joy.app.utils.http.ReqFactory;
import com.joy.app.view.LinearListView;

import java.util.ArrayList;

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
    private LinearListView linearLv;
    private ProductLevelAdapter mAdapter;

    private TextView mTvPrice;
    private AppCompatButton mAcbNext;

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

        sdvPhoto = (SimpleDraweeView) findViewById(R.id.sdvPhoto);
        linearLv = (LinearListView) findViewById(R.id.linearLv);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        mAdapter = new ProductLevelAdapter();
        mAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {

            @Override
            public void onItemViewClick(int position, View clickView, Object o) {

                ProductLevels data = mAdapter.getItem(position);
                if (data != null) {

                    showToast(data.getLevel_id());
                }
            }
        });

        mTvPrice = (TextView) findViewById(R.id.tvTotalPrice);
        mAcbNext = (AppCompatButton) findViewById(R.id.acbNext);
        mAcbNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                OrderBookProfileActivity.startActivity(OrderBookActivity.this, v, mPhotoUrl, mTitle, mTvPrice.getText().toString(), mOrderItem);
            }
        });
    }

    @Override
    protected boolean invalidateContent(Product product) {

        sdvPhoto.setImageURI(Uri.parse(mPhotoUrl));
        tvTitle.setText(mTitle);

        mAdapter.setData(product.getLevels());
        linearLv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mTvPrice.setText("0");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // // TODO: 15/11/26 选择日期、项目后 重置金额
    }

    @Override
    protected ObjectRequest<Product> getObjectRequest() {

        ObjectRequest obj = ReqFactory.newPost(OrderHtpUtil.URL_POST_OPTIONS, Product.class, OrderHtpUtil.getProductOptionListUrl(mId));

        if (BuildConfig.DEBUG) {

            Product data = new Product();
            ArrayList listdata = new ArrayList();
            ProductLevels levels = null;
            for (int i = 0; i < 3; i++) {
                levels = new ProductLevels();

                levels.setLevel_id((i + 1) + "");
                levels.setTitle("我是标题：type为" + i);
                levels.setType((i + 1) + "");

                ArrayList list = new ArrayList();

                for (int j = 0; j < 2; j++) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, view, view.getTransitionName());
            act.startActivity(intent, options.toBundle());
        } else {

            act.startActivity(intent);
        }
    }

}
