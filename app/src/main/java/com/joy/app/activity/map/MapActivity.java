package com.joy.app.activity.map;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.CollectionUtil;
import com.android.library.utils.LogMgr;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.activity.poi.PoiDetailActivity;
import com.joy.app.utils.map.GoogleTileSource;
import com.joy.app.utils.map.MapUtil;
import com.joy.app.utils.map.OsmResourceImpl;
import com.joy.app.utils.map.JoyMapOverlayItem;
import com.joy.app.bean.map.MapPoiDetail;
import com.joy.app.utils.QaAnimUtil;

import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.DirectedLocationOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 地图基本功能    <br>
 */
public abstract class MapActivity extends BaseUiActivity implements View.OnClickListener, AMapLocationListener {

    List<MapPoiDetail> data;

    private ResourceProxy mResourceProxy;
    protected MapView mapview;
    private ArrayList<JoyMapOverlayItem> mOverlayItems;
    private DirectedLocationOverlay mLocationOverlay;
    private boolean isLocation = false;
    private AMapLocationClient locationClient = null;
    private JoyMapOverlayItem selectMark;

    @Bind(R.id.maplayout)
    FrameLayout maplayout;

    @Bind(R.id.sdv_photo)
    SimpleDraweeView sdvPhoto;

    @Bind(R.id.jtv_cnname)
    JTextView jtvCnname;

    @Bind(R.id.jtv_enname)
    JTextView jtvEnname;

    @Bind(R.id.iv_path)
    ImageView ivPath;


    @Bind(R.id.poi_map_location_bar)
    RelativeLayout poiMapLocationBar;

    @Bind(R.id.ll_content)
    LinearLayout llContent;

    @Bind(R.id.poi_map_path_btn)
    RelativeLayout poiMapPathBtn;

