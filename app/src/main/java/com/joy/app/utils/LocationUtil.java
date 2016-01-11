package com.joy.app.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.joy.app.JoyApplication;
import com.joy.app.utils.map.MapUtil;

import java.util.List;

/**
 * @author litong
 * @Description 定位
 * @项目名称: joy-android
 * @创建日期: 16/1/8/上午11:24
 * @文件名称: LocationUtil
 */
public class LocationUtil implements AMapLocationListener {

    private boolean MockEnable = true;
    private boolean WifiActive = true;
    private boolean NeedAddress = true;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private Context mContext;

    public LocationUtil(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 是否允许模拟位置
     * @param mockEnable
     */
    public void setMockEnable(boolean mockEnable) {
        MockEnable = mockEnable;
    }

    /**
     * 是否强制刷新WIFI
     * @param wifiActive
     */
    public void setWifiActive(boolean wifiActive) {
        WifiActive = wifiActive;
    }

    /**
     * 是否返回地址信息
     * @param needAddress
     */
    public void setNeedAddress(boolean needAddress) {
        NeedAddress = needAddress;
    }


    public void resetClientOption(boolean OnceLocation){
        if (locationOption == null){
            locationOption = new AMapLocationClientOption();
        }
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(NeedAddress);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(OnceLocation);
        //设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(WifiActive);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(MockEnable);
    }

    public AMapLocationClient getLocationClient() {
        if (locationClient == null){
            locationClient = new AMapLocationClient(mContext);
            //给定位客户端对象设置定位参数
            locationClient.setLocationOption(locationOption);
        }
        return locationClient;
    }

    public void gettAccuracyLocation(AMapLocationListener listener, long Interval) {

        resetClientOption(false);
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(Interval);
        // 设置定位监听
        getLocationClient().setLocationListener(listener);

    }

    public void gettBatterySavingLocation(AMapLocationListener listener, long Interval) {

        resetClientOption(false);
        // 设置定位模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationOption.setInterval(Interval);
        // 设置定位监听
        getLocationClient().setLocationListener(listener);

    }

    public void getOnceLocation(AMapLocationListener listener) {

        resetClientOption(true);
        // 设置定位模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 设置定位监听
        getLocationClient().setLocationListener(listener);
        getLocationClient().startLocation();
    }
    public void getOnceLocation() {
        getOnceLocation(this);
    }

    public void getLastLocation(AMapLocationListener listener){
        if (getLocationClient().getLastKnownLocation() != null){
            listener.onLocationChanged(getLocationClient().getLastKnownLocation());
        }else{
            getOnceLocation(listener);
        }
    }

    public AMapLocation getLastKnownLocation(){
        return getLocationClient().getLastKnownLocation();
    }

    public void startLocation(){
        if (locationClient != null) {
            locationClient.startLocation();
        }
    }

    public void stopLocation(){
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stopLocation();
        }
    }

    public void removeListener(AMapLocationListener listener){
        stopLocation();
        locationClient.unRegisterLocationListener(listener);
    }

    public void onDestroy() {
        if (locationClient != null) {
            locationClient.onDestroy();
            locationClient = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        MapUtil.showLocationInfor(aMapLocation);
        removeListener(this);
    }
}
