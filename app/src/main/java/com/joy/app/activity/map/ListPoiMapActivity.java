package com.joy.app.activity.map;

import android.app.Activity;
import android.content.Intent;

import com.joy.app.bean.map.MapPoiDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author litong  <br>
 * @Description  多点的poi地图   <br>
 */
public class ListPoiMapActivity extends MapActivity {
    List data;
    public static void startActivityByPoiList(Activity activity, ArrayList<MapPoiDetail> list){
        Intent intent = new Intent(activity,ListPoiMapActivity.class);
        intent.putParcelableArrayListExtra("data",list);
        activity.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        data = getIntent().getParcelableArrayListExtra("data");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        clearCurrMap();
        addMarkers(data);
        showMarkers();
        showAllMarker();
    }


}
