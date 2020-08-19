
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;
import com.iot.timetomeet.availabilityslot.SeatDto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RoomDto implements Serializable {

    @SerializedName("plantId")
    private Integer plantId;

    @SerializedName("conferenceRoomId")
    private Integer conferenceRoomId;

    @SerializedName("conferenceRoomName")
    private String conferenceRoomName;

    @SerializedName("conferenceRoomName_en")
    private String conferenceRoomNameEn;

    @SerializedName("description")
    private String description;

    @SerializedName("description_en")
    private String descriptionEn;

    @SerializedName("priceFrom")
    private Float priceFrom;

    @SerializedName("priceAM")
    private Float priceAM;

    @SerializedName("pricePM")
    private Float pricePM;

    @SerializedName("priceFull")
    private Float priceFull;

    @SerializedName("images")
    private List<ImageDto> imageDtos;

    @SerializedName("maxSeats")
    private List<SeatDto> seatDtos;

    @SerializedName("preNoonAvailabilityHourStart")
    private String preNoonAvailabilityHourStart;

    @SerializedName("preNoonAvailabilityHourEnd")
    private String preNoonAvailabilityHourEnd;

    @SerializedName("afterNoonAvailabilityHourStart")
    private String afterNoonAvailabilityHourStart;

    @SerializedName("afterNoonAvailabilityHourEnd")
    private String afterNoonAvailabilityHourEnd;

}
