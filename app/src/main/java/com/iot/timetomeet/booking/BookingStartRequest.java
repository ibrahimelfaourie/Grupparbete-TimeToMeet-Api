package com.iot.timetomeet.booking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class BookingStartRequest implements Serializable {

    @SerializedName("paymentAlternative")
    private String paymentAlternative;

    @SerializedName("wantHotelRoomInfo")
    private String wantHotelRoomInfo;

    @SerializedName("wantActivityInfo")
    private String wantActivityInfo;

    @SerializedName("specialRequest")
    private String specialRequest;

    @SerializedName("numberOfParticipants")
    private String numberOfParticipants;

    @SerializedName("bookingSourceSystem")
    private String bookingSourceSystem;

    @SerializedName("agreementNumber")
    private String agreementNumber;
}
