package com.iot.timetomeet.plants;

import lombok.Data;

@Data
public class PlantDetailsSearchRequest {

    private int cityId;

    private int seats;

    private String dateTimeFrom;

    private int page = 100;
}
