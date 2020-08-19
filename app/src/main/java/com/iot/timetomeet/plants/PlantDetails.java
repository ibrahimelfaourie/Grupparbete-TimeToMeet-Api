package com.iot.timetomeet.plants;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlantDetails implements Serializable {

    private String plantId;

    private String plantName;

    private String plantFacts;

    private String plantAddress;

    private String plantImage;

    private Float plantPriceFrom;

    private int roomsAvailable;
}
