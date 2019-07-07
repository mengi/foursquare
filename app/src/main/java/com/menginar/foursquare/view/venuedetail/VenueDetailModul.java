package com.menginar.foursquare.view.venuedetail;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class VenueDetailModul {

    @Binds
    abstract VenueDetailContract.VenueDetailPresenterContract provide(VenueDetailPresenter venueDetailPresenter);
}
