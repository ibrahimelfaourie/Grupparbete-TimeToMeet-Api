package com.iot.timetomeet.conferenceroom;

import java.io.Serializable;

import lombok.Data;

@Data
public class ConferenceRoom implements Serializable {

    private String roomId;

    private String conferenceRoomId;

    private String roomName;

    private int maxSeats;

    private boolean hasMorningSlot;

    private boolean hasAfternoonSlot;

    private int morningSlotId;

    private int afternoonSlotId;

    private String morningSlotPrice;

    private String afternoonSlotPrice;

    private String fulldaySlotPrice;
}
