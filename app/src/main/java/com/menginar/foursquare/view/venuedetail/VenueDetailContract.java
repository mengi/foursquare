package com.menginar.foursquare.view.venuedetail;

import android.widget.Toast;

import com.menginar.foursquare.R;
import com.menginar.foursquare.data.model.venuedetail.Item_;
import com.menginar.foursquare.data.model.venuedetail.Meta;
import com.menginar.foursquare.view.main.MainActivity;
import com.menginar.myflamingo.base.infrastructure.InteractionException;
import com.menginar.myflamingo.base.pages.PresenterContract;
import com.menginar.myflamingo.base.pages.ViewContract;

import java.util.List;

public class VenueDetailContract {

    public interface VenueDetailViewContract extends ViewContract {

        void onRunOnUiThread(Runnable runnable);

        void setMapLatLng(double lat, double lng, String venueName);

        void setImageVenue(String imgeUrl);

        void setTextVenueName(String venueName);

        void setUserComments(List<Item_> items);

        void onError(InteractionException exception);

        void onErrorNull(boolean isImageUrlNull, boolean isCommentsNull);

        void onErrorMeta(Meta meta);

        void onNetworkConnectionError();

    }

    public interface VenueDetailPresenterContract extends PresenterContract<VenueDetailViewContract> {

        void getVenueData(String venueJsonObject);
    }
}
