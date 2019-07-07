package com.menginar.foursquare.service.interfaces;

public interface FSResponseListener<T> {
    void onSuccess(T t);

    void onFail(int errorCode, String message, Throwable t);
}
