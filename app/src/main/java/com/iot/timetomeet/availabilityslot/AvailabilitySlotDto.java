package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AvailabilitySlotDto {

    @SerializedName("fullDayPrice")
    private String fullDayPrice;

    @SerializedName("preNoonPrice")
    private String preNoonPrice;

    @SerializedName("afterNoonPrice")
    private String afterNoonPrice;

    @SerializedName("id31")
    private Integer morningSlotId;

    @SerializedName("id32")
    private Integer afternoonSlotId;

    @SerializedName("room_id")
    private int roomId;

    @SerializedName("plant_id")
    private int plantId;
}
