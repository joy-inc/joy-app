package com.joy.app.activity.map;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.joy.library.utils.ViewUtil;
import com.joy.library.view.ExLayoutWidget;

/**
 * @author litong  <br>
 * @Description 静态图    <br>
 */
public class StaticMapWidget extends ExLayoutWidget {

    View location;
    LinearLayout TileLayout;
    SimpleDraweeView map,Lmap,Rmap;
    SimpleDraweeView LTmap,CTmap,RTmap;
    SimpleDraweeView LBmap,CBmap,RBmap;
    int zoom = 0;


    public StaticMapWidget(Activity activity) {
        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        TileLayout = new LinearLayout(activity);
        RelativeLayout contentView = new RelativeLayout(activity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        contentView.addView(TileLayout,params);
//        View contentView = activity.getLayoutInflater().inflate(R.layout.view_static_map, null);
//        map = (SimpleDraweeView)contentView.findViewById(R.id.aiv_map);
//        Lmap = (SimpleDraweeView)contentView.findViewById(R.id.aiv_map_left);
//        Rmap = (SimpleDraweeView)contentView.findViewById(R.id.aiv_map_right);
//        location = contentView.findViewById(R.id.tv_location);
//        ViewUtil.goneView(location);
        return contentView;
    }

    public void invalidate(Location location){

        double lng = location.getLongitude();
        double lat = location.getLatitude();
        invalidate(lng, lat, 18);
    }
    private void initView(){
        int width = TileLayout.getWidth();
        int height = TileLayout.getHeight();
        if (width>0 && height>0){
            int div =  width/height;
            if (height > 400){//9张图

            }else{//3三张图

            }

        }
    }
    

    public void invalidate( double lng, double lat,int Zoom){
        zoom = Zoom;
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(maplistener)
                .setUri(Uri.parse(toTile(lng, lat))).build();
        map.setController(controller);
        Lmap.setImageURI(Uri.parse(getLeftPath(X, Y, zoom)));
        Rmap.setImageURI(Uri.parse(getRightPath(X, Y, zoom)));
    }
    int X,Y;

    //将lnglat地理坐标系转换为tile瓦片坐标系
    private String toTile(double lng,double lat) {
        double n = Math.pow(2, zoom);
        double tileX = ((lng + 180.0) / 360.0) * n;
        double tileY = (1 - (Math.log(Math.tan(Math.toRadians(lat)) + (1 / Math.cos(Math.toRadians(lat)))) / Math.PI)) / 2 * n;
        X = new Double(tileX).intValue();
        Y = new Double(tileY).intValue();
        return getPath(X, Y, zoom);
    }



    private String getPath(int x,int y,int zoom){
        return "http://mt2.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&z="+zoom+"&x="+x+"&y="+y+"&s=Gali";
    }
    private String getLeftPath(int x,int y,int zoom){
        return "http://mt2.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&z="+zoom+"&x="+(x-1)+"&y="+y+"&s=Gali";
    }
    private String getRightPath(int x,int y,int zoom){
        return "http://mt2.google.cn/vt/lyrs=m@285000000&hl=zh-CN&gl=CN&src=app&expIds=201527&rlbl=1&z="+zoom+"&x="+(x+1)+"&y="+y+"&s=Gali";
    }

    ControllerListener maplistener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onSubmit(String s, Object o) {

        }

        @Override
        public void onFinalImageSet(String s, ImageInfo o, Animatable animatable) {
            ViewUtil.showView(location);
        }

        @Override
        public void onIntermediateImageSet(String s, ImageInfo o) {

        }

        @Override
        public void onIntermediateImageFailed(String s, Throwable throwable) {

        }

        @Override
        public void onFailure(String s, Throwable throwable) {

        }

        @Override
        public void onRelease(String s) {

        }
    };
}
