package com.menginar.foursquare.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.model.venueslist.Venue;
import com.menginar.myflamingo.ui.adapters.BusinessAdapter;

import java.util.List;

/**
 * Mekanların Liste Kullanılan Adapter
 * */
public class VenuesAdapter extends BusinessAdapter<Venue, VenuesAdapter.VenuesListViewHolder> {

    private onItemClickListener onItemClickListener;

    public interface onItemClickListener {
        void onItemClick(String venueId);
    }

    public VenuesAdapter(onItemClickListener onItemClickListener, Context context) {
        super(context);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getViewResByItemType(int position) {
        return R.layout.venues_list_item;
    }

    @Override
    public VenuesListViewHolder getViewHolder(View view) {
        return new VenuesListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VenuesListViewHolder venuesListViewHolder, int i) {

        Venue venue = getItem(i);
        if (venue == null)
            return;

        venuesListViewHolder.tvTitle.setText(venue.getName());
        venuesListViewHolder.tvAddress.setText(venue.getLocation().getAddress());
        venuesListViewHolder.tvCity.setText(venue.getLocation().getCity());
        venuesListViewHolder.tvCountry.setText(venue.getLocation().getCountry());

        venuesListViewHolder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(venue.getId()));
    }

    public class VenuesListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvAddress, tvCity, tvCountry;

        public VenuesListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvAddress = itemView.findViewById(R.id.tv_item_address);
            tvCity = itemView.findViewById(R.id.tv_item_city);
            tvCountry = itemView.findViewById(R.id.tv_item_country);
        }
    }

    public void onItemChangeData(List<Venue> venueList) {
        clear();
        addAll(venueList);
        notifyDataSetChanged();
    }
}
