package com.menginar.foursquare.service.services;

import retrofit2.Retrofit;

public abstract class BaseService {

    protected Retrofit retrofit;
    protected final String APP_VERSION = "20161010";
    protected final String CLIENT_ID = "ON1VHU42OGA2ZTVQZY5YI0NMFI4YB5JJCJXKO5BFJ52BAD1J";
    protected final String CLIENT_SECRET = "3X2ZSUAOMPCNQSBOCM4R10KEFHKBPCHA4XG0A4LA0IKTVWLM";

    BaseService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    protected <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
