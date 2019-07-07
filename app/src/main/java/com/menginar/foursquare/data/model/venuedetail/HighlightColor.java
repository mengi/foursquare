
package com.menginar.foursquare.data.model.venuedetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HighlightColor {

    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
