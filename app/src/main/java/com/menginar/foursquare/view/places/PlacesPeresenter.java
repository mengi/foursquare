package com.menginar.foursquare.view.places;


import com.google.gson.Gson;
import com.menginar.foursquare.data.model.venueslist.VenuesList;
import com.menginar.myflamingo.base.baselog.LogType;
import com.menginar.myflamingo.base.baselog.Logger;
import com.menginar.myflamingo.base.pages.BasePresenter;

import javax.inject.Inject;

public class PlacesPeresenter extends BasePresenter<PlacesContract.PlacesViewContract> implements PlacesContract.PlacesPresenterContract {

    private Gson gson;
    private Logger logger;

    @Inject
    public PlacesPeresenter(Logger logger) {
        this.logger = logger;
        this.gson = new Gson();
    }

    @Override
    protected void onAttached() {

    }

    @Override
    protected void onDetached() {

    }

    /**
     * Venue detail
     * */
    @Override
    public void getVenueListData(String venuesJsonObject) {
        if (gettView() != null) {

            try {
                VenuesList venueList = gson.fromJson(venuesJsonObject, VenuesList.class);
                gettView().hideloader();
                gettView().setVenuesList(venueList.getResponse().getVenues());

            } catch (Exception e) {
                gettView().hideloader();
                gettView().onError();
                logger.log(LogType.ERROR, "PlacesPeresenter", e.getMessage(), e);
            }
        }
    }

}
