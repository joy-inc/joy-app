package com.joy.app.activity.hotel;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.library.utils.TextUtil;
import com.android.library.utils.ViewUtil;
import com.android.library.view.ExLayoutWidget;
import com.joy.app.R;

/**
 * Created by qixinghai on 15/5/14.
 *
 * 通用Title位置都搜索组件
 */
public class SeachTitleWidget extends ExLayoutWidget{

    private EditText mEtSearch;
    private View mClearView;

    public SeachTitleWidget(Activity activity) {

        super(activity);
    }

    @Override
    protected View onCreateView(Activity activity, Object... args) {

        View contentView = LayoutInflater.from(activity).inflate(R.layout.act_search_title,null);
        mEtSearch = (EditText) contentView.findViewById(R.id.et_search);
        mClearView = contentView.findViewById(R.id.iv_clear_content);

        mEtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtil.isEmpty(s.toString())) {
                    hideClearView();
                    showInputWindow();
                } else
                    showClearView();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mClearView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mEtSearch.setText(TextUtil.TEXT_EMPTY);
            }
        });

        return contentView;
    }

    @Override
    public void onStop() {

        super.onStop();
        hiddenInputWindow();
    }

    public void hideClearView(){

        ViewUtil.hideView(mClearView);
    }

    public void showClearView(){

        ViewUtil.showView(mClearView);
    }


    public EditText getEtSearch() {

        return mEtSearch;
    }

    /**
     *
     * @param str
     * @param spanColor
     */
    public void setTextHinit(String str,int spanColor){

//        SpannableString ss = new SpannableString(str);
//        ss.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(spanColor)),
//                0, ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        mEtSearch.setHint(new SpannedString(ss));
        mEtSearch.setHintTextColor(spanColor);
        mEtSearch.setHint(str);

    }
    /**
     *
     * @param str
     *
     */
    public void setTextHinit(String str){

        mEtSearch.setHint(str);

    }

    /**
     * 设置搜索框的显示内容
     * @param str
     */
    public void setEditText(String str){

        if(str != null && str.equals(mEtSearch.getText().toString()))return;
        mEtSearch.setText(str);
        mEtSearch.setSelection(str.length());
    }

    public String getText(){

        return TextUtil.filterNull(mEtSearch.getText().toString());
    }

    /**
     * 隐藏输入框
     */
    public void hiddenInputWindow() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);

    }

    /**
     * 弹出输入框
     */
    public void showInputWindow() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtSearch, InputMethodManager.SHOW_IMPLICIT);
    }

}


