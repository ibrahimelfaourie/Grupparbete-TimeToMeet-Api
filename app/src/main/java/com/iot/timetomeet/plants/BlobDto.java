package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class BlobDto implements Serializable {

    @SerializedName("url")
    private String url;
}
