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
import com.joy.app.view.ExpandTextView;
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
    private ExpandTextView etvIntroduce;
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
        etvIntroduce = (ExpandTextView) contentView.findViewById(R.id.etvIntroduce);
        tvAllIntroduce = (TextView) contentView.findViewById(R.id.tvAllIntroduce);
        tvAllKnow = (TextView) contentView.findViewById(R.id.tvAllKnow);
        tvAllIntroduce.setOnClickListener(this);
        tvAllKnow.setOnClickListener(this);
        ftvIntroduce.setOnClickListener(this);

        return contentView;
    }

    protected void invalidate(final PoiDetail data) {

        if (data == null || (TextUtil.isEmpty(data.getIntroduction()) && TextUtil.isEmpty(data.getPurchase_info()))) {
            ViewUtil.goneView(vRootView);
            return;
        }

        data.setIntroduction("新西兰天然牧场（SPCA）是英联邦国家政府下属机构，也是防止虐待宠物，宠物救助，政府用犬（警犬、缉毒犬、导盲犬）训练的专业机构，天然牧场（SPCA）宠物食品被指定为英联邦政府用犬唯一食品。\n" +
                "天然牧场（SPCA）系列产品做工考究，品质优良，宠物食品更是选用新西兰及澳洲天然牧场放养无任何饲料添加剂的鲜肉为基础材料并配合天然无污染糙米及谷物为辅料加工而成，营养丰富，适口性好，能够充分满足宠物发育以及日常所需营养。\n" +
                "过去二十年，新西兰经济成功地从农业为主，转型为具有国际竞争力的工业化自由市场经济。鹿茸、羊肉、奶制品和粗羊毛的出口值皆为世界第一。新西兰气候宜人、环境清新、风景优美、旅游胜地遍布、森林资源丰富，所以产物都会非常适合宠物消化吸收。");

        if (TextUtil.isNotEmpty(data.getIntroduction())) {
            ftvIntroduce.setText(data.getIntroduction());
            etvIntroduce.setText(data.getIntroduction());
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

        if (v.getId() == R.id.tvAllIntroduce) {
            ftvIntroduce.toggle();
        } else if (v.getId() == R.id.ftvIntroduce) {
            ftvIntroduce.toggle();
        } else if (v.getId() == R.id.tvAllKnow)
            callbackWidgetViewClickListener(v);
    }
}
