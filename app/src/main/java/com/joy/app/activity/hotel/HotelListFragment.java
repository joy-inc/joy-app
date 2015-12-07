package com.joy.app.activity.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.httptask.ObjectRequest;
import com.facebook.cache.disk.DiskStorageCache;
import com.joy.app.adapter.hotel.HotelListAdapter;
import com.joy.app.bean.hotel.HotelList;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.http.HotelHttpUtil;

import java.util.List;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class HotelListFragment extends BaseHttpRvFragment<HotelList> {
    HotelParams params ;

    public static HotelListFragment instantiate(Context context,HotelParams hotelParams) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", hotelParams );
        return (HotelListFragment) Fragment.instantiate(context, HotelListFragment.class.getName(), bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {
        super.initData();
        params = getArguments().getParcelable("data");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        HotelListAdapter adapter = new HotelListAdapter();
        setAdapter(adapter);
    }

    public void reLoadHotelList(HotelParams hotelParams){
        params = hotelParams;
        executeRefreshOnly();
    }

    @Override
    protected List<?> getListInvalidateContent(HotelList hotelList) {
        return hotelList.getHotel();
    }

    @Override
    protected ObjectRequest<HotelList> getObjectRequest(int pageIndex, int pageLimit) {
        return HotelHttpUtil.getHotelListRequest(params,pageIndex,pageLimit,HotelList.class);
    }
}
