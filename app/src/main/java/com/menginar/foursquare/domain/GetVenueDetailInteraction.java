package com.menginar.foursquare.domain;

import android.annotation.SuppressLint;

import com.menginar.foursquare.data.model.venuedetail.VenueDetail;
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
 * Venue detay için yazılan interaction class
 * */
public class GetVenueDetailInteraction extends Interaction<GetVenueDetailInteraction.Request, VenueDetail> {

    private FSService fsService;
    private Logger logger;

    @Inject
    public GetVenueDetailInteraction(FSService fsService, Logger logger) {
        this.fsService = fsService;
        this.logger = logger;
    }

    @Override
    public void execute(InteractionResultListener interactionResultListener) {
        fsService.getVenueDetail(getIn().venueId, new FSResponseListener<VenueDetail>() {
            @Override
            public void onSuccess(VenueDetail venueDetail) {
                interactionResultListener.onResult(new InteractionResult(venueDetail));
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onFail(int errorCode, String message, Throwable t) {
                logger.log(LogType.ERROR, "GetVenueDetailInteraction", message);
                message = String.format("Error Code %d | Error Message %s", errorCode, message);
                interactionResultListener.onResult(new InteractionResult(new GetVenueDetailInteractionException(message, t)));
            }
        });
    }


    public class GetVenueDetailInteractionException extends InteractionException {

        GetVenueDetailInteractionException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }

    public static class Request {
        private String venueId;

        public Request(String venueId) {
            this.venueId = venueId;
        }
    }
}