    boolean isTouchable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        super.initData();
    }

    @Override
    protected void initTitleView() {

        addTitleLeftBackView();
        setTitle("");
    }

    @Override
    protected void initContentView() {
        mResourceProxy = new OsmResourceImpl(this);

        mapview = new MapView(this,mResourceProxy);
        OnlineTileSourceBase GoogleMap = GoogleTileSource.getDefultGoogleTileSource();
        mapview.setTileSource(GoogleMap);
        FrameLayout.LayoutParams mapParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maplayout.addView(mapview, mapParams);
        mapview.setMultiTouchControls(true);
        mapview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isTouchable){
                    return false;
                }
                if (ontouch(event)){
                    v.postDelayed(cleanMarker,500);
                }
                return false;
            }
        });
        ivPath.setOnClickListener(this);
        poiMapPathBtn.setOnClickListener(this);
        poiMapLocationBar.setOnClickListener(this);
        llContent.setOnClickListener(this);
        locationClient = new AMapLocationClient(getApplicationContext());
        MapUtil.initAccuracyLocation(locationClient, this);
    }

    float touchx,touchy;
    private boolean ontouch(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchx = event.getX();
            touchy = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (MapUtil.compareFloatData(touchx, event.getX())
                    && MapUtil.compareFloatData(touchy, event.getY())) {
                return true;
            }
        }
        return false;
    }

    protected void clearCurrMap() {
        if (mOverlayItems == null) {
            mOverlayItems = new ArrayList<>();
        }
        mOverlayItems.clear();
    }

    protected JoyMapOverlayItem addPoi(MapPoiDetail poi) {
        if (poi == null || !poi.isShow())
            return null;
        JoyMapOverlayItem overLayitem = new JoyMapOverlayItem(poi.getmCnName(),
                poi.getmEnName(), new GeoPoint(poi.getLatitude(), poi.getLongitude()));

        overLayitem.setMarker(ContextCompat.getDrawable(this, poi.getIcon_nor()));
        overLayitem.setDataObject(poi);
        mOverlayItems.add(overLayitem);
        return overLayitem;

    }

    protected void addMarkers(List<MapPoiDetail> list) {
        for (MapPoiDetail detail : list) {
            addPoi(detail);
        }
    }

    protected void showMarkers() {
        ItemizedIconOverlay mOverlay = new ItemizedIconOverlay<JoyMapOverlayItem>(mOverlayItems,
                new ItemizedIconOverlay.OnItemGestureListener<JoyMapOverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final JoyMapOverlayItem item) {
                        if (!isTouchable){
                            return false;
                        }

                        selectPosition(index, item);

                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final JoyMapOverlayItem item) {
                        return false;
                    }
                }, mResourceProxy);

        if (!CollectionUtil.isEmpty(mapview.getOverlays())) {
            mapview.getOverlays().clear();
        }
        mapview.getOverlays().add(mOverlay);
        //TODO loaction
    }


    protected void selectPosition(int position, JoyMapOverlayItem item) {
        for (int i = 0; i < mOverlayItems.size(); i++) {
            JoyMapOverlayItem tempitem = mOverlayItems.get(i);
            MapPoiDetail poiDetail = tempitem.getDataObject();
            if (position == i) {
                tempitem.setSelected(true);
                tempitem.setMarker(ContextCompat.getDrawable(this, poiDetail.getIcon_press()));
                IGeoPoint point = tempitem.getPoint();
                if (point != null)
                    mapview.getController().animateTo(point);
            } else {
                tempitem.setSelected(false);
                tempitem.setMarker(ContextCompat.getDrawable(this, poiDetail.getIcon_nor()));
            }
        }

        if (item == null) {
            item = mOverlayItems.get(position);
        }
        selectMark = item;
        showInfoBar();
        updatePoiInfoView(item.getDataObject());
    }

    protected void selectPosition(JoyMapOverlayItem marker,boolean isTouchable){

        marker.setMarker(ContextCompat.getDrawable(this,marker.getDataObject().getIcon_press()));
        selectMark = marker;
        if (isTouchable){
            updatePoiInfoView(marker.getDataObject());
            showInfoBar();
        }else{
            hideInforBar();
        }
        this.isTouchable = isTouchable;
    }

    public void showAllMarker() {

        MapUtil.setMoveCameraAndZoomByOverlay(mOverlayItems, mapview);
    }

    protected void showInfoBar() {
        if (llContent.getVisibility() != View.VISIBLE) {

            llContent.setAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
            llContent.setVisibility(View.VISIBLE);
            poiMapLocationBar.startAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
        }
    }

    public void updatePoiInfoView(MapPoiDetail poiDetail) {
        sdvPhoto.setImageURI(Uri.parse(poiDetail.getmPhotoUrl()));
        jtvCnname.setText(poiDetail.getmCnName());
        jtvEnname.setText(poiDetail.getmEnName());
//        ViewUtil.hideView(ivPath);
    }

    public void hideInforBar() {
        if (llContent.getVisibility() == View.VISIBLE) {
            TranslateAnimation anim = new TranslateAnimation(0f, 0f, 0f, 200f);
            anim.setInterpolator(new AccelerateInterpolator());
            anim.setDuration(300);
            anim.setFillAfter(false);
            anim.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llContent.setVisibility(View.GONE);

                }
            });
            llContent.startAnimation(anim);
        }
    }

    protected void showPathBtn() {
        if (poiMapPathBtn.getVisibility() != View.VISIBLE) {
            poiMapPathBtn.setAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
            poiMapPathBtn.setVisibility(View.VISIBLE);
            poiMapLocationBar.startAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
        }
    }

    private void updateLocation(AMapLocation aMapLocation) {

        double mLatitude = aMapLocation.getLatitude();
        double mLongitude = aMapLocation.getLongitude();
        GeoPoint mLocationPoint = new GeoPoint(mLatitude, mLongitude);

        if (mLocationOverlay == null) {
            mLocationOverlay = new DirectedLocationOverlay(this,mResourceProxy);
            mLocationOverlay.setEnabled(true);
            mLocationOverlay.setLocation(mLocationPoint);
            mapview.getOverlays().add(mLocationOverlay);
            mapview.getController().animateTo(mLocationPoint);
        } else {
            mLocationOverlay.setLocation(mLocationPoint);
        }
    }

    private void startGetLocation() {
        if (isLocation) return;
        isLocation = true;
        locationClient.startLocation();
    }

    private void startPoiDetailActivity() {
        if (selectMark == null || selectMark.getDataObject() == null )return;
        PoiDetailActivity.startActivity(this,selectMark.getDataObject().getmId());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_path:
            case R.id.poi_map_path_btn: // poiMapPathBtn
                MapUtil.startMapApp(this, selectMark.getDataObject());
                return;
            case R.id.poi_map_location_bar: //poiMapLocationBar

                startGetLocation();
                return;
            case R.id.ll_content: //llContent;
                startPoiDetailActivity();
                return;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationClient != null && isLocation) {
            locationClient.startLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    @Override
    protected void onDestroy() {
        if (locationClient != null) {
            locationClient.onDestroy();
            locationClient = null;
        }
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //高德定位回调
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                updateLocation(aMapLocation);
                if (LogMgr.isDebug())
                    MapUtil.showLocationInfor(aMapLocation);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                if (LogMgr.isDebug()) {
                    LogMgr.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    Runnable cleanMarker = new Runnable() {
        @Override
        public void run() {
            selectMark.setMarker(ContextCompat.getDrawable(MapActivity.this,selectMark.getDataObject().getIcon_nor()));
            mapview.invalidate();
            selectMark = null;
            hideInforBar();
        }
    };
}
