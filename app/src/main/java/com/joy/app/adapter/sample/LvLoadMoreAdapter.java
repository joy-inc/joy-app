package com.joy.app.adapter.sample;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.sample.Special;
import com.joy.library.adapter.frame.ExAdapter;
import com.joy.library.adapter.frame.ExViewHolder;
import com.joy.library.adapter.frame.ExViewHolderBase;

/**
 * Created by KEVIN.DAI on 15/11/23.
 */
public class LvLoadMoreAdapter extends ExAdapter<Special> {

    @Override
    protected ExViewHolder getViewHolder(int position) {

        return new ViewHolder();
    }

    private class ViewHolder extends ExViewHolderBase {

        private SimpleDraweeView sdvPhoto;
        private TextView tvName;

        @Override
        public int getConvertViewRid() {

            return R.layout.t_item_listview;
        }

        @Override
        public void initConvertView(View convertView) {

            sdvPhoto = (SimpleDraweeView) convertView.findViewById(R.id.sdvPhoto);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(mPosition, v);
                }
            });
        }

        @Override
        public void invalidateConvertView() {

            Special data = getItem(mPosition);
            sdvPhoto.setImageURI(Uri.parse(data.getPhoto()));
            tvName.setText(data.getTitle());
        }
    }
}
