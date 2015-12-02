package com.joy.app.activity.map;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.library.activity.BaseUiActivity;
import com.android.library.utils.CollectionUtil;
import com.android.library.widget.JTextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.utils.map.GoogleTileSource;
import com.joy.app.utils.map.OsmResourceImpl;
import com.joy.app.utils.map.QyerMapOverlayItem;
import com.joy.app.bean.map.MapPoiDetail;
import com.joy.app.utils.QaAnimUtil;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.TileSystem;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    protected MapView mapview;
    private ArrayList<QyerMapOverlayItem> mOverlayItems;


    @Bind(R.id.maplayout)
    FrameLayout maplayout;

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

    @Bind(R.id.ll_map_bottom)
    LinearLayout llMapBottom;

    @Bind(R.id.poi_map_path_btn)
    RelativeLayout poiMapPathBtn;

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
    }

    @Override
    protected void initContentView() {
        mResourceProxy = new OsmResourceImpl(this);
        mapview = new MapView(this, new DefaultResourceProxyImpl(this));
        OnlineTileSourceBase GoogleMap = GoogleTileSource.getDefultGoogleTileSource();
        mapview.setTileSource(GoogleMap);
        FrameLayout.LayoutParams mapParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maplayout.addView(mapview, mapParams);
        mapview.setMultiTouchControls(true);

    }

    protected void clearCurrMap() {
        if (mOverlayItems == null) {
            mOverlayItems = new ArrayList<>();
        }
        mOverlayItems.clear();
    }

    private void addRouterLine() {
//		if (mLocationPoint == null || CollectionUtil.isEmpty(mRouteList)){
//			return;
//		}
//        PathOverlay line = new PathOverlay(0xff40c8c8, mAct);
//        if (mLocationPoint != null)
//            line.addPoint(mLocationPoint);
////		mRouteList.add(0, mLocationPoint);
//        for (GeoPoint point : mRouteList){
//            line.addPoint(point);
//        }
//        line.getPaint().setStrokeWidth(7);
//        IMapController mMapController = mOsmv.getController();
//        mMapController.setCenter(mRouteList.get(0));
//        mOsmv.getOverlays().add(line);
//        mOsmv.setMaxZoomLevel(mOsmv.getMaxZoomLevel()-2);//OSM Droid缩放超过18，划线的时候会出错
//		mOsmv.invalidate();
    }

    protected QyerMapOverlayItem addPoi(MapPoiDetail poi) {
        if (poi == null || !poi.isShow())
            return null;
        QyerMapOverlayItem overLayitem = new QyerMapOverlayItem(poi.getmCnName(),
                poi.getmEnName(), new GeoPoint(poi.getLatitude(), poi.getLongitude()));

        overLayitem.setMarker(ContextCompat.getDrawable(this, poi.getIcon_nor()));
        overLayitem.setDataObject(poi);
        mOverlayItems.add(overLayitem);
        return overLayitem;
    }

    protected void addMarkers(List<MapPoiDetail> list){
        for (MapPoiDetail detail : list){
            addPoi(detail);
        }
    }

    protected void showMarkers() {
        ItemizedIconOverlay mOverlay = new ItemizedIconOverlay<QyerMapOverlayItem>(mOverlayItems,
                new ItemizedIconOverlay.OnItemGestureListener<QyerMapOverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final QyerMapOverlayItem item) {

                        selectPosition(index, item);

                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final QyerMapOverlayItem item) {
                        return false;
                    }
                }, mResourceProxy);

        if (!CollectionUtil.isEmpty(mapview.getOverlays())) {
            mapview.getOverlays().clear();
        }
        mapview.getOverlays().add(mOverlay);
        //TODO loaction
    }


    protected void selectPosition(int position, QyerMapOverlayItem item) {
        for (int i = 0; i < mOverlayItems.size(); i++) {
            QyerMapOverlayItem tempitem = mOverlayItems.get(i);
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

        showInfoBar();
        updatePoiInfoView(item.getDataObject());
    }

    public void showAllMarker(){
        setMoveCameraAndZoomByOverlay(mOverlayItems);
    }

    public void setMoveCameraAndZoomByOverlay(List <QyerMapOverlayItem> list){

        ArrayList<Double> latListSort = new ArrayList<>();
        ArrayList<Double> lngListSort = new ArrayList<>();
        for (QyerMapOverlayItem item :list){
            latListSort.add(item.getPoint().getLatitude());
            lngListSort.add(item.getPoint().getLongitude());
        }
        setMoveCameraAndZoom(latListSort,lngListSort);
    }

    public void setMoveCameraAndZoomByPoi(List<MapPoiDetail> list){

        ArrayList<Double> latListSort = new ArrayList<>();
        ArrayList<Double> lngListSort = new ArrayList<>();
        for (MapPoiDetail item :list){
            latListSort.add(item.getLatitude());
            lngListSort.add(item.getLongitude());
        }
        setMoveCameraAndZoom(latListSort,lngListSort);
    }

    protected void setMoveCameraAndZoom( List<Double> latListSort,List<Double> lngListSort){

        Collections.sort(latListSort, new LatComparator());
        Collections.sort(lngListSort, new LatComparator());

        double maxX = latListSort.get(0);
        double minX = latListSort.get(latListSort.size() - 1);
        double maxY = lngListSort.get(0);
        double minY = lngListSort.get(lngListSort.size() - 1);

        double[] middle = new double[] { (maxX + minX) / 2, (maxY + minY) / 2 };

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int mWidth = metrics.widthPixels;
        int mHeight = metrics.heightPixels;

        float xLimit = mWidth / 2 - mWidth * 0.1f;
        float yLimit = mHeight / 2 - mHeight * 0.05f;

        int minLevel = 0;
        for (int i = 18; i >= 1; --i) {
            Point pCenter = TileSystem.LatLongToPixelXY(middle[0], middle[1],
                    i, null);
            Point pMin = TileSystem.LatLongToPixelXY(minX, minY, i, null);
            Point pMax = TileSystem.LatLongToPixelXY(maxX, maxY, i, null);

            if ((Math.abs(pCenter.x - pMin.x) < xLimit && Math.abs(pCenter.y
                    - pMin.y) < yLimit)
                    && (Math.abs(pMax.x - pCenter.x) < xLimit && Math
                    .abs(pMax.y - pCenter.y) < yLimit)) {
                minLevel = i;
                break;
            }
        }

        mapview.getController().setZoom(minLevel);

        mapview.getController().animateTo(new GeoPoint(middle[0], middle[1]));

        latListSort.clear();
        lngListSort.clear();
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

    protected void showPathBtn(){
        if ( poiMapPathBtn.getVisibility() == View.VISIBLE ){
            poiMapPathBtn.setAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
            poiMapPathBtn.setVisibility(View.VISIBLE);
            poiMapLocationBar.startAnimation(QaAnimUtil.getFloatViewBottomSlideInAnim());
        }
    }

    private class LatComparator implements Comparator<Double> {

        @Override
        public int compare(Double lhs, Double rhs) {
            // an integer < 0 if lhs is less than rhs, 0 if they are equal, and
            // > 0 if lhs is greater than rhs.
            if (lhs < rhs) {
                return 1;
            } else if (lhs > rhs) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
