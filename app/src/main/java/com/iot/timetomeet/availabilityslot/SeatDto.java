
package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeatDto implements Serializable {

    @SerializedName("numberOfSeat")
    private Integer numberOfSeat;
}
