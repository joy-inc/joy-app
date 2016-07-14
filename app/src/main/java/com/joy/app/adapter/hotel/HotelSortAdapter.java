package com.joy.app.adapter.hotel;

import android.view.View;
import android.widget.ImageView;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.android.library.utils.ViewUtil;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.hotel.FilterItems;

/**
 * @author litong  <br>
 * @Description 排序    <br>
 */
public class HotelSortAdapter extends ExAdapter<FilterItems> {
    int select;
    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    public void setSelect(int select) {
        this.select = select;
    }

    class ViewHolder extends ExViewHolderBase implements View.OnClickListener  {
        JTextView jtvName;
        ImageView ivSelect;

        @Override
        public void invalidateConvertView() {
            FilterItems items =  getItem(mPosition);
            jtvName.setText(items.getName());
            if (select == items.getValue()){
                ViewUtil.showView(ivSelect);
            }else{
                ViewUtil.hideView(ivSelect);
            }
        }

        @Override
        public int getConvertViewRid() {
            return  R.layout.item_hotel_sort;
        }

        @Override
        public void initConvertView(View convertView) {
            convertView.setOnClickListener(this);
            ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            jtvName = (JTextView) convertView.findViewById(R.id.jtv_name);
        }

        @Override
        public void onClick(View v) {
            select = mPosition;
            callbackOnItemViewClickListener(mPosition,v);
        }
    }
}
