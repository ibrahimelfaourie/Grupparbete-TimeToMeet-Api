
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class PlantImageDto implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("plantId")
    private Integer plant;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;
}
