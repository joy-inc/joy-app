package com.joy.app.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.joy.app.R;
import com.joy.app.bean.MainRoute;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页的线路推荐
 * User: liulongzhenhai(longzhenhai.liu@qyer.com)
 * Date: 2015-11-16
 */
public class MainRouteRvAdapter extends ExRvAdapter<MainRouteRvAdapter.ViewHolder, MainRoute> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(inflate(parent, R.layout.item_main_route_adapter));
    }

    public class ViewHolder extends ExRvViewHolder<MainRoute> {

        @Bind(R.id.sdvPhoto)
        SimpleDraweeView sdvPhoto;
        @Bind(R.id.tvName)
        TextView tvName;

        @Bind(R.id.tvEnName)
        TextView tvEnName;

        @Bind(R.id.tvTags)
        TextView tvTags;
        @Bind(R.id.tvTopic)
        TextView tvTopic;

        public ViewHolder(final View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callbackOnItemViewClickListener(getLayoutPosition(), itemView);
                }
            });
        }

        @Override
        protected void invalidateItemView(int position, MainRoute mainRoute) {

            if (mainRoute != null) {

                sdvPhoto.setImageURI(Uri.parse(mainRoute.getPic_url()));
                tvName.setText(mainRoute.getCn_name());
                tvEnName.setText(mainRoute.getEn_name());
                tvTags.setText(mainRoute.getTags());
                if (mainRoute.isCity()) {

                    hideView(tvTopic);
                } else {

                    showView(tvTopic);
                }
            }
        }
    }
}
