package com.joy.app.activity.map;

import android.app.Activity;
import android.content.Intent;

import com.joy.app.R;
import com.joy.app.utils.map.QyerMapOverlayItem;
import com.joy.app.bean.map.MapPoiDetail;
import com.joy.app.bean.sample.PoiDetail;

/**
 * @author litong  <br>
 * @Description 单点地图    <br>
 */
public class SinglePoiMapActivity extends MapActivity {
    MapPoiDetail mapPoiDetail;

    public static void startActivityByPoiDetail(Activity activity, PoiDetail poiDetail){
        Intent intent = new Intent(activity,SinglePoiMapActivity.class);
        intent.putExtra("PoiDetail",poiDetail);
        activity.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        PoiDetail poi = getIntent().getParcelableExtra("PoiDetail");
        if (poi != null ){
            mapPoiDetail = new MapPoiDetail();
            mapPoiDetail.setIcon_nor(R.drawable.ic_map_poi);
            mapPoiDetail.setIcon_press(R.drawable.ic_map_poi_pressed);
            mapPoiDetail.setmCnName(poi.getTitle());
            mapPoiDetail.setLatitude(Double.parseDouble(poi.getLat()));
            mapPoiDetail.setLongitude(Double.parseDouble(poi.getLon()));
        }
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        clearCurrMap();
        QyerMapOverlayItem item = addPoi(mapPoiDetail);
        showMarkers();
        mapview.getController().setCenter(item.getPoint());
        mapview.getController().setZoom(15);
        showPathBtn();
    }
}
