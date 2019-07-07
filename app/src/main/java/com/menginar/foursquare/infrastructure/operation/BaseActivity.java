package com.menginar.foursquare.infrastructure.operation;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.menginar.foursquare.data.manager.CurrentLocationManager;
import com.menginar.foursquare.service.events.RefreshLocationEvent;
import com.menginar.myflamingo.base.baselog.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends com.menginar.myflamingo.base.pages.BaseActivity implements
        HasSupportFragmentInjector, CurrentLocationListener {

    @Inject
    Logger logger;

    @Inject
    CurrentLocationManager currentLocationManager;

    private CurrentLocationListener currentLocationListener;

    @Override
    protected void createContent(Bundle savedInstanceState) {

        setContentView(getLayoutId());

        this.currentLocationListener = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected Logger getDefaultLogger() {
        return logger;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshLocationEvent event) {

        Location currentLocation = currentLocationManager.getCurrentLocation((LocationManager)
                (this.getSystemService(Context.LOCATION_SERVICE)));

        if (currentLocationListener != null) {
            currentLocationListener.onCurrentLocation(currentLocation);
        }
    }

    @Override
    public void onCurrentLocation(Location currentLocation) {

    }
}