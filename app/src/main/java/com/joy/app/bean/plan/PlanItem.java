package com.joy.app.bean.plan;

import android.graphics.Color;
import android.text.GetChars;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.android.library.utils.LogMgr;
import com.android.library.utils.TextUtil;
import com.joy.app.R;
import com.joy.app.bean.map.MapPoiDetail;

/**
 * @author litong  <br>
 * @Description 旅行规划    <br>
 */
public class PlanItem {
//            "product_id":"zluoqRYxTZY=",
//            "cn_name":"清水寺",
//            "en_name":"qingshuisi",
//            "price":"134.8",
//            "before_day":"3",
//            "pic_url" :"http://xx.com/photo...",
//            "lon" : "23.22",
//            "lat" : "144.33",
    String product_id = TextUtil.TEXT_EMPTY,
        cn_name = TextUtil.TEXT_EMPTY,
        en_name = TextUtil.TEXT_EMPTY,
        before_day = TextUtil.TEXT_EMPTY,
        price = TextUtil.TEXT_EMPTY,
        pic_url = TextUtil.TEXT_EMPTY,
        lon = TextUtil.TEXT_EMPTY,
        lat = TextUtil.TEXT_EMPTY;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCn_name() {
        return cn_name;
    }

    public void setCn_name(String cn_name) {
        this.cn_name = cn_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = TextUtil.filterEmpty(lon,"0");
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = TextUtil.filterEmpty(lat,"0");
    }

    public String getBefore_day() {
        return before_day;
    }
    public Boolean hasBefore_day() {
        try{
            if (TextUtil.isEmpty(before_day)||Integer.parseInt(before_day) == 0){
                return false;
            }
        }catch (NumberFormatException e){
            return true;
        }catch (Exception other ){
            return false;
        }
        return true;
    }

    public void setBefore_day(String before_day) {
        this.before_day = before_day;
    }

    public SpannableString getPrice() {
        String str = "￥"+TextUtil.filterEmpty(price,"0")+" 起";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new RelativeSizeSpan(0.5f),0,1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.75f),str.length()-1,str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#D9f9617c")),0,str.length()-1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    public void setPrice(String price) {
        this.price = TextUtil.filterEmpty(price,"0");
    }

    public boolean isEmptyPrice(){
        if ((price.equals("0"))){
            return true;
        }else{

            return false;
        }
    }

    public MapPoiDetail getMapPoiDetail(){
        if (TextUtil.isEmpty(lon)||TextUtil.isEmpty(lat)||lon.equals("0")||lat.equals("0")){
            return null;
        }
        MapPoiDetail detail = new MapPoiDetail();
        detail.setmCnName(cn_name);
        detail.setmEnName(en_name);
        detail.setLongitude(Double.parseDouble(lon));
        detail.setLatitude(Double.parseDouble(lat));
        detail.setmId(product_id);
        detail.setmPhotoUrl(pic_url);
        detail.setIcon_nor(R.drawable.ic_map_poi);
        detail.setIcon_press(R.drawable.ic_map_poi_pressed);
        if (detail.getLatitude() > 90 || detail.getLatitude() < -90)
            return null;

        return detail;
    }
}
