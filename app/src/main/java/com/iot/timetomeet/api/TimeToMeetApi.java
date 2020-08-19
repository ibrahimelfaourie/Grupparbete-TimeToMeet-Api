package com.iot.timetomeet.api;

import com.iot.timetomeet.availabilityslot.AvailabilityConferenceRoomDto;
import com.iot.timetomeet.availabilityslot.AvailabilitySlotSearchRequest;
import com.iot.timetomeet.availabilityslot.AvailabilitySlotWrapperDto;
import com.iot.timetomeet.booking.BookingStartRequest;
import com.iot.timetomeet.city.CityDto;
import com.iot.timetomeet.conferenceroom.ConferenceRoomDto;
import com.iot.timetomeet.plants.PlantDetailsSearchRequest;
import com.iot.timetomeet.plants.PlantDto;
import com.iot.timetomeet.plants.PlantsOverviewWrapperDto;
import com.iot.timetomeet.reservationsummary.ReservationSummaryDto;
import com.iot.timetomeet.user.CreateUserRequest;
import com.iot.timetomeet.user.LoginRequest;
import com.iot.timetomeet.user.TokenDto;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TimeToMeetApi {

    @POST("conferenceroomavailability/search/")
    Observable<PlantsOverviewWrapperDto> findAvailableRooms(@Body PlantDetailsSearchRequest plantDetailsSearchRequest);

    @POST("search/availability/period/v3")
    Observable<AvailabilitySlotWrapperDto> findAvailabilitySlot(@Body AvailabilitySlotSearchRequest availabilitySlotSearchRequest);

    @POST("conferenceroomavailability/{id}")
    Observable<AvailabilityConferenceRoomDto> getRoomDetails(@Path("id") String id);

    @GET("conferenceroom/{id}")
    Observable<ConferenceRoomDto> findConferenceRoom(@Path("id") String id);

    @GET("citieswithvenues/")
    Observable<List<CityDto>> listCities();

    @GET("venue/{id}")
    Observable<PlantDto> findPlant(@Path("id") String id);

    @POST("api-token-auth/")
    Observable<TokenDto> login(@Body LoginRequest loginRequest);

    @POST("user/add/")
    Observable<Response<ResponseBody>> createUser(@Body CreateUserRequest createUserRequest);

    @POST("booking/add/")
    Observable<Response<ResponseBody>> bookingStart(@Header("Authorization") String token, @Body BookingStartRequest bookingStartRequest);

    @PUT("conferenceroomavailability/book/{id}/")
    Observable<Response<ResponseBody>> bookConferenceRoom(@Header("Authorization") String token, @Path("id") String id);

    @GET("booking/completed")
    Observable<ReservationSummaryDto> completeBooking(@Header("Authorization") String token);



}

