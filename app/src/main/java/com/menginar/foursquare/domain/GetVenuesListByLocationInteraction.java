package com.menginar.foursquare.domain;

import android.annotation.SuppressLint;

import com.menginar.foursquare.data.model.venueslist.VenuesList;
import com.menginar.foursquare.service.interfaces.FSResponseListener;
import com.menginar.foursquare.service.services.FSService;
import com.menginar.myflamingo.base.baselog.LogType;
import com.menginar.myflamingo.base.baselog.Logger;
import com.menginar.myflamingo.base.infrastructure.Interaction;
import com.menginar.myflamingo.base.infrastructure.InteractionException;
import com.menginar.myflamingo.base.infrastructure.InteractionResult;
import com.menginar.myflamingo.base.infrastructure.InteractionResultListener;

import javax.inject.Inject;

/**
 * Venue Listesi (Locationa gore) için yazılan interaction class
 * */
public class GetVenuesListByLocationInteraction extends Interaction<GetVenuesListByLocationInteraction.Request, VenuesList> {

    private FSService fsService;
    private Logger logger;

    @Inject
    public GetVenuesListByLocationInteraction(FSService fsService, Logger logger) {
        this.fsService = fsService;
        this.logger = logger;
    }


    @Override
    public void execute(InteractionResultListener interactionResultListener) {
        fsService.getVenuesByLocaiton(getIn().placeType, getIn().latlng, new FSResponseListener<VenuesList>() {
            @Override
            public void onSuccess(VenuesList venuesList) {
                interactionResultListener.onResult(new InteractionResult(venuesList));
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onFail(int errorCode, String message, Throwable t) {
                logger.log(LogType.ERROR, "GetVenuesListByLocationInteraction", message, t);
                message = String.format("Error Code %d | Error Message %s", errorCode, message);
                interactionResultListener.onResult(new InteractionResult(new GetVenuesListByLocationInteractionException(message, t)));
            }
        });
    }

    public class GetVenuesListByLocationInteractionException extends InteractionException {

        GetVenuesListByLocationInteractionException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    public static class Request {
        private String placeType;
        private String latlng;

        public Request(String placeType, String latlng) {
            this.placeType = placeType;
            this.latlng = latlng;
        }
    }
}
