
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeatDto implements Serializable {

    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("name_en")
    public String nameEn;

    @SerializedName("iconPath")
    public String iconPath;

}
