package com.menginar.foursquare.data.manager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * İzinler bağlantı kontorlu için
 * */

@Singleton
public class PermissionManager {

    @Inject
    public PermissionManager() {

    }

    public static boolean hasPermission(AppCompatActivity mActivity, String permisson) {
        return ActivityCompat.checkSelfPermission(mActivity, permisson) != PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasAccessFineLocationPermission(AppCompatActivity mActivity) {
        return hasPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
