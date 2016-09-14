package com.joy.app.activity.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.library.httptask.ObjectRequest;
import com.android.library.listener.OnItemClickListener;
import com.android.library.ui.fragment.BaseHttpRvFragment;
import com.android.library.ui.webview.BaseWebViewActivityNoTitle;
import com.joy.app.R;
import com.joy.app.adapter.hotel.HotelListAdapter;
import com.joy.app.bean.hotel.HotelEntity;
import com.joy.app.bean.hotel.HotelList;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.http.HotelHtpUtil;

import java.util.List;

/**
 * @author litong  <br>
 * @Description 酒店列表    <br>
 */
public class HotelListFragment extends BaseHttpRvFragment<HotelList> implements OnItemClickListener<HotelEntity>, View.OnClickListener {
    HotelParams params;
    RecyclerView.OnScrollListener listener;

    public static HotelListFragment instantiate(Context context, HotelParams hotelParams) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", hotelParams);
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
        adapter.setOnItemClickListener(this);
        setAdapter(adapter);
        View v = View.inflate(getActivity(), R.layout.item_hotel_header, null);
        v.setOnClickListener(this);
        addHeaderView(v);
//        View view = new View(getActivity());
//        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(55)));
//        addFooterView(view);
        if (listener != null){
            getRecyclerView().addOnScrollListener(listener);
        }
    }

    public void reLoadHotelList(HotelParams hotelParams) {
        params = hotelParams;
        executeFrameRefresh();
    }

    @Override
    public void onItemClick(int position, View clickView, HotelEntity hotelEntity) {
//        WebViewActivity.startHotelActivity(getActivity(), hotelEntity.getLink());
        BaseWebViewActivityNoTitle.startActivity(getActivity(), hotelEntity.getLink());
    }

    @Override
    public void onClick(View v) {
        SearchHotelActivity.startActivity(getActivity(), params.getCityId(), params.getCheckIn(), params.getCheckOut(), params.getFrom_key());
    }

    @Override
    protected List<?> getListInvalidateContent(HotelList hotelList) {
        ((CityHotelListActivity)getActivity()).showtitle(hotelList.getCity_name());
        return hotelList.getHotel();
    }

    @Override
    protected ObjectRequest<HotelList> getObjectRequest(int pageIndex, int pageLimit) {
        return HotelHtpUtil.getHotelListRequest(params, pageIndex, pageLimit, HotelList.class);
    }
}