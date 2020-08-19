package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AvailabilitySlotWrapperDto {

    @SerializedName("result")
    private List<AvailabilitySlotDto> availabilitySlotsDto;
}
