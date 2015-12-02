package com.joy.app.utils.map;

import com.joy.app.bean.map.MapPoiDetail;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class QyerMapOverlayItem extends OverlayItem {
    MapPoiDetail dataObject;//数据
    boolean isSelected = false;//是否选中

    public QyerMapOverlayItem(String aTitle, String aSnippet, IGeoPoint aGeoPoint) {
        super(aTitle, aSnippet, aGeoPoint);
    }

    public QyerMapOverlayItem(String aUid, String aTitle, String aDescription, IGeoPoint aGeoPoint) {
        super(aUid, aTitle, aDescription, aGeoPoint);
    }

    public MapPoiDetail getDataObject() {
        return dataObject;
    }

    public void setDataObject(MapPoiDetail dataObject) {
        this.dataObject = dataObject;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
