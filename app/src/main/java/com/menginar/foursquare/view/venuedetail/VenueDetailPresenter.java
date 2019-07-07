package com.menginar.foursquare.view.venuedetail;

import com.menginar.foursquare.data.manager.NetworkManager;
import com.menginar.foursquare.data.model.venuedetail.Tips;
import com.menginar.foursquare.data.model.venuedetail.Venue;
import com.menginar.foursquare.data.model.venuedetail.VenueDetail;
import com.menginar.foursquare.domain.GetVenueDetailInteraction;
import com.menginar.myflamingo.base.infrastructure.InteractionResultListener;
import com.menginar.myflamingo.base.pages.BasePresenter;

import javax.inject.Inject;

public class VenueDetailPresenter extends BasePresenter<VenueDetailContract.VenueDetailViewContract> implements VenueDetailContract.VenueDetailPresenterContract {

    public static final String SIZE_500 = "500x500";

    private NetworkManager networkManager;
    private GetVenueDetailInteraction getVenueDetailInteraction;


    @Inject
    public VenueDetailPresenter(GetVenueDetailInteraction getVenueDetailInteraction, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.getVenueDetailInteraction = getVenueDetailInteraction;
    }

    @Override
    protected void onAttached() {

    }

    @Override
    protected void onDetached() {

    }

    /**
     * Venue query
     * */

    @Override
    public void getVenueData(String venueId) {

        if (gettView() != null && networkManager.checkNetworkConnection()) {
            gettView().onRunOnUiThread(() -> {

                getVenueDetailInteraction.setIn(new GetVenueDetailInteraction.Request(venueId));
                getVenueDetailInteraction.execute((InteractionResultListener<VenueDetail>) out -> {
                    if (out.isSuccess()) {
                        if (out.getOut().getMeta().getCode() == 200) {

                            Venue venue = out.getOut().getResponse().getVenue();
                            if (venue != null) {

                                if (venue.getName() != null)
                                    gettView().setTextVenueName(venue.getName());

                                if (venue.getBestPhoto() != null && venue.getBestPhoto().getPrefix() != null && venue.getBestPhoto().getSuffix() != null) {
                                    String photoUrl = venue.getBestPhoto().getPrefix() + SIZE_500 + venue.getBestPhoto().getSuffix();
                                    gettView().setImageVenue(photoUrl);
                                } else {
                                    gettView().onErrorNull(true, false);
                                }

                                if (venue.getTips() != null && venue.getTips().getGroups() != null) {
                                    Tips userComments = venue.getTips();
                                    gettView().setUserComments(userComments.getGroups().get(0).getItems());
                                } else {
                                    gettView().onErrorNull(false, true);
                                }

                                if (venue.getLocation() != null) {
                                    gettView().setMapLatLng(venue.getLocation().getLat(), venue.getLocation().getLng(), venue.getName());
                                } else {
                                    gettView().setMapLatLng(37.8557, 32.4722, venue.getName());
                                }

                            } else {
                                gettView().onErrorNull(true, true);
                            }
                        } else {
                            gettView().onErrorMeta(out.getOut().getMeta());
                        }
                    } else {
                        gettView().onError(out.getException());
                    }
                });
            });
        } else {
            if (gettView() != null) {
                gettView().onNetworkConnectionError();
            }
        }

    }

}
