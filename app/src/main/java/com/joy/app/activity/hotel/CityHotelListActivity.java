package com.joy.app.activity.hotel;

import com.android.library.activity.BaseHttpRvActivity;
import com.android.library.activity.BaseHttpUiActivity;
import com.android.library.httptask.ObjectRequest;

/**
 * @author litong  <br>
 * @Description 城市酒店列表    <br>
 */
public class CityHotelListActivity extends BaseHttpUiActivity {
    @Override
    protected boolean invalidateContent(Object o) {
        return false;
    }

    @Override
    protected ObjectRequest getObjectRequest() {
        return null;
    }
}
