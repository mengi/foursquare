package com.menginar.foursquare.service.interfaces;


import com.menginar.foursquare.data.model.venueslist.VenuesList;
import com.menginar.foursquare.data.model.venuedetail.VenueDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FSRetroInterface {

    @GET("/v2/venues/search")
    Call<VenuesList> getVenues(@Query("v") String version,
                               @Query("client_id") String clientID,
                               @Query("client_secret") String clientSecret,
                               @Query("query") String placeType,
                               @Query("near") String near);

    @GET("/v2/venues/search")
    Call<VenuesList> getVenuesByLocation(@Query("v") String version,
                                         @Query("client_id") String clientID,
                                         @Query("client_secret") String clientSecret,
                                         @Query("query") String placeType,
                                         @Query("ll") String latLng);


    @GET("/v2/venues/{venue_id}")
    Call<VenueDetail> getVenueDetail(@Path("venue_id") String venueId,
                                             @Query("v") String version,
                                             @Query("client_id") String clientID,
                                             @Query("client_secret") String clientSecret);
}