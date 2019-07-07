package com.menginar.foursquare.view.places;

import com.menginar.foursquare.data.model.venueslist.Venue;
import com.menginar.myflamingo.base.pages.PresenterContract;
import com.menginar.myflamingo.base.pages.ViewContract;

import java.util.List;

public class PlacesContract {

    public interface PlacesViewContract extends ViewContract {

        void showloader();

        void hideloader();

        void onError();

        void setVenuesList(List<Venue> venuesList);

    }

    public interface PlacesPresenterContract extends PresenterContract<PlacesViewContract> {
        void getVenueListData(String venuesJsonObject);
    }
}
