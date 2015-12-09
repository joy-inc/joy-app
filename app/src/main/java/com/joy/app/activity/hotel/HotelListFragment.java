package com.joy.app.activity.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.LogMgr;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.hotel.HotelListAdapter;
import com.joy.app.adapter.sample.CityDetailRvAdapter;
import com.joy.app.bean.hotel.HotelEntity;
import com.joy.app.bean.hotel.HotelList;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.http.HotelHtpUtil;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 酒店列表    <br>
 */
public class HotelListFragment extends BaseHttpRvFragment<HotelList> implements OnItemViewClickListener{
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
        HotelListAdapter adapter = new HotelListAdapter(getActivity());
        adapter.setHeaderCount(1);
        adapter.setOnItemViewClickListener(this);
        setAdapter(adapter);
    }

    public void reLoadHotelList(HotelParams hotelParams){
        params = hotelParams;
        getAdapter().getData().clear();
        getAdapter().notifyDataSetChanged();
        executeRefreshOnly();
    }

    @Override
    public void onItemViewClick(int position, View clickView, Object o) {
        LogMgr.i("position:"+position);
        if (position != 0 ){
            HotelEntity hotelEntity = (HotelEntity) getAdapter().getData().get(position -1);
            WebViewActivity.startActivity(getActivity(),hotelEntity.getLink(),"hotel");
        }else{
            SearchHotelActivity.startActivity(getActivity(),params.getCityId(),params.getCheckIn(),params.getCheckOut(),params.getFrom_key());
        }
    }

    @Override
    protected List<?> getListInvalidateContent(HotelList hotelList) {
        return hotelList.getHotel();
    }

    @Override
    protected ObjectRequest<HotelList> getObjectRequest(int pageIndex, int pageLimit) {
        return HotelHtpUtil.getHotelListRequest(params,pageIndex,pageLimit,HotelList.class);
    }
}