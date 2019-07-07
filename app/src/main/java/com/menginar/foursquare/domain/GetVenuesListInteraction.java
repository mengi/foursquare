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
 * Venue List(Category girilerek) için yazılan interaction class
 * */

public class GetVenuesListInteraction extends Interaction<GetVenuesListInteraction.Request, VenuesList> {

    private FSService fsService;
    private Logger logger;

    @Inject
    public GetVenuesListInteraction(FSService fsService, Logger logger) {
        this.fsService = fsService;
        this.logger = logger;
    }

    @Override
    public void execute(InteractionResultListener<VenuesList> interactionResultListener) {
        fsService.getVenuesList(getIn().placeType, getIn().near, new FSResponseListener<VenuesList>() {
            @Override
            public void onSuccess(VenuesList venuesList) {
                interactionResultListener.onResult(new InteractionResult<>(venuesList));
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onFail(int errorCode, String message, Throwable t) {
                logger.log(LogType.ERROR, "GetVenuesListInteraction", message, t);
                message = String.format("Error Code %d | Error Message %s", errorCode, message);
                interactionResultListener.onResult(new InteractionResult<>(new GetVenuesListInteractionException(message, t)));
            }
        });
    }

    public class GetVenuesListInteractionException extends InteractionException {

        GetVenuesListInteractionException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    public static class Request {
        private String placeType;
        private String near;

        public Request(String placeType, String near) {
            this.placeType = placeType;
            this.near = near;
        }
    }
}
