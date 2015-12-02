package com.joy.app.utils.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import org.osmdroid.ResourceProxy;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.DirectedLocationOverlay;

public class OsmDirectOverlay extends DirectedLocationOverlay {

	public OsmDirectOverlay(Context ctx, ResourceProxy pResourceProxy) {
		super(ctx, pResourceProxy);
	}

	@Override
	public void draw(Canvas arg0, MapView arg1, boolean arg2) {

//		if (arg0.getDrawFilter() == null) {
//			PaintFlagsDrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
//			arg0.setDrawFilter(drawFilter);
//		}
		super.draw(arg0, arg1, arg2);
	}
	
}
