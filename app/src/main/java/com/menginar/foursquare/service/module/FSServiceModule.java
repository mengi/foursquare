package com.menginar.foursquare.service.module;

import android.os.AsyncTask;

import com.menginar.foursquare.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit connection
 * */

@Module
public class FSServiceModule {

    public static final String RETROFIT_PROVIDER = "fSRetrofit";

    @Provides
    @Singleton
    @Named(RETROFIT_PROVIDER)
    Retrofit provideRetrofit() {

        HttpLoggingInterceptor.Level LOGGING_LEVEL = HttpLoggingInterceptor.Level.NONE;

        if (BuildConfig.DEBUG) {
            LOGGING_LEVEL = HttpLoggingInterceptor.Level.BODY;
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(LOGGING_LEVEL);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor).build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com/v2/")
                .callbackExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create());

        return retrofitBuilder.build();
    }

}
