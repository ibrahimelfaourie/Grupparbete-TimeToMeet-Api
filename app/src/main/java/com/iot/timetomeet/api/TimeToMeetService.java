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
import retrofit2.Retrofit;

public class TimeToMeetService {

    private static Retrofit retrofit = RetrofitApiFactory.create();

    private static final TimeToMeetApi timeToMeetApi = retrofit.create(TimeToMeetApi.class);

    public Observable<PlantsOverviewWrapperDto> findAvailableRooms(PlantDetailsSearchRequest plantDetailsSearchRequest) {
        return timeToMeetApi.findAvailableRooms(plantDetailsSearchRequest);
    }

    public Observable<AvailabilitySlotWrapperDto> findAvailabilitySlot(AvailabilitySlotSearchRequest availabilitySlotSearchRequest) {
        return timeToMeetApi.findAvailabilitySlot(availabilitySlotSearchRequest);
    }

    public Observable<AvailabilityConferenceRoomDto> getRoomDetails(String id) {
        return timeToMeetApi.getRoomDetails(id);
    }

    public Observable<ConferenceRoomDto> findConferenceRoom(String roomId) {
        return timeToMeetApi.findConferenceRoom(roomId);
    }

    public Observable<List<CityDto>> listCities() {
        return timeToMeetApi.listCities();
    }

    public Observable<PlantDto> findPlant(String plantId) {
        return timeToMeetApi.findPlant(plantId);
    }

    public Observable<TokenDto> login(LoginRequest loginRequest) {
        return timeToMeetApi.login(loginRequest);
    }

    public Observable<Response<ResponseBody>> createUser(CreateUserRequest createUserRequest) {
        return timeToMeetApi.createUser(createUserRequest);
    }

    public Observable<Response<ResponseBody>> startBooking(String token, BookingStartRequest bookingStartRequest) {
        return timeToMeetApi.bookingStart("Token " + token, bookingStartRequest);
    }

    public Observable<Response<ResponseBody>> bookConferenceRoom(String token, String id) {
        return timeToMeetApi.bookConferenceRoom("Token " + token, id);
    }

    public Observable<ReservationSummaryDto> completeBooking(String token) {
        return timeToMeetApi.completeBooking("Token " + token);
    }
}
