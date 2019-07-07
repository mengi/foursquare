package com.menginar.foursquare.view.main;

import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.menginar.foursquare.data.manager.NetworkManager;
import com.menginar.foursquare.data.model.venueslist.Meta;
import com.menginar.foursquare.data.model.venueslist.VenuesList;
import com.menginar.foursquare.domain.GetVenuesListByLocationInteraction;
import com.menginar.foursquare.domain.GetVenuesListInteraction;
import com.menginar.myflamingo.base.infrastructure.InteractionResultListener;
import com.menginar.myflamingo.base.pages.BasePresenter;

import javax.inject.Inject;


public class MainPresenter extends BasePresenter<MainContract.MainViewContract> implements MainContract.MainPresenterContract {

    private Gson gson;
    private NetworkManager networkManager;
    private GetVenuesListInteraction getVenuesListInteraction;
    private GetVenuesListByLocationInteraction getVenuesListByLocationInteraction;

    @Inject
    public MainPresenter(GetVenuesListInteraction getVenuesListInteraction, NetworkManager networkManager,
                         GetVenuesListByLocationInteraction getVenuesListByLocationInteraction) {

        this.gson = new Gson();
        this.networkManager = networkManager;
        this.getVenuesListInteraction = getVenuesListInteraction;
        this.getVenuesListByLocationInteraction = getVenuesListByLocationInteraction;
    }

    @Override
    protected void onAttached() {

    }

    @Override
    protected void onDetached() {

    }

    /**
     * Venue List(Location) query
     * */

    @Override
    public void getVenueListByLocation(Location location, String placeType) {
        if (gettView() != null && networkManager.checkNetworkConnection()) {

            gettView().showLoader();

            String latLng = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());

            getVenuesListByLocationInteraction.setIn(new GetVenuesListByLocationInteraction.Request(placeType, latLng));
            getVenuesListByLocationInteraction.execute((InteractionResultListener<VenuesList>) out -> {
                if (gettView() != null) {

                    gettView().hideLoader();

                    if (out.isSuccess()) {
                        if (out.getOut().getMeta().getCode() == 200 && !out.getOut().getResponse().getVenues().isEmpty()) {
                            gettView().setVenuesListData(gson.toJson(out.getOut(), VenuesList.class));
                        } else {
                            gettView().onError(out.getOut().getMeta());
                        }
                    } else {
                        gettView().setVenuesListInteractionException(out.getException());
                    }
                }
            });
        } else {
            if (gettView() != null) {
                gettView().onNetworkConnectionError();
            }
        }
    }

    /**
     * Venue List(Category) query
     * */

    @Override
    public void getVenueList(String placeType, String near) {

        if (gettView() != null && networkManager.checkNetworkConnection()) {

            gettView().showLoader();

            getVenuesListInteraction.setIn(new GetVenuesListInteraction.Request(placeType, near));
            getVenuesListInteraction.execute(out -> {

                gettView().hideLoader();

                if (out.isSuccess()) {
                    if (out.getOut().getMeta().getCode() == 200 && !out.getOut().getResponse().getVenues().isEmpty()) {
                        gettView().setVenuesListData(gson.toJson(out.getOut(), VenuesList.class));
                    } else {
                        gettView().onError(out.getOut().getMeta());
                    }
                } else {
                    gettView().setVenuesListInteractionException(out.getException());
                }
            });

        } else {
            if (gettView() != null) {
                gettView().onNetworkConnectionError();
            }
        }

    }

}
