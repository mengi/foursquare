package com.menginar.foursquare.di;

import android.app.Application;

import com.menginar.foursquare.infrastructure.app.FSApplication;
import com.menginar.foursquare.service.module.FSServiceModule;
import com.menginar.myflamingo.base.files.storage.StorageModule;
import com.menginar.myflamingo.base.pages.BaseAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
/**
 * Uygulama ilk yaratıldığın oluturmamız istedğimiz temel classlar
 * */
@Singleton
@Component(modules =
            {
                    BaseAppModule.class,
                    StorageModule.class,
                    FSServiceModule.class,
                    AndroidSupportInjectionModule.class,
                    ActivityProviderModule.class,
                    AppModule.class
            }
        )
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(FSApplication fsApplication);
}