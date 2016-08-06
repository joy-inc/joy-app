package com.joy.app.adapter.hotel;

import android.view.View;
import android.view.ViewGroup;

import com.android.library.adapter.ExRvAdapter;
import com.android.library.adapter.ExRvViewHolder;
import com.android.library.view.fresco.FrescoIv;
import com.android.library.widget.JTextView;
import com.joy.app.R;
import com.joy.app.bean.hotel.HotelEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author litong  <br>
 * @Description 酒店列表    <br>
 */
public class HotelListAdapter extends ExRvAdapter<HotelListAdapter.ViewHolder,HotelEntity> {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(parent, R.layout.item_hotel_search_list));
    }

    public class ViewHolder extends ExRvViewHolder<HotelEntity>  {
        @BindView(R.id.topline)
        View topline;

        @BindView(R.id.iv_hotel_item_Photo)
        FrescoIv ivHotelItemPhoto;
        @BindView(R.id.tv_hotel_city_cnname)
        JTextView tvHotelCityCnname;
        @BindView(R.id.tv_hotel_city_enname)
        JTextView tvHotelCityEnname;
        @BindView(R.id.tv_hotel_city_star)
        JTextView tvHotelCityStar;
        @BindView(R.id.tv_hotel_item_price)
        JTextView tvHotelItemPrice;

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
        public void invalidateItemView(int position, HotelEntity hotelEntity) {
            ivHotelItemPhoto.setImageURI(hotelEntity.getPhoto());
            tvHotelCityCnname.setText(hotelEntity.getCnname());
            tvHotelCityEnname.setText(hotelEntity.getEnname());
            tvHotelItemPrice.setText(hotelEntity.getPriceStr());
            tvHotelCityStar.setText(hotelEntity.getStar());
        }
    }
}
