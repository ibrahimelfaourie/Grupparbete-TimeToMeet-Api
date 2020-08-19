
package com.iot.timetomeet.plants;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PlantsOverviewWrapperDto implements Serializable {

    @SerializedName("plantsOverview")
    private List<PlantsOverviewDto> plantsOverviewDto;

}
