package com.joy.app.activity.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.LogMgr;
import com.joy.app.activity.common.WebViewActivity;
import com.joy.app.adapter.hotel.HotelListAdapter;
import com.joy.app.adapter.hotel.SearchHotelListAdapter;
import com.joy.app.bean.hotel.HotelEntity;
import com.joy.app.bean.hotel.HotelList;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.http.HotelHtpUtil;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 酒店列表    <br>
 */
public class SearchHotelListFragment extends BaseHttpRvFragment<HotelList> implements OnItemViewClickListener{
    HotelParams params ;

    public static SearchHotelListFragment instantiate(Context context, HotelParams hotelParams) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", hotelParams );
        return (SearchHotelListFragment) Fragment.instantiate(context, SearchHotelListFragment.class.getName(), bundle);
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
        SearchHotelListAdapter adapter = new SearchHotelListAdapter();
        adapter.setOnItemViewClickListener(this);
        setAdapter(adapter);
    }

    public void reLoadHotelList(String keyword){
        if (params.setHotel(keyword)){
            getAdapter().getData().clear();
            getAdapter().notifyDataSetChanged();
            executeRefreshOnly();
        }
    }

    @Override
    public void onItemViewClick(int position, View clickView, Object o) {
        if (position != 0 ){
            HotelEntity hotelEntity = (HotelEntity) getAdapter().getData().get(position -1);
            WebViewActivity.startActivity(getActivity(),hotelEntity.getLink(),"");
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
