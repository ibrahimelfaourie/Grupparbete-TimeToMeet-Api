package com.iot.timetomeet.booking;

import lombok.Data;

@Data
public class Booking {

    private int id;

    private String date;

    private String time;

    private int seats;

    private int cityId;

    private int plantId;

    private int roomId;

    private int conferenceRoomId;

    private String userEmail;

    private Status status;

    private String token;
}
