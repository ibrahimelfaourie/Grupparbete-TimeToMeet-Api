package com.iot.timetomeet.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CreateUserRequest {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("organization_name")
    private String organizationName;

    @SerializedName("organization_nr")
    private String organizationNr;

    @SerializedName("street")
    private String street;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("zipCode")
    private String zipCode;
}
