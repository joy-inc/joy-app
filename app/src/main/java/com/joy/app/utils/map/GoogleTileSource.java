package com.joy.app.utils.map;

import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;

/**
 * @author litong  <br>
 * @Description google地图瓦片    <br>
 */
public class GoogleTileSource extends OnlineTileSourceBase {

    public static GoogleTileSource getDefultGoogleTileSource(){
        String MapUrl[] = new String[]{
                "http://mt0.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&s=Gali&z=%d&x=%d&y=%d",
                "http://mt1.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&s=Gali&z=%d&x=%d&y=%d",
                "http://mt2.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&s=Gali&z=%d&x=%d&y=%d",
                "http://mt3.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&s=Gali&z=%d&x=%d&y=%d"};
        //                          文件名 ，最小，最大，分辨率，后缀，url数组
        //google url 不能带着后缀
        //使用四个是为了均横负载，getBaseUrl的时候会随机选一个
        return new GoogleTileSource("GoogleMap", 0, 22, 256, "", MapUrl);
    }

    public GoogleTileSource(String aName, int aZoomMinLevel, int aZoomMaxLevel, int aTileSizePixels, String aImageFilenameEnding, String[] aBaseUrl) {
        super(aName, aZoomMinLevel, aZoomMaxLevel, aTileSizePixels, aImageFilenameEnding, aBaseUrl);
    }

    @Override
    public String getTileURLString(MapTile mapTile) {
        return String.format(this.getBaseUrl(), mapTile.getZoomLevel(),mapTile.getX() , mapTile.getY());
    }

    @Override
    public String getTileRelativeFilenameString(MapTile tile) {
        //缓存时候保存的文件名，最后框架会再加上.tile的后缀作为文件名
        return super.getTileRelativeFilenameString(tile);
    }
}
