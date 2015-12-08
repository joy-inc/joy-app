package com.joy.app.bean.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.library.utils.TextUtil;
import com.android.library.utils.TimeUtil;
/**
 * @author litong  <br>
 * @Description 酒店筛选    <br>
 */
public class HotelParams implements Parcelable {

    String cityId;
    String checkIn;//入住时间	2004-02-12
    long checkInMills;//入住时间
    String checkOut;//离开时间	2004-02-13
    long checkOutMills;//离开时间
    String From_key;//aid	booking跟踪来源
    String hotel;//酒店名
    String star_ids;//过滤星级
    String price_rangs;//价格区间	逗号分割高低价格
    String facilities_ids;//过滤标签
    int Orderby;//排序方式 默认智能排序
    int area_id;//区域id
    String[] price;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckIn(long checkIn) {
        checkInMills = checkIn;
        this.checkIn = TimeUtil.getSimpleTime(checkIn);
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setCheckOut(long checkOut) {
        checkOutMills = checkOut;
        this.checkOut = TimeUtil.getSimpleTime(checkOut);
    }

    public String getFrom_key() {
        return From_key;
    }

    public void setFrom_key(String from_key) {
        From_key = from_key;
    }

    public void setOrderby(int orderby) {
        Orderby = orderby;
    }

    public int getOrderby() {
        return Orderby;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getHotel() {
        return hotel;
    }

    public boolean setHotel(String hotel) {
        if (TextUtil.isNotEmpty(this.hotel) && this.hotel.equals(hotel))return false;
        this.hotel = hotel;
        return true;
    }

    public String getStar_ids() {
        return star_ids;
    }

    public void setStar_ids(String star_ids) {
        this.star_ids = star_ids;
    }

    public String getPrice_rangs() {
        return price_rangs;
    }

    public void setPrice_rangs(String price_rangs) {
        this.price_rangs = price_rangs;
    }
    public void setPrice_rangs(String[] price_rangs) {
        price = price_rangs;
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : price_rangs){
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        this.price_rangs = stringBuilder.toString().substring(0, stringBuilder.length()-1);
    }

    public String[] getPrice() {
        if (price == null){
            price = new String[]{"0","不限"};
        }
        return price;
    }

    public String getFacilities_ids() {
        return facilities_ids;
    }

    public void setFacilities_ids(String facilities_ids) {
        this.facilities_ids = facilities_ids;
    }

    public long getCheckInMills() {
        return checkInMills;
    }

    public void setCheckInMills(long checkInMills) {
        this.checkInMills = checkInMills;
        this.checkIn = TimeUtil.getSimpleTime(checkInMills);
    }

    public long getCheckOutMills() {
        return checkOutMills;

    }

    public void setCheckOutMills(long checkOutMills) {
        this.checkOutMills = checkOutMills;
        this.checkOut = TimeUtil.getSimpleTime(checkOutMills);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cityId);
        dest.writeString(this.checkIn);
        dest.writeLong(this.checkInMills);
        dest.writeString(this.checkOut);
        dest.writeLong(this.checkOutMills);
        dest.writeString(this.From_key);
        dest.writeString(this.hotel);
        dest.writeString(this.star_ids);
        dest.writeString(this.price_rangs);
        dest.writeString(this.facilities_ids);
        dest.writeInt(this.Orderby);
        dest.writeInt(this.area_id);
    }

    public HotelParams() {
    }

    protected HotelParams(Parcel in) {
        this.cityId = in.readString();
        this.checkIn = in.readString();
        this.checkInMills = in.readLong();
        this.checkOut = in.readString();
        this.checkOutMills = in.readLong();
        this.From_key = in.readString();
        this.hotel = in.readString();
        this.star_ids = in.readString();
        this.price_rangs = in.readString();
        this.facilities_ids = in.readString();
        this.Orderby = in.readInt();
        this.area_id = in.readInt();
    }

    public static final Creator<HotelParams> CREATOR = new Creator<HotelParams>() {
        public HotelParams createFromParcel(Parcel source) {
            return new HotelParams(source);
        }

        public HotelParams[] newArray(int size) {
            return new HotelParams[size];
        }
    };
}
