
package com.menginar.foursquare.data.model.venuedetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabeledLatLng {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
