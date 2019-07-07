package com.menginar.foursquare.di;

import com.menginar.foursquare.view.main.MainActivity;
import com.menginar.foursquare.view.main.MainModule;
import com.menginar.foursquare.view.places.PlacesActivity;
import com.menginar.foursquare.view.places.PlacesModule;
import com.menginar.foursquare.view.splash.SplashActivity;
import com.menginar.foursquare.view.venuedetail.VenueDetailActivity;
import com.menginar.foursquare.view.venuedetail.VenueDetailModul;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Acitivityleri provide ettiğimiz class
 * */

@Module
public abstract class ActivityProviderModule {

    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {PlacesModule.class})
    abstract PlacesActivity bindPlacesActivity();

    @ContributesAndroidInjector(modules = {VenueDetailModul.class})
    abstract VenueDetailActivity bindVenueDetailActivity();
}
