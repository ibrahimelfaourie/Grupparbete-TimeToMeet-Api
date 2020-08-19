package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PlantDto implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("maxSeats")
    private List<SeatDto> seatsDto = null;

    @SerializedName("blobs")
    private List<BlobDto> blobsDto = null;
}
