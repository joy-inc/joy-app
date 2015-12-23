package com.joy.app.activity.hotel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.DensityUtil;
import com.android.library.utils.DeviceUtil;
import com.android.library.utils.ToastUtil;
import com.android.library.utils.ViewUtil;
import com.joy.app.R;
import com.joy.app.bean.hotel.FilterItems;
import com.joy.app.view.hotel.AutoChangeLineViewGroup;
import com.joy.app.view.hotel.SeekBarPressure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 酒店列表页条件筛选页面
 *
 * @author xinghai.qi on 2015/4/15.
 */
public class HotelSearchFilterActivity extends Activity implements View.OnClickListener {

    public static final String EX_KEY_HOTEL_FACILITIES_TYPE = "ex_hotel_facilities_types";
    public static final String EX_KEY_HOTEL_PRICES_TYPE = "ex_hotel_prices_types";
    public static final String EX_KEY_HOTEL__FACILITIES_TYPE_STR = "ex_hotel_facilities_types_str";
    public static final String EX_KEY_HOTEL_STAR_TYPE_STR = "ex_hotel_star_types_str";
    public static final String EX_KEY_HOTEL_STAR_TYPE = "ex_hotel_star_types";

    private SeekBarPressure mSbDistance;

    private Integer mCheckedNum = 0;
    private List<CheckBox> mListCb = new ArrayList<>();


    private TextView tvClear;
    private List<FilterItems> mTypesData;
    private List<String> mTypeList = new ArrayList<>();

    //酒店星级集合
    private List<CheckBox> mStarListCb = new ArrayList<>();
    private List<FilterItems> mStarTypes;
    private List<String> mStarTypeList = new ArrayList<>();
    private int mCheckedStarNum = 0;

    private String[] mPrice = new String[2];


