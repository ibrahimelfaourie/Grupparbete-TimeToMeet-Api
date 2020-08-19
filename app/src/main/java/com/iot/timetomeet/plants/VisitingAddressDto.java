
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class VisitingAddressDto implements Serializable {

    @SerializedName("street")
    private String street;

    @SerializedName("zip")
    private String zip;

    @SerializedName("city")
    private String city;

    @SerializedName("lat")
    private Float lat;

    @SerializedName("long")
    private Float _long;

}
