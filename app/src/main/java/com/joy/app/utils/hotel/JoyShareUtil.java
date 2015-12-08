package com.joy.app.utils.hotel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.library.utils.TextUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

public class JoyShareUtil {


	public static boolean clearCityHistory(Activity activity){
		return clearData(activity,activity.getClass().getSimpleName());
	}

	public static boolean SaveData(Context mContext , String FileName,String DataName,List<String> Entry){
		int currentapiVersion= Build.VERSION.SDK_INT;
		try {			
			SharedPreferences share = mContext.getSharedPreferences(FileName, mContext.MODE_PRIVATE);
			Editor editor = share.edit();
			JSONArray array = new JSONArray();
			for (String str : Entry) {
				array.put(str);
			}
			JSONObject json = new JSONObject();
			json.put(DataName, array);
			editor.putString(DataName, json.toString());
			if(currentapiVersion >= Build.VERSION_CODES.GINGERBREAD){
				
				editor.apply();
			}else{
				
				editor.commit();
			}
		} catch (Exception e) {
			return false;
		}
		return true;	
	}
	
	public static boolean clearData(Context mContext , String FileName){
		int currentapiVersion= Build.VERSION.SDK_INT;
		try {			
			SharedPreferences share = mContext.getSharedPreferences(FileName, mContext.MODE_PRIVATE);
			Editor editor = share.edit();
			editor.clear();
			if(currentapiVersion >= Build.VERSION_CODES.GINGERBREAD){
				editor.apply();
			}else{				
				editor.commit();
			}
		} catch (Exception e) {
			return false;
		}
		return true;	
	}
	
	public static List<String> getData(Context mContext , String FileName,String DataName) {
		SharedPreferences share = mContext.getSharedPreferences(FileName, mContext.MODE_PRIVATE);
		boolean isContain = share.contains(DataName);
		if(!isContain)return new ArrayList<String>();
		else{
			String str = share.getString(DataName, TextUtil.TEXT_EMPTY);
			if(TextUtil.isEmptyTrim(str)){
				return new ArrayList<String>();
			}else{
				try {
					JSONObject object = new JSONObject(str);
					JSONArray array = object.getJSONArray(DataName);
					ArrayList<String> data = new ArrayList<String>();
					for (int i = 0; i < array.length(); i++) {
						data.add(array.getString(i));
					}
					return data;
				} catch (JSONException e) {
					return new ArrayList<String>();
				}
			}
		}
	}
	
	
}
