package com.menginar.foursquare.view.places;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PlacesModule {

    @Binds
    abstract PlacesContract.PlacesPresenterContract provide(PlacesPeresenter placesPeresenter);
}
