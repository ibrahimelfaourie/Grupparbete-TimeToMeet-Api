
package com.iot.timetomeet.availabilityslot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class TechnologyDto implements Serializable {

    @SerializedName("id")
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("technologyGroup")
    public Integer technologyGroup;
}
