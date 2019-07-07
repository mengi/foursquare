package com.menginar.foursquare.view.main;

import android.location.Location;

import com.menginar.foursquare.data.model.venueslist.Meta;
import com.menginar.myflamingo.base.infrastructure.InteractionException;
import com.menginar.myflamingo.base.pages.PresenterContract;
import com.menginar.myflamingo.base.pages.ViewContract;

public class MainContract {

    public interface MainViewContract extends ViewContract {

        void showLoader();

        void hideLoader();

        void setVenuesListData(String venuesJsonObject);

        void setVenuesListInteractionException(InteractionException exception);

        void onError(Meta meta);

        void onNetworkConnectionError();
    }

    public interface MainPresenterContract extends PresenterContract<MainViewContract> {

        void getVenueListByLocation(Location location, String placeType);

        void getVenueList(String placeType, String near);
    }
}
