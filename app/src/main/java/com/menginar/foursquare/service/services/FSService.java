package com.menginar.foursquare.service.services;

import com.menginar.foursquare.data.model.venuedetail.VenueDetail;
import com.menginar.foursquare.data.model.venueslist.VenuesList;
import com.menginar.foursquare.service.RequestCallBack;
import com.menginar.foursquare.service.interfaces.FSResponseListener;
import com.menginar.foursquare.service.interfaces.FSRetroInterface;
import com.menginar.foursquare.service.module.FSServiceModule;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * App query method
 * */

@Singleton
public class FSService extends BaseService {

    @Inject
    FSService(@Named(FSServiceModule.RETROFIT_PROVIDER) Retrofit retrofit) {
        super(retrofit);
    }

    public void getVenuesList(String placeType, String near, FSResponseListener<VenuesList> fsResponseListener) {

        FSRetroInterface fsRetroInterface = createService(FSRetroInterface.class);
        Call<VenuesList> call = fsRetroInterface.getVenues(APP_VERSION, CLIENT_ID, CLIENT_SECRET, placeType, near);
        call.enqueue(new RequestCallBack<>(fsResponseListener));
    }

    public void getVenuesByLocaiton(String placeType, String latLng, FSResponseListener<VenuesList> fsResponseListener) {

        FSRetroInterface fsRetroInterface = createService(FSRetroInterface.class);
        Call<VenuesList> call = fsRetroInterface.getVenuesByLocation(APP_VERSION, CLIENT_ID, CLIENT_SECRET, placeType, latLng);
        call.enqueue(new RequestCallBack<>(fsResponseListener));
    }

    public void getVenueDetail(String venueId, FSResponseListener<VenueDetail> fsResponseListener) {
        FSRetroInterface fsRetroInterface = createService(FSRetroInterface.class);
        Call<VenueDetail> call = fsRetroInterface.getVenueDetail(venueId, APP_VERSION, CLIENT_ID, CLIENT_SECRET);
        call.enqueue(new RequestCallBack<>(fsResponseListener));
    }
}
