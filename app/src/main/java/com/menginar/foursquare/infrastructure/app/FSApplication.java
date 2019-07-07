package com.menginar.foursquare.infrastructure.app;

import com.menginar.foursquare.BuildConfig;
import com.menginar.foursquare.di.AppComponent;
import com.menginar.foursquare.di.DaggerAppComponent;
import com.menginar.myflamingo.base.baselog.LogType;
import com.menginar.myflamingo.base.baselog.Logger;
import com.menginar.myflamingo.base.baselog.LoggerFactory;
import com.menginar.myflamingo.base.baselog.metods.ConsoleLogMethod;
import com.menginar.myflamingo.base.files.storage.KeyValueStorage;
import com.menginar.myflamingo.base.pages.BaseApplication;

import javax.inject.Inject;
import javax.inject.Named;

public class FSApplication extends BaseApplication {

    private static FSApplication application;
    private AppComponent appComponent;

    @Inject
    @Named("Persistent")
    KeyValueStorage persistentKeyValueStorage;

    @Inject
    @Named("MemoryStorage")
    KeyValueStorage memoryKeyValueStorage;

    @Inject
    Logger logger;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            LoggerFactory.getLoggerFactory().initLogger("DefaultLogger", new ConsoleLogMethod());
        }

        application = this;

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build();

        appComponent.inject(this);

        logger.log(LogType.INFO, getClass().getSimpleName(), "Mobven app created");
    }

    public static AppComponent getAppComponent() {
        return application.appComponent;
    }

    @Override
    protected int getApplicationVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    protected String getApplicationVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    protected KeyValueStorage getPersistentKeyValueStorage() {
        return persistentKeyValueStorage;
    }

    @Override
    protected KeyValueStorage getMemoryStorage() {
        return memoryKeyValueStorage;
    }
}
