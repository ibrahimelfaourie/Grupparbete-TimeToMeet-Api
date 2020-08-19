package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImageDto implements Serializable {

    @SerializedName("image")
    private String image;
}