    boolean isDisable = false;
    private int mThemeColor;
    private int mThemeSelector;
    private int mThemeLeftSelector;
    private int mThemeRightSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_hotel_list_filter);
        initData();
        initContentView();
    }


    protected void initData() {

        mTypesData = (List<FilterItems>) getIntent().getSerializableExtra(EX_KEY_HOTEL_FACILITIES_TYPE);
        mStarTypes = (List<FilterItems>) getIntent().getSerializableExtra(EX_KEY_HOTEL_STAR_TYPE);
        mPrice = getIntent().getStringArrayExtra(EX_KEY_HOTEL_PRICES_TYPE);
        String typeStr = getIntent().getStringExtra(EX_KEY_HOTEL__FACILITIES_TYPE_STR);
        String starTypeStr = getIntent().getStringExtra(EX_KEY_HOTEL_STAR_TYPE_STR);
        initTheme();
        if (typeStr != null && typeStr.length() > 0) {

            String[] typs = typeStr.split(",");
            mTypeList.clear();
            for (int i = 0; i < typs.length; i++) {
                mTypeList.add(typs[i]);
            }
            mCheckedNum = mTypeList.size();
        }

        if (starTypeStr != null && starTypeStr.length() > 0) {

            String[] typs = starTypeStr.split(",");
            mStarTypeList.clear();
            for (int i = 0; i < typs.length; i++) {
                mStarTypeList.add(typs[i]);
            }
            mCheckedStarNum = mStarTypeList.size();
        }
    }

    private void initTheme() {

        mThemeColor = R.color.white;
        mThemeSelector = R.drawable.selector_bg_hotel_list_filter;
        mThemeLeftSelector = R.drawable.selector_bg_hotel_list_left_filter;
        mThemeRightSelector = R.drawable.selector_bg_hotel_list_right_filter;

    }

    protected void initContentView() {
        int width = DeviceUtil.getScreenWidth();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, DeviceUtil.getScreenHeight()-DensityUtil.dip2px(20));
        LinearLayout llRoot = (LinearLayout) findViewById(R.id.llFilterRoot);
        llRoot.setLayoutParams(params);

        TextView tvDone = (TextView) findViewById(R.id.tvDone);
        tvDone.setTextColor(getResources().getColorStateList(mThemeColor));
        tvDone.setOnClickListener(this);
        tvClear = (TextView) findViewById(R.id.tvClear);
        tvClear.setOnClickListener(this);
        initStarFilterView();
        initSeekBar();
        initFilterView();
        changeCheckBoxState();
        findViewById(R.id.v_shadow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void initStarFilterView() {

        if (CollectionUtil.isEmpty(mStarTypes)) {

            ViewUtil.goneView(findViewById(R.id.tvStar));
            return;
        }

        LinearLayout llStarsView = (LinearLayout) findViewById(R.id.ll_stars);

        int i= 0;
        for (FilterItems types : mStarTypes) {

            String name = types.getName();
            CheckBox filterCB = initCheckBoxForStar(name);
            i++;
            if (i== 1){
                filterCB.setBackgroundResource(mThemeLeftSelector);
            }
            if (i== mStarTypes.size()){
                filterCB.setBackgroundResource(mThemeRightSelector);
            }
            mStarListCb.add(filterCB);
            if (mStarTypeList.contains(types.getName()))
                filterCB.setChecked(true);
            filterCB.setTag(types.getName());
            llStarsView.addView(filterCB);
            llStarsView.addView(getSpaceView());
        }
    }


    private void initSeekBar() {
        int width = DeviceUtil.getScreenWidth()-DensityUtil.dip2px(52);
        mSbDistance = (SeekBarPressure) findViewById(R.id.sbDistance);
        mSbDistance.setmScollBarWidth(width);
        mSbDistance.setProgressLowInt(getProgress(mPrice[0]));
        int highProgress = getProgress(mPrice[1]);
        if (highProgress == 0) {
            highProgress = 5;
        }
        mSbDistance.setProgressHighInt(highProgress);
        mSbDistance.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBarPressure seekBar,
                                          double progressLow, double progressHigh, int mprogressLow,
                                          int mprogressHigh, double max, double min) {
                mPrice[0] = getPrice(mprogressLow);
                mPrice[1] = getPrice(mprogressHigh);
                changeClearView();
            }
        });
    }

    private void initFilterView() {

        if (CollectionUtil.isEmpty(mTypesData)) {

            ViewUtil.goneView(findViewById(R.id.tvFacility));
            return;
        }

        AutoChangeLineViewGroup mLlFilterView = (AutoChangeLineViewGroup) findViewById(R.id.llFilterView);

        for (FilterItems types : mTypesData) {

            String name = types.getName();
            CheckBox filterCB = initCheckBox(name);
            mListCb.add(filterCB);
            if (mTypeList.contains(types.getName()))
                filterCB.setChecked(true);
            filterCB.setTag(types.getName());
            mLlFilterView.addView(filterCB);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvDone://确认

                String typeStr = "";
                for (int i = 0; i < mTypeList.size(); i++) {
                    if (i == 0)
                        typeStr = mTypeList.get(i);
                    else
                        typeStr += "," + mTypeList.get(i);
                }

                String starsStr = "";
                for (int i = 0; i < mStarTypeList.size(); i++) {
                    if (i == 0)
                        starsStr = mStarTypeList.get(i);
                    else
                        starsStr += "," + mStarTypeList.get(i);
                }

                Intent intent = new Intent();
                intent.putExtra(HotelSearchFilterActivity.EX_KEY_HOTEL__FACILITIES_TYPE_STR, typeStr);
                intent.putExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_PRICES_TYPE, mPrice);
                intent.putExtra(HotelSearchFilterActivity.EX_KEY_HOTEL_STAR_TYPE_STR, starsStr);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.tvClear://清空

                if (!CollectionUtil.isEmpty(mListCb)) {
                    for (CheckBox c : mListCb) {
                        c.setChecked(false);
                    }
                }

                if (!CollectionUtil.isEmpty(mStarListCb)) {
                    for (CheckBox c : mStarListCb) {
                        c.setChecked(false);
                    }
                }

                if (!(0 == getProgress(mPrice[0])) || !(5 == getProgress(mPrice[1]))) {
                    mSbDistance.setProgressLowInt(0);
                    mSbDistance.setProgressHighInt(5);

                    mSbDistance.invalidate();
                }
                break;

            default:
                break;
        }

    }

    private ImageView getSpaceView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(params);
        imageView.setBackgroundColor(Color.WHITE);

        return imageView;
    }

    private CheckBox initCheckBoxForStar(String text) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(36.0f));
        params.weight=1;
        params.setMargins(DensityUtil.dip2px(2),0,DensityUtil.dip2px(2),0);
        CheckBox FilterCheckBox = new CheckBox(this);
        FilterCheckBox.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
        FilterCheckBox.setText(text);
        FilterCheckBox.setGravity(Gravity.CENTER);
        FilterCheckBox.setBackgroundResource(mThemeSelector);
        FilterCheckBox.setTextColor(getResources().getColorStateList(R.color.selector_font_poilist_filter));
        FilterCheckBox.setSingleLine();
        FilterCheckBox.setEllipsize(TextUtils.TruncateAt.END);//容错
        FilterCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f);
        FilterCheckBox.setPadding(DensityUtil.dip2px(15.0f), DensityUtil.dip2px(3.0f), DensityUtil.dip2px(15.0f), DensityUtil.dip2px(3.0f));
        FilterCheckBox.setLayoutParams(params);
        FilterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String tag = (String) buttonView.getTag();
                if (tag == null)
                    return;
                if (mStarTypeList.contains(tag) && !isChecked) {
                    mStarTypeList.remove(tag);
                    mCheckedStarNum--;
                }
                if (!mStarTypeList.contains(tag) && isChecked) {

                    if (mCheckedStarNum == 5) {
                        buttonView.setChecked(false);
                        ToastUtil.showToast(R.string.more_then_5);
                    }
                    if (mCheckedStarNum <= 4) {
                        mStarTypeList.add(tag);
                        mCheckedStarNum++;
                    }
                }
                changeCheckBoxState();
            }
        });

        return FilterCheckBox;
    }


    /**
     * 初始化类型选项控件
     *
     * @param text text
     * @return poi类型
     */
    private CheckBox initCheckBox(String text) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(30.0f));
        CheckBox FilterCheckBox = new CheckBox(this);
        FilterCheckBox.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
        FilterCheckBox.setText(text);
        FilterCheckBox.setBackgroundResource(R.drawable.selector_bg_hotel_list_tag_filter);
        FilterCheckBox.setTextColor(getResources().getColorStateList(R.color.selector_font_poilist_filter));
        FilterCheckBox.setSingleLine();
        FilterCheckBox.setEllipsize(TextUtils.TruncateAt.END);//容错
        FilterCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f);
        FilterCheckBox.setPadding(DensityUtil.dip2px(15.0f), DensityUtil.dip2px(3.0f), DensityUtil.dip2px(15.0f), DensityUtil.dip2px(3.0f));
        FilterCheckBox.setLayoutParams(params);
        FilterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String tag = (String) buttonView.getTag();
                if (tag == null)
                    return;
                if (mTypeList.contains(tag) && !isChecked) {
                    mTypeList.remove(tag);
                    mCheckedNum--;
                }
                if (!mTypeList.contains(tag) && isChecked) {

                    if (mCheckedNum == 5) {
                        buttonView.setChecked(false);
                        ToastUtil.showToast(R.string.more_then_5);
                    }
                    if (mCheckedNum <= 4) {
                        mTypeList.add(tag);
                        mCheckedNum++;
                    }
                }
                changeCheckBoxState();

            }
        });

        return FilterCheckBox;
    }

    /**
     * 当选中的超过5项，其他类型控件不可点击；少于时，开启点击
     */
    private void changeCheckBoxState() {

        if (mCheckedNum >= 5) {
            isDisable = true;
            for (int i = 0; i < mListCb.size(); i++) {
                if (!mTypeList.contains(mListCb.get(i).getTag())) {
                    mListCb.get(i).setBackgroundResource(R.drawable.shape_bg_poilist_filter_tag_disable);
                    mListCb.get(i).setTextColor(Color.parseColor("#cfcfcf"));
                }
            }
        } else if (isDisable) {
            isDisable = false;
            for (int i = 0; i < mListCb.size(); i++) {
                if (!mTypeList.contains(mListCb.get(i).getTag())) {
                    mListCb.get(i).setBackgroundResource(R.drawable.selector_bg_hotel_list_tag_filter);
                    mListCb.get(i).setTextColor(getResources().getColorStateList(R.color.selector_font_poilist_filter));
                }
            }
        }
        changeClearView();
    }

    private void changeClearView() {

//        if (0 == getProgress(mPrice[0]) && 5 == getProgress(mPrice[1]) && mCheckedNum == 0 && mCheckedStarNum == 0) {
//            tvClear.setTextColor(getResources().getColorStateList(R.color.white));
//        } else {
//            tvClear.setTextColor(getResources().getColorStateList(mThemeColor));
//        }
    }


    //距离转换为progressbar的数值
    private int getProgress(String distance) {

        if ("不限".equals(distance)) {

            return 5;
        } else if ("1000".equals(distance)) {

            return 4;
        } else if ("800".equals(distance)) {

            return 3;
        } else if ("500".equals(distance)) {

            return 2;
        } else if ("300".equals(distance)) {

            return 1;
        } else if ("0".equals(distance)) {

            return 0;
        }
        return 0;
    }

    //progressbar数值转换为距离
    private String getPrice(int progress) {

        switch (progress) {
            case 0:
                return "0";
            case 1:
                return "300";
            case 2:
                return "500";
            case 3:
                return "800";
            case 4:
                return "1000";
            case 5:
                return "不限";

            default:
                return "";
        }
    }

