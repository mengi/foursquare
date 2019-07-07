package com.menginar.foursquare.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.menginar.foursquare.service.events.RefreshLocationEvent;

import org.greenrobot.eventbus.EventBus;

public class LocationProviderReceiver extends BroadcastReceiver {

    public static final String LOCATION_CHANGED = "android.location.PROVIDERS_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().matches(LOCATION_CHANGED)) {

            LocationManager manager =
                    (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                EventBus.getDefault().post(new RefreshLocationEvent());
        }
    }
}
