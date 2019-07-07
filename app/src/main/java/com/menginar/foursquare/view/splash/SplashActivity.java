package com.menginar.foursquare.view.splash;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.manager.CurrentLocationManager;
import com.menginar.foursquare.data.manager.PermissionManager;
import com.menginar.foursquare.infrastructure.operation.BaseActivity;
import com.menginar.foursquare.view.main.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    PermissionManager permissionManager;

    @Inject
    CurrentLocationManager currentLocationManager;

    private Location currentLocation;

    @Override
    protected void createContent(Bundle savedInstanceState) {
        super.createContent(savedInstanceState);


        if (permissionManager.hasAccessFineLocationPermission(this));
        currentLocation = currentLocationManager.getCurrentLocation((LocationManager) getSystemService(Context.LOCATION_SERVICE));

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.BundleParameters.CURRENT_LOCATION, currentLocation);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 2000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void appIsInBackGround() {

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
