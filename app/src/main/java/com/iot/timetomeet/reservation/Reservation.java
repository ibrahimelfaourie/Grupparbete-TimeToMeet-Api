package com.iot.timetomeet.reservation;

import java.io.Serializable;

import lombok.Data;

@Data
public class Reservation implements Serializable {

    private String roomName;

    private String plantAddress;

    private String reservationDate;

    private String reservationTime;

    private String roomPrice;
}
