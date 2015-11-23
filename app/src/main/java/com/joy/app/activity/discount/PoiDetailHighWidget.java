package com.joy.app.activity.discount;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joy.app.R;
import com.joy.app.bean.sample.PoiDetail;
import com.joy.library.utils.CollectionUtil;
import com.joy.library.utils.TextUtil;
import com.joy.library.view.ExLayoutWidget;

/**
 * 项目亮点
 * <p/>
 * Created by xiaoyu.chen on 15/11/18.
 */
public class PoiDetailHighWidget extends ExLayoutWidget {

    private LinearLayout mTvHighDesc;

    public PoiDetailHighWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = activity.getLayoutInflater().inflate(R.layout.view_discount_poi_detail_high, null);

        mTvHighDesc = (LinearLayout) contentView.findViewById(R.id.tvHighDiv);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null)
            return;

        TextView textView = null;

        for (int i = 0; i < CollectionUtil.size(data.getHighlights()); i++) {

           if (!TextUtil.isEmpty(data.getHighlights().get(i))) {

               textView = new TextView(getActivity());
               textView.setTextColor(getActivity().getResources().getColor(R.color.black_trans54));
               textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
               textView.setText(data.getHighlights().get(i));
               textView.setPadding(0, 15, 0, 15);
               mTvHighDesc.addView(textView);
           }
        }
    }
}
