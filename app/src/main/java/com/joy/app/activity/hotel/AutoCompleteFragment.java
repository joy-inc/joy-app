package com.joy.app.activity.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.android.library.activity.BaseHttpRvFragment;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.httptask.ObjectRequest;
import com.android.library.utils.TextUtil;
import com.android.library.view.recyclerview.RecyclerAdapter;
import com.joy.app.adapter.hotel.AutoCompleteAdapter;
import com.joy.app.bean.hotel.AutoComplete;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.http.HotelHtpUtil;

import java.net.URLEncoder;
import java.util.List;

/**
 * @author litong  <br>
 * @Description 酒店搜索联想    <br>
 */
public class AutoCompleteFragment extends BaseHttpRvFragment<AutoComplete> implements RecyclerAdapter.OnItemClickListener {
    String id ,keyword;
    OnItemViewClickListener clickListener;

    public static AutoCompleteFragment instantiate(Context context, String id,String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString("cityID", TextUtil.filterNull(id) );
        bundle.putString("keyword", TextUtil.filterNull(keyword) );
        return (AutoCompleteFragment) Fragment.instantiate(context, AutoCompleteFragment.class.getName(), bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        executeRefreshOnly();
    }

    @Override
    protected void initData() {
        super.initData();
        id = getArguments().getString("cityID");
        keyword = getArguments().getString("keyword");
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter();
        if (clickListener != null){
            autoCompleteAdapter.setOnItemViewClickListener(clickListener);
        }
        setAdapter(autoCompleteAdapter);
    }

    public void setClickListener(OnItemViewClickListener clickListener) {
        this.clickListener = clickListener;

    }

    public void reloadAutoComplete(String keyword){
        if (TextUtil.isNotEmpty(this.keyword) && this.keyword.equals(keyword))return;
        this.keyword = keyword;
        getAdapter().clear();
        executeRefreshOnly();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    protected List<?> getListInvalidateContent(AutoComplete autoComplete) {
        return autoComplete.getEntry();
    }

    @Override
    protected ObjectRequest<AutoComplete> getObjectRequest(int pageIndex, int pageLimit) {
        return HotelHtpUtil.getAutoCompleteRequest(id, URLEncoder.encode(keyword),AutoComplete.class);
    }
}
