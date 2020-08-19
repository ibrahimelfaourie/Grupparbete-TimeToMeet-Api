package com.iot.timetomeet.availabilityslot;

import lombok.Data;

@Data
public class AvailabilitySlotSearchRequest {

    private int objectIds; // plantId id

    private String objectType; // plantId

    private String fromDate;

    private String toDate; // dd.mm.yyyy
}
