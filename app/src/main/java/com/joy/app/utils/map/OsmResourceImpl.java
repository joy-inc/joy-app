package com.joy.app.utils.map;




import org.osmdroid.DefaultResourceProxyImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.android.library.utils.LogMgr;
import com.joy.app.R;

public class OsmResourceImpl extends DefaultResourceProxyImpl {

	private final Context mContext;

	public OsmResourceImpl(final Context pContext) {
		super(pContext);
		mContext = pContext;
	}

	@Override
	public String getString(final string pResId) {
		try {
			int res;

			res = R.string.class.getDeclaredField(pResId.name()).getInt(null);
			return mContext.getString(res);
		} catch (final Exception e) {
			return super.getString(pResId);
		}
	}

	@Override
	public Bitmap getBitmap(final bitmap pResId) {
		try {
			LogMgr.i("_______Bitmap___bitmap:"+pResId.name()+"____________");
			LogMgr.i("drawble:"+ R.drawable.direction_arrow);
//			if (pResId == bitmap.direction_arrow){
			int res = R.drawable.class.getDeclaredField(pResId.name()).getInt(null);
//			}
			LogMgr.i("OsmResourceImpl"+res);

			return BitmapFactory.decodeResource(mContext.getResources(), res);
		} catch (final Exception e) {
			return super.getBitmap(pResId);
		}
	}

	@Override
	public Drawable getDrawable(final bitmap pResId) {
		try {
			LogMgr.i("_______Drawable___bitmap:"+pResId.name()+"____________");
			LogMgr.i("Drawable:"+ R.drawable.direction_arrow);
			if (pResId == bitmap.direction_arrow){
				int res = R.drawable.class.getDeclaredField(pResId.name()).getInt(null);
				return ContextCompat.getDrawable(mContext,res);
			}
			else{
				return super.getDrawable(pResId);
			}

//			return mContext.getResources().getDrawable(res);
		} catch (final Exception e) {
			return super.getDrawable(pResId);
		}
	}
}
