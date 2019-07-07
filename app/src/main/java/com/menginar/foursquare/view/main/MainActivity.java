package com.menginar.foursquare.view.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.helpers.DialogHelpers;
import com.menginar.foursquare.data.manager.NetworkManager;
import com.menginar.foursquare.data.manager.PermissionManager;
import com.menginar.foursquare.data.model.venueslist.Meta;
import com.menginar.foursquare.infrastructure.operation.BaseActivity;
import com.menginar.foursquare.service.events.RefreshLocationEvent;
import com.menginar.foursquare.view.places.PlacesActivity;
import com.menginar.myflamingo.base.infrastructure.InteractionException;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class MainActivity extends BaseActivity implements MainContract.MainViewContract, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    MainContract.MainPresenterContract presenter;

    @Inject
    PermissionManager permissionManager;

    private Location currentLocation;

    private ProgressDialog progressDialog;

    public class BundleParameters {
        public static final String CURRENT_LOCATION = "CURRENT_LOCATION";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            currentLocation = (Location) getIntent().getExtras().get(BundleParameters.CURRENT_LOCATION);
        }

        if (!hasAllPermissionsCheck())
            requestNeedPermissions();
    }

    @Override
    protected void createContent(Bundle savedInstanceState) {
        super.createContent(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.baseline_reorder_black_24));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        EditText etPlaceName = findViewById(R.id.et_places_name);
        EditText etNear = findViewById(R.id.et_near);
        Button btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> {
            if (!etPlaceName.getText().toString().isEmpty() && etPlaceName.getText().toString().length() > 2) {
                if (etNear.getText().toString().trim().isEmpty()) {
                    if (currentLocation != null) {
                        presenter.getVenueListByLocation(currentLocation, etPlaceName.getText().toString());
                    } else {
                        DialogHelpers.showErrorDialog(MainActivity.this, getString(R.string.str_close_location_error_message), (dialog, which) -> dialog.dismiss());
                    }
                } else {
                    presenter.getVenueList(etPlaceName.getText().toString(), etNear.getText().toString());
                }
            } else {
                DialogHelpers.showErrorDialog(MainActivity.this, getString(R.string.str_places_length_error_message), (dialog, which) -> dialog.dismiss());
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.str_please_wait));

        presenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    protected void appIsInBackGround() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_navigation;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogHelpers.showDialog(this, getString(R.string.dialog_title_info),
                    getString(R.string.str_app_exit), true,

                    getString(R.string.dialog_okay_button_text), (dialog, which) -> {
                        finish();
                        dialog.dismiss();
                    },

                    getString(R.string.dialog_no_button_text), (dialog, which) -> dialog.dismiss(), null, null);
        }
    }

    @Override
    public void showLoader() {
        if (progressDialog != null) {
            MainActivity.this.runOnUiThread(() -> progressDialog.show());
        }

    }

    @Override
    public void hideLoader() {
        if (progressDialog != null) {
            MainActivity.this.runOnUiThread(() -> progressDialog.dismiss());
        }
    }

    @Override
    public void setVenuesListData(String venuesJsonObject) {
        Intent intent = new Intent(this, PlacesActivity.class);
        intent.putExtra(PlacesActivity.BundleParameters.PLACES_VENUE_LIST, venuesJsonObject);
        startActivity(intent);
    }

    @Override
    public void setVenuesListInteractionException(InteractionException exception) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onNetworkConnectionError() {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, getString(R.string.str_not_connection_network), Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onError(Meta meta) {
        if (meta != null) {
            runOnUiThread(() -> {
                if (meta.getCode() != 200) {
                Toast.makeText(MainActivity.this, String.format("Error Code : %d | Error Detail %s",
                        meta.getCode(), meta.getErrorDetail()) , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, getString(R.string.str_category_empty), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EventBus.getDefault().post(new RefreshLocationEvent());
    }

    @Override
    public void onCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    private void requestNeedPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{INTERNET, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, 1);
    }

    private boolean hasAllPermissionsCheck() {
        boolean grantedAllPermissions = checkPermissionGranted(INTERNET) && checkPermissionGranted(ACCESS_FINE_LOCATION)
                && checkPermissionGranted(ACCESS_COARSE_LOCATION);

        return grantedAllPermissions;
    }

    private boolean checkPermissionGranted(String permission) {
        return permissionManager.hasPermission(this, permission);
    }
}
