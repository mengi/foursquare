package com.menginar.foursquare.view.places;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.adapter.VenuesAdapter;
import com.menginar.foursquare.data.manager.NetworkManager;
import com.menginar.foursquare.data.model.venueslist.Venue;
import com.menginar.foursquare.infrastructure.operation.BaseActivity;
import com.menginar.foursquare.view.venuedetail.VenueDetailActivity;

import java.util.List;

import javax.inject.Inject;

public class PlacesActivity extends BaseActivity implements VenuesAdapter.onItemClickListener, PlacesContract.PlacesViewContract {

    @Inject
    PlacesContract.PlacesPresenterContract presenter;

    @Inject
    NetworkManager networkManager;

    private VenuesAdapter venuesAdapter;
    private String venueJsonObject = "";

    private ProgressBar pbPlaces;
    private TextView tvPlaces;
    private RecyclerView rvVenuesList;

    public class BundleParameters {
        public final static String PLACES_VENUE_LIST = "PLACES_VENUE_LIST";
        public final static String PLACES_VENUE_ID = "PLACES_VENUE_ID";
    }

    @Override
    protected void createContent(Bundle savedInstanceState) {
        super.createContent(savedInstanceState);

        if (getIntent().getExtras() != null) {
            venueJsonObject = getIntent().getExtras().getString(BundleParameters.PLACES_VENUE_LIST);
        }

        presenter.attach(this);

        pbPlaces = findViewById(R.id.pb_places);
        tvPlaces = findViewById(R.id.tv_places_empty);
        rvVenuesList = findViewById(R.id.rv_venues_list);

        rvVenuesList.setHasFixedSize(true);
        rvVenuesList.setLayoutManager(new LinearLayoutManager(this));
        venuesAdapter = new VenuesAdapter(this, getApplicationContext());
        rvVenuesList.setAdapter(venuesAdapter);

        if (venueJsonObject.isEmpty()) {
            pbPlaces.setVisibility(View.GONE);
            rvVenuesList.setVisibility(View.GONE);
            tvPlaces.setVisibility(View.VISIBLE);
        } else {
            showloader();
            presenter.getVenueListData(venueJsonObject);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.places_activity;
    }

    @Override
    protected void appIsInBackGround() {

    }

    @Override
    public void showloader() {
        if (pbPlaces != null) {
            pbPlaces.setVisibility(View.VISIBLE);
            rvVenuesList.setVisibility(View.GONE);
            tvPlaces.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideloader() {

        if (pbPlaces != null) {
            pbPlaces.setVisibility(View.GONE);
            rvVenuesList.setVisibility(View.VISIBLE);
            tvPlaces.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (tvPlaces != null) {
            pbPlaces.setVisibility(View.GONE);
            rvVenuesList.setVisibility(View.GONE);
            tvPlaces.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setVenuesList(List<Venue> venuesList) {
        venuesAdapter.onItemChangeData(venuesList);
    }

    @Override
    public void onItemClick(String venueId) {
        if (networkManager.checkNetworkConnection()) {
            Intent intent = new Intent(this, VenueDetailActivity.class);
            intent.putExtra(BundleParameters.PLACES_VENUE_ID, venueId);
            startActivity(intent);
        } else {
            runOnUiThread(() -> Toast.makeText(PlacesActivity.this, getString(R.string.str_not_connection_network), Toast.LENGTH_SHORT).show());
        }

    }
}
