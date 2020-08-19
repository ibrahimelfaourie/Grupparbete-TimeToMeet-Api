
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoomImageDto implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("conferenceRoom")
    private Integer conferenceRoom;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

}
