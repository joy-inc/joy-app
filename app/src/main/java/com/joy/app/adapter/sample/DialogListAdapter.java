package com.joy.app.adapter.sample;

import android.view.View;

import com.android.library.adapter.ExAdapter;
import com.android.library.adapter.ExViewHolder;
import com.android.library.adapter.ExViewHolderBase;
import com.joy.app.R;

public class DialogListAdapter extends ExAdapter {

    @Override
    public int getCount() {

        return 4;
    }

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    class ViewHolder extends ExViewHolderBase {

        @Override
        public int getConvertViewRid() {

            return R.layout.t_item_dialog;
        }

        @Override
        public void initConvertView(View convertView) {

        }

        @Override
        public void invalidateConvertView() {

        }
    }
}