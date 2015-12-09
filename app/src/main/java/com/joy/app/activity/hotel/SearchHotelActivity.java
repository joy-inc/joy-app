package com.joy.app.activity.hotel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.library.activity.BaseUiActivity;
import com.android.library.adapter.OnItemViewClickListener;
import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JRecyclerView;
import com.joy.app.R;
import com.joy.app.adapter.hotel.HotelSearchHistoryAdapter;
import com.joy.app.bean.hotel.AutoComplete;
import com.joy.app.bean.hotel.EntryEntity;
import com.joy.app.bean.hotel.HotelParams;
import com.joy.app.utils.hotel.JoyShareUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 酒店搜索    <br>
 */
public class SearchHotelActivity extends BaseUiActivity implements OnItemViewClickListener,TextView.OnEditorActionListener {


    @Bind(R.id.jrv_history)
    JRecyclerView jrvHistory;

    final String SHARE_fILE = "SearchHotelActivity";
    int state = 0;

    final int empty_state = 0;
    final int history_state = 1;
    final int autocomplete_state = 2;
    final int resultcontent_state = 3;

    String cityId,checkIn,checkOut,aid;
    boolean mNeedInterceptCallback = false;
    HotelParams params;
    SeachTitleWidget seachTitleWidget;
    HotelSearchHistoryAdapter historyAdapter ;
    AutoCompleteFragment autoCompleteFragment;
    SearchHotelListFragment searchHotelListFragment;
    public static void startActivity(Activity activity, String cityID, String checkin, String checkout, String aid) {

        Intent intent = new Intent(activity, SearchHotelActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cityID", cityID);
        bundle.putString("checkIn", checkin);
        bundle.putString("checkOut", checkout);
        bundle.putString("aid", aid);
        intent.putExtras(bundle);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_hotel);
    }

    @Override
    protected void initData() {

        super.initData();
        cityId = getIntent().getStringExtra("cityID");
        checkIn = getIntent().getStringExtra("checkIn");
        checkOut = getIntent().getStringExtra("checkOut");
        aid = getIntent().getStringExtra("aid");
        params = new HotelParams();
        params.setFrom_key(aid);
        params.setCheckIn(checkIn);
        params.setCheckOut(checkOut);
        params.setCityId(cityId);
    }

    @Override
    protected void initTitleView() {
        addTitleLeftBackView();
        seachTitleWidget = new SeachTitleWidget(this);
        seachTitleWidget.getEtSearch().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //手动点击listview的item给EditText赋值时会触发回调，这时候拦截一次
                if(mNeedInterceptCallback){

                    mNeedInterceptCallback = false;
                    return;
                }

                if(TextUtil.isEmpty(s.toString())){

                    showHistory();
                }else{

                    showAutoCompleteContent(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        seachTitleWidget.getEtSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state != resultcontent_state)return;
                removeSearchContent();
                state = empty_state;
            }
        });
        seachTitleWidget.setTextHinit("输入酒店名称");
        seachTitleWidget.getEtSearch().setOnEditorActionListener(this);
        addTitleMiddleView(seachTitleWidget.getContentView());
    }

    @Override
    protected void initContentView() {

        super.initContentView();
        ButterKnife.bind(this);
        jrvHistory.setLayoutManager(new LinearLayoutManager(this));
        showHistory();
    }

    private void showHistory(){
        if (state != history_state){
            removeSearchContent();
            removeAutoCompleteContent();
        }
        if (historyAdapter == null){
            historyAdapter = new HotelSearchHistoryAdapter();
            historyAdapter.setOnItemViewClickListener(this);
            jrvHistory.setAdapter(historyAdapter);
        }
        historyAdapter.setData(JoyShareUtil.getData(this,getClass().getSimpleName(),SHARE_fILE));
        ViewUtil.showView(jrvHistory);
        state = history_state;
    }

    private void removeHistory(){
        ViewUtil.hideView(jrvHistory);
    }

    private void clearHistoryData(){
        historyAdapter.getData().clear();
        historyAdapter.notifyDataSetChanged();
        JoyShareUtil.clearData(this,getClass().getSimpleName());
    }

    private void showSearchContent(String keyowrd){
        if (state != resultcontent_state){
            removeAutoCompleteContent();
            removeHistory();
            if (searchHotelListFragment == null){
                params.setHotel(keyowrd);
                searchHotelListFragment = SearchHotelListFragment.instantiate(this,params);
            }
            addFragment(R.id.fl_content,searchHotelListFragment);
            state = resultcontent_state;
        }else{
            searchHotelListFragment.reLoadHotelList(keyowrd);
        }
        saveHistoryData(keyowrd);
    }
    private void removeSearchContent(){
        if (searchHotelListFragment == null || state != resultcontent_state)return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(searchHotelListFragment);
        ft.commitAllowingStateLoss();
    }

    private void showAutoCompleteContent(String keyowrd){
        if (state != autocomplete_state){
            state = autocomplete_state;
            removeSearchContent();
            removeHistory();
            if (autoCompleteFragment == null){
                autoCompleteFragment = AutoCompleteFragment.instantiate(this,cityId,keyowrd);
                autoCompleteFragment.setClickListener(this);
            }
            addFragment(R.id.fl_content,autoCompleteFragment);
            state = autocomplete_state;
        }else{
            autoCompleteFragment.reloadAutoComplete(keyowrd);
        }
    }

    private void removeAutoCompleteContent(){

        if (autoCompleteFragment == null || state != autocomplete_state)return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(autoCompleteFragment);
        ft.commitAllowingStateLoss();
    }

    private void saveHistoryData( String keyword){
        List<String> list = historyAdapter.getData();
        if (list.contains(keyword)) {

            return;
        } else {

            if (list.size() >= 6) {
                list.remove(5);
            }
            list.add(0, keyword);
            historyAdapter.notifyDataSetChanged();
            JoyShareUtil.SaveData(this, getClass().getSimpleName(), SHARE_fILE,list);
        }
    }

    @Override
    public void onItemViewClick(int position, View clickView, Object o) {
        if (clickView == null){
            clearHistoryData();
        }else{
            String keyword;
            if (o instanceof EntryEntity){
                keyword = ((EntryEntity) o).getCnname();
            }else{
                keyword = (String) o;
            }
            mNeedInterceptCallback = true;
            seachTitleWidget.setEditText(keyword);
            showSearchContent(keyword);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            if (TextUtil.isEmptyTrim(v.getText().toString()))
                return false;

            showSearchContent(v.getText().toString());
            return true;
        }
        return false;
    }

}
