package com.joy.app.activity.hotel;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.httptask.ObjectRequest;
import com.joy.app.bean.hotel.HotelList;

/**
 * @author litong  <br>
 * @Description 搜索结果页    <br>
 */
public class SearchResultFragment extends BaseHttpRvFragment<HotelList> {

    @Override
    protected ObjectRequest<HotelList> getObjectRequest(int pageIndex, int pageLimit) {
        return null;
    }
}
