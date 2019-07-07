package com.menginar.foursquare.view.venuedetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.menginar.foursquare.R;
import com.menginar.foursquare.data.adapter.UserCommentsAdapter;
import com.menginar.foursquare.data.model.venuedetail.Item_;
import com.menginar.foursquare.data.model.venuedetail.Meta;
import com.menginar.foursquare.infrastructure.operation.BaseActivity;
import com.menginar.foursquare.view.places.PlacesActivity;
import com.menginar.myflamingo.base.baselog.LogType;
import com.menginar.myflamingo.base.infrastructure.InteractionException;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class VenueDetailActivity extends BaseActivity implements VenueDetailContract.VenueDetailViewContract, OnMapReadyCallback {

    @Inject
    VenueDetailContract.VenueDetailPresenterContract presenter;

    private GoogleMap googleMap;
    private Handler handler;
    private String venueId;
    private MapView mpvVenue;
    private ImageView ivVenue;
    private TextView tvVenueName, tvCommentsError;
    private RecyclerView rvUserComments;

    private UserCommentsAdapter userCommentsAdapter;

    @Override
    protected void createContent(Bundle savedInstanceState) {
        super.createContent(savedInstanceState);

        handler = new Handler();

        if (getIntent().getExtras() != null) {
            venueId = getIntent().getExtras().getString(PlacesActivity.BundleParameters.PLACES_VENUE_ID);
        }

        mpvVenue = findViewById(R.id.mpv_venue);
        ivVenue = findViewById(R.id.iv_venue);
        mpvVenue = findViewById(R.id.mpv_venue);
        tvVenueName = findViewById(R.id.tv_venue_name);
        rvUserComments = findViewById(R.id.rv_user_comments);
        tvCommentsError = findViewById(R.id.tv_comments_empty);

        rvUserComments.setHasFixedSize(true);
        rvUserComments.setLayoutManager(new LinearLayoutManager(this));
        userCommentsAdapter = new UserCommentsAdapter(this);
        rvUserComments.setAdapter(userCommentsAdapter);

        MapsInitializer.initialize(this);
        mpvVenue.onCreate(savedInstanceState);
        mpvVenue.getMapAsync(this);

        presenter.attach(this);

        if (venueId.isEmpty()) {
            Toast.makeText(this, getString(R.string.str_data_error), Toast.LENGTH_SHORT).show();
        } else {
            presenter.getVenueData(venueId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mpvVenue.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mpvVenue.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
        mpvVenue.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.venue_detail_activity;
    }

    @Override
    protected void appIsInBackGround() {

    }

    @Override
    public void onError(InteractionException exception) {
        getDefaultLogger().log(LogType.ERROR, "VenueDetailActivity", exception.getMessage(), exception);
        handler.post(() -> {
            Toast.makeText(VenueDetailActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }

    @Override
    public void onErrorNull(boolean isImageUrlNull, boolean isCommentsNull) {
        handler.post(() -> {

            if (isCommentsNull) {
                ivVenue.setVisibility(View.VISIBLE);
            }

            if (isCommentsNull) {
                tvCommentsError.setVisibility(View.VISIBLE);
                rvUserComments.setVisibility(View.GONE);
            }

        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onErrorMeta(Meta meta) {
        if (meta != null) {
            handler.post(() -> {
                Toast.makeText(VenueDetailActivity.this, String.format("Error Code : %d | Error Detail %s",
                        meta.getCode(), meta.getErrorDetail()), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onNetworkConnectionError() {
        handler.post(() -> Toast.makeText(VenueDetailActivity.this, getString(R.string.str_not_connection_network), Toast.LENGTH_SHORT).show());
    }


    @Override
    public void onRunOnUiThread(Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        }
    }

    @Override
    public void setMapLatLng(double lat, double lng, String venueName) {

        handler.post(() -> {
            if (mpvVenue != null) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(venueName));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));
            }
        });

    }

    @Override
    public void setTextVenueName(String venueName) {
        if (tvVenueName != null && venueName != null) {
            handler.post(() -> tvVenueName.setText(venueName));
        }
    }

    @Override
    public void setImageVenue(String imgeUrl) {
        if (ivVenue != null) {
            handler.post(() -> Picasso.with(VenueDetailActivity.this).load(imgeUrl).error(R.drawable.ic_splash).into(ivVenue));
        }
    }

    @Override
    public void setUserComments(List<Item_> userComments) {
        handler.post(() -> {
            if (userComments.size() > 0) {
                tvCommentsError.setVisibility(View.GONE);
                rvUserComments.setVisibility(View.VISIBLE);
                userCommentsAdapter.onChangeItemData(userComments);
            } else {
                tvCommentsError.setVisibility(View.VISIBLE);
                rvUserComments.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

    }
}
