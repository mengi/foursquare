package com.menginar.foursquare.view.main;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.MainPresenterContract provide(MainPresenter mainPresenter);
}
