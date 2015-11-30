package com.joy.app.bean.map;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class MapPoiDetail implements Parcelable {
    String mEnName,mCnName,mPhotoUrl;
    int icon_nor,icon_press;
    double latitude,longitude;



    public String getmEnName() {
        return mEnName;
    }

    public void setmEnName(String mEnName) {
        this.mEnName = mEnName;
    }

    public String getmCnName() {
        return mCnName;
    }

    public void setmCnName(String mCnName) {
        this.mCnName = mCnName;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public int getIcon_nor() {
        return icon_nor;
    }

    public void setIcon_nor(int icon_nor) {
        this.icon_nor = icon_nor;
    }

    public int getIcon_press() {
        return icon_press;
    }

    public void setIcon_press(int icon_press) {
        this.icon_press = icon_press;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mEnName);
        dest.writeString(this.mCnName);
        dest.writeString(this.mPhotoUrl);
        dest.writeInt(this.icon_nor);
        dest.writeInt(this.icon_press);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    public MapPoiDetail() {
    }

    protected MapPoiDetail(Parcel in) {
        this.mEnName = in.readString();
        this.mCnName = in.readString();
        this.mPhotoUrl = in.readString();
        this.icon_nor = in.readInt();
        this.icon_press = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<MapPoiDetail> CREATOR = new Parcelable.Creator<MapPoiDetail>() {
        public MapPoiDetail createFromParcel(Parcel source) {
            return new MapPoiDetail(source);
        }

        public MapPoiDetail[] newArray(int size) {
            return new MapPoiDetail[size];
        }
    };
}
