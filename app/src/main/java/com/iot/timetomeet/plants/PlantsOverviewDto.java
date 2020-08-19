
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;
import com.iot.timetomeet.availabilityslot.SeatDto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PlantsOverviewDto implements Serializable {

    @SerializedName("roomsAvailable")
    private Integer roomsAvailable;

    @SerializedName("plantId")
    private Integer plantId;

    @SerializedName("priceFrom")
    private Float priceFrom;

    @SerializedName("roomName")
    private String roomName;

    @SerializedName("plantName")
    private String plantName;

    @SerializedName("plantFacts")
    private String plantFacts;

    @SerializedName("visitingAddress")
    private VisitingAddressDto visitingAddressDto;

    @SerializedName("seats")
    private List<SeatDto> seatDtos = null;

    @SerializedName("plantImages")
    private List<PlantImageDto> plantImageDtos = null;

    @SerializedName("roomImages")
    private List<RoomImageDto> roomImageDtos = null;
}
