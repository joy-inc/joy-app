package com.joy.app.activity.poi;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.library.view.ExLayoutWidget;

/**
 * 简介与购买须知
 * <p/>
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailIntroduceWidget extends ExLayoutWidget implements View.OnClickListener {

    TextView tvIntroduce;
    TextView tvAllIntroduce;
    private SimpleDraweeView mSdvKnow;
    TextView tvAllKnow;

    public PoiDetailIntroduceWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_introduce, null);

        tvIntroduce = (TextView) contentView.findViewById(R.id.tvIntroduce);
        tvAllIntroduce = (TextView) contentView.findViewById(R.id.tvAllIntroduce);
        mSdvKnow = (SimpleDraweeView) contentView.findViewById(R.id.sdvKnow);
        tvAllKnow = (TextView) contentView.findViewById(R.id.tvAllKnow);
        tvAllIntroduce.setOnClickListener(this);
        tvAllKnow.setOnClickListener(this);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        tvIntroduce.setText(data.getDescription());
        mSdvKnow.setImageURI(Uri.parse("http://static.qyer.com/models/project/lmDedail/images/img_prebook.jpg"));
    }

    @Override
    public void onClick(View v) {

        callbackWidgetViewClickListener(v);
    }
}
