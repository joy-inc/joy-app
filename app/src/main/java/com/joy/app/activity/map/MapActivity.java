package com.joy.app.activity.map;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.library.activity.BaseUiActivity;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.map.MapPoiDetail;

import org.osmdroid.ResourceProxy;
import org.osmdroid.views.MapView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 地图基本功能    <br>
 */
public abstract class MapActivity extends BaseUiActivity {

    List<MapPoiDetail> data;
    private ResourceProxy mResourceProxy;

    @Bind(R.id.mapview)
    MapView mapview;
    @Bind(R.id.location_progress)
    ProgressBar locationProgress;
    @Bind(R.id.poi_map_iv_my_location)
    ImageView poiMapIvMyLocation;
    @Bind(R.id.poi_map_location_bar)
    RelativeLayout poiMapLocationBar;
    @Bind(R.id.sdv_photo)
    SimpleDraweeView sdvPhoto;
    @Bind(R.id.jtv_cnname)
    JTextView jtvCnname;
    @Bind(R.id.jtv_enname)
    JTextView jtvEnname;
    @Bind(R.id.iv_path)
    ImageView ivPath;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.ll_map_infor)
    LinearLayout llMapInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleView() {
        addTitleLeftBackView();
    }

    @Override
    protected void initContentView() {
        mResourceProxy = new OsmResourceImpl.java(act);
    }
}
