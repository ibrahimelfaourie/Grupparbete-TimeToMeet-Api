
package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class DefaultSeatingDto implements Serializable {

    @SerializedName("numberOfSeat")
    public Integer numberOfSeat;

}
