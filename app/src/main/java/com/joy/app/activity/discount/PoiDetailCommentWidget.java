package com.joy.app.activity.discount;

import android.app.Activity;
import android.view.View;

import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.library.view.ExLayoutWidget;

import java.util.ArrayList;

/**
 * poi详情页的 点评列表部分
 * <p/>
 * Created by xiaoyu.chen on 15/5/7.
 */
public class PoiDetailCommentWidget extends ExLayoutWidget implements View.OnClickListener {

    public PoiDetailCommentWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        initData();

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_discount_poi_detail_comment, null);

        initContentView(contentView);

        return contentView;
    }

    private void initData() {

    }

    private void initContentView(View contentView) {

    }

    protected void invalidate(final ArrayList<String> data) {

        if (data == null)
            return;

        invalidateContent();
    }

    private void invalidateContent() {

    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }

}
