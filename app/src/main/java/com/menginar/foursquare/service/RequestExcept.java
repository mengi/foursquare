package com.menginar.foursquare.service;

public class RequestExcept extends Exception {

    private int responseCode;

    public RequestExcept(int responseCode, String message) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
