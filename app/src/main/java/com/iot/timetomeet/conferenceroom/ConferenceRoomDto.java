package com.iot.timetomeet.conferenceroom;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ConferenceRoomDto implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("name_en")
    private String nameEn;

    @SerializedName("description")
    private String description;

    @SerializedName("description_en")
    private String descriptionEn;

    @SerializedName("plantId")
    private Integer plantId;

    @SerializedName("fullDayPrice")
    private String fullDayPrice;

    @SerializedName("preNoonPrice")
    private String preNoonPrice;

    @SerializedName("afterNoonPrice")
    private String afterNoonPrice;

    @SerializedName("showSizeInsteadOfName")
    private Boolean showSizeInsteadOfName;

    @SerializedName("preNoonAvailabilityHourStart")
    private String preNoonAvailabilityHourStart;

    @SerializedName("preNoonAvailabilityHourEnd")
    private String preNoonAvailabilityHourEnd;

    @SerializedName("afterNoonAvailabilityHourStart")
    private String afterNoonAvailabilityHourStart;

    @SerializedName("afterNoonAvailabilityHourEnd")
    private String afterNoonAvailabilityHourEnd;

    @SerializedName("defaultSeating")
    private List<DefaultSeatingDto> defaultSeating = null;

    @SerializedName("conferenceRoomBlob")
    private List<Integer> conferenceRoomBlob = null;

    @SerializedName("state")
    private Integer state;

    @SerializedName("technologies")
    private List<TechnologyDto> technologies = null;

    @SerializedName("seats")
    private List<SeatDto> seats = null;

    @SerializedName("blobs")
    private List<BlobDto> blobs = null;

    private class BlobDto {

        @SerializedName("id")
        private Integer id;

        @SerializedName("name")
        private String name;

        @SerializedName("name_en")
        private Object nameEn;

        @SerializedName("description")
        private String description;

        @SerializedName("description_en")
        private String descriptionEn;

        @SerializedName("server")
        private String server;

        @SerializedName("path")
        private String path;

        @SerializedName("fileName")
        private String fileName;

        @SerializedName("url")
        private String url;
    }

    private class DefaultSeatingDto {

        @SerializedName("id")
        private Integer id;

        @SerializedName("conferenceRoom")
        private Integer conferenceRoom;

        @SerializedName("standardSeating")
        private Integer standardSeating;

        @SerializedName("numberOfSeat")
        private Integer numberOfSeat;

        @SerializedName("conferenceRoomBlob")
        private Object conferenceRoomBlob;
    }

    private class SeatDto {

        @SerializedName("id")
        private Integer id;

        @SerializedName("name")
        private String name;

        @SerializedName("name_en")
        private String nameEn;

        @SerializedName("iconPath")
        private String iconPath;
    }

    private class TechnologyDto {

        @SerializedName("id")
        private Integer id;

        @SerializedName("name")
        private String name;

        @SerializedName("name_en")
        private String nameEn;

        @SerializedName("technologyGroup")
        private Integer technologyGroup;

        @SerializedName("state_id")
        private Integer stateId;
    }
}

