package com.joy.app.activity.poi;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.app.view.FoldTextView;

/**
 * 简介与购买须知
 * <p/>
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailIntroduceWidget extends ExLayoutWidget implements View.OnClickListener {

    private View vRootView;
    private LinearLayout llIntroduceDiv;
    private LinearLayout llKnowDiv;
    private View vLine;
    private FoldTextView ftvIntroduce;
    private TextView tvAllIntroduce;
    private TextView tvAllKnow;

    public PoiDetailIntroduceWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_poi_detail_introduce, null);

        vRootView = contentView;
        llIntroduceDiv = (LinearLayout) contentView.findViewById(R.id.llIntroduceDiv);
        llKnowDiv = (LinearLayout) contentView.findViewById(R.id.llKnowDiv);
        vLine = contentView.findViewById(R.id.vLine);
        ftvIntroduce = (FoldTextView) contentView.findViewById(R.id.ftvIntroduce);
        tvAllIntroduce = (TextView) contentView.findViewById(R.id.tvAllIntroduce);
        tvAllKnow = (TextView) contentView.findViewById(R.id.tvAllKnow);
        tvAllIntroduce.setOnClickListener(this);
        tvAllKnow.setOnClickListener(this);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null || (TextUtil.isEmpty(data.getIntroduction()) && TextUtil.isEmpty(data.getPurchase_info()))) {
            ViewUtil.goneView(vRootView);
            return;
        }

        if (TextUtil.isNotEmpty(data.getIntroduction())) {
            ftvIntroduce.setText(data.getIntroduction());
            ViewUtil.showView(llIntroduceDiv);
        }

        if (TextUtil.isNotEmpty(data.getPurchase_info())) {
            ViewUtil.showView(llKnowDiv);
            if (TextUtil.isNotEmpty(data.getIntroduction()))
                ViewUtil.showView(vLine);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tvAllIntroduce)
            ftvIntroduce.toggle();
        else if (v.getId() == R.id.tvAllKnow)
            callbackWidgetViewClickListener(v);
    }
}