//    @Override
//    public void finish() {
//        overridePendingTransition(R.anim.anim_bottom_enter, R.anim.anim_bottom_finish);
//        super.finish();
//    }

    /**
     * 开启酒店条件筛选页面
     *
     * @param activity
     * @param requestCode
     * @param facilitiesTypes 设施的条件集合
     * @param starTypes       星级的条件集合
     * @param facTypesStr     已经选择的设施，中间用逗号分隔
     * @param starTypeStr     已经选择的星级，中间用逗号分隔
     * @param priceStrs       已经选择的价格区间
     */
    public static void startActivityForResult(Activity activity, int requestCode, List<FilterItems> facilitiesTypes,
                                              List<FilterItems> starTypes, String facTypesStr, String starTypeStr,
                                              String[] priceStrs) {

        Intent intent = new Intent(activity, HotelSearchFilterActivity.class);
        intent.putExtra(EX_KEY_HOTEL_FACILITIES_TYPE, (Serializable) facilitiesTypes);
        intent.putExtra(EX_KEY_HOTEL_STAR_TYPE, (Serializable) starTypes);
        intent.putExtra(EX_KEY_HOTEL__FACILITIES_TYPE_STR, facTypesStr);
        intent.putExtra(EX_KEY_HOTEL_STAR_TYPE_STR, starTypeStr);
        intent.putExtra(EX_KEY_HOTEL_PRICES_TYPE, priceStrs);
//        activity.overridePendingTransition(R.anim.anim_bottom_enter, R.anim.anim_bottom_finish);
        activity.startActivityForResult(intent, requestCode);

    }
}
