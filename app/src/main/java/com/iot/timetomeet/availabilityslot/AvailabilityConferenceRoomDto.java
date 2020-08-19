
package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AvailabilityConferenceRoomDto implements Serializable {

    @SerializedName("id")
    public Integer id;

    @SerializedName("conferenceRoom")
    public Integer conferenceRoom;

    @SerializedName("fullDayPrice")
    public String fullDayPrice;

    @SerializedName("preNoonPrice")
    public String preNoonPrice;

    @SerializedName("afterNoonPrice")
    public String afterNoonPrice;

    @SerializedName("conferenceRoomName")
    public String conferenceRoomName;

    @SerializedName("seats")
    public List<SeatDto> seatsDto = null;

    @SerializedName("image")
    public List<ImageDto> imageDto = null;
}
