package com.joy.app.activity.map;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.joy.app.activity.map.osmutil.GoogleTileSource;
import com.joy.app.activity.map.osmutil.QyerMapOverlayItem;
import com.joy.app.bean.map.MapPoiDetail;
import com.joy.app.utils.QaAnimUtil;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;

import java.util.ArrayList;
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

    protected void showMap() {
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
}
