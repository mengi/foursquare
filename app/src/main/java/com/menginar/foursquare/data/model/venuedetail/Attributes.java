
package com.menginar.foursquare.data.model.venuedetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributes {

    @SerializedName("groups")
    @Expose
    private List<Group_____> groups = null;

    public List<Group_____> getGroups() {
        return groups;
    }

    public void setGroups(List<Group_____> groups) {
        this.groups = groups;
    }

}
