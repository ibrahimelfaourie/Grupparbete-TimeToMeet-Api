package com.iot.timetomeet.city;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CityDto {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("name_en")
    private String nameEn;
}