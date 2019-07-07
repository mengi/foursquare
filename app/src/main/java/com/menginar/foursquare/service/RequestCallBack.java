package com.menginar.foursquare.service;

import com.menginar.foursquare.service.interfaces.FSResponseListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallBack<T> implements Callback<T> {

    private FSResponseListener<T> responseListener;

    public RequestCallBack(FSResponseListener<T> responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            responseListener.onSuccess(response.body());
        } else {
            responseListener.onFail(response.code(),"Server error", new RequestExcept(response.code(), response.message()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        call.cancel();
        responseListener.onFail(-1, t.getMessage(), t);
    }
}
