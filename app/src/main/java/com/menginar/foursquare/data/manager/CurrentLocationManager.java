package com.menginar.foursquare.data.manager;

import android.location.Location;
import android.location.LocationManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Location için kullandğım manager
 * */

@Singleton
public class CurrentLocationManager {

    @Inject
    public CurrentLocationManager() {

    }

    public Location getCurrentLocation(LocationManager mLocationManager) {

        if (mLocationManager == null)
            return null;

        List<String> providers = mLocationManager.getProviders(true);
        Location mCurrentLocation = null;


        for (String mProvider : providers) {

            try {

                mCurrentLocation = mLocationManager.getLastKnownLocation(mProvider);
                if (mCurrentLocation != null)
                    break;

            } catch (SecurityException ex) {

            }
        }

        return mCurrentLocation;
    }

}
