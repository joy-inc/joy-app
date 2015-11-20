package com.joy.app.activity.discount;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.library.view.ExLayoutWidget;

/**
 * 项目亮点
 * <p/>
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailHighWidget extends ExLayoutWidget {

    private TextView mTvHighDesc;

    public PoiDetailHighWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_discount_poi_detail_high, null);

//        mTvHighDesc = (TextView) contentView.findViewById(R.id.tvHighDesc);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        String listStr = "aaaaaaaaaaaaaaaaa";

//        for (String a : data.getHighlights()) {
//            listStr += a +"\n";
//        }

//        mTvHighDesc.setText(listStr);

    }
}
