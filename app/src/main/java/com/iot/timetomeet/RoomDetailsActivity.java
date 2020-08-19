package com.iot.timetomeet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.api.TimeToMeetService;
import com.iot.timetomeet.availabilityslot.AvailabilityConferenceRoomDto;
import com.iot.timetomeet.availabilityslot.AvailabilitySlotSearchRequest;
import com.iot.timetomeet.availabilityslot.AvailabilitySlotWrapperDto;
import com.iot.timetomeet.availabilityslot.SeatDto;
import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.booking.BookingStartRequest;
import com.iot.timetomeet.components.RoomDetailListView;
import com.iot.timetomeet.conferenceroom.ConferenceRoom;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.helper.Helper;
import com.iot.timetomeet.plants.PlantDetails;
import com.iot.timetomeet.plants.PlantDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RoomDetailsActivity extends AppCompatActivity {

    private ListView ltv_plants;

    private TimeToMeetService timeToMeetService;

    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    private RoomDetailListView roomDetailListView;

    private static List<ConferenceRoom> conferenceRooms;

    public static Activity roomDetailsActivity;

    public RoomDetailsActivity() {
        this.timeToMeetService = new TimeToMeetService();
        this.timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        roomDetailsActivity = this;

        TextView textPlantName = findViewById(R.id.txt_plant_title);
        ImageView imagePlant = findViewById(R.id.img_plant_detail);
        ltv_plants = findViewById(R.id.ltv_plants);

        roomDetailListView = new RoomDetailListView(RoomDetailsActivity.this, new ArrayList<>());

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();

        findAvailabilitySlot(bookingInProgress);

        PlantDto plantDto = timeToMeetService.findPlant(String.valueOf(bookingInProgress.getPlantId())).blockingSingle();
        textPlantName.setText(plantDto.getName());
        String imageUrl = Helper.formatImageUrl(plantDto.getBlobsDto().get(0).getUrl());
        Picasso.get().load(imageUrl).into(imagePlant);


        BookingStartRequest bookingStartRequest = buildBookingStartRequest(bookingInProgress);

        timeToMeetService.startBooking(bookingInProgress.getToken(), bookingStartRequest)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    private List<ConferenceRoom> findAvailabilitySlot(Booking booking) {
        conferenceRooms = new ArrayList<>();
        AvailabilitySlotSearchRequest availabilitySlotSearchRequest = buildAvailabilitySlotSearchRequest(booking);

        AvailabilitySlotWrapperDto availabilitySlotWrapperDto = timeToMeetService.findAvailabilitySlot(availabilitySlotSearchRequest)
                .subscribeOn(Schedulers.newThread())
                .blockingSingle();

        if (availabilitySlotWrapperDto.getAvailabilitySlotsDto().isEmpty()) {
            PlantDetails plantDetailsToDelete = PlantDetailsActivity.plantDetailsList.stream()
                    .filter(plantDetails -> plantDetails.getPlantId().equals(String.valueOf(availabilitySlotSearchRequest.getObjectIds())))
                    .findFirst().get();

            PlantDetailsActivity.plantDetailsList.remove(plantDetailsToDelete);
            PlantDetailsActivity.plantDetailListView.notifyDataSetChanged();

            Toast.makeText(RoomDetailsActivity.this, "Ops! This plant does not have any conference room available!", Toast.LENGTH_LONG).show();
            finish();
        }

        availabilitySlotWrapperDto.getAvailabilitySlotsDto().forEach(availabilitySlotDto -> {
            Observable.zip(
                    getRoomDetails(availabilitySlotDto.getMorningSlotId()),
                    getRoomDetails(availabilitySlotDto.getAfternoonSlotId()),
                    (morningConferenceRoomDto, afternoonConferenceRoomDto) -> {
                        ConferenceRoom conferenceRoom = new ConferenceRoom();

                        if (morningConferenceRoomDto != null && morningConferenceRoomDto.getConferenceRoomName() != null) {
                            Integer getMaxSeat = morningConferenceRoomDto.getSeatsDto().stream().map(SeatDto::getNumberOfSeat).max(Integer::compareTo).get();
                            conferenceRoom.setMaxSeats(getMaxSeat);
                            conferenceRoom.setConferenceRoomId(String.valueOf(morningConferenceRoomDto.getConferenceRoom()));

                            conferenceRoom.setHasMorningSlot(true);
                            conferenceRoom.setRoomId(String.valueOf(morningConferenceRoomDto.getId()));
                            conferenceRoom.setRoomName(morningConferenceRoomDto.getConferenceRoomName());
                            conferenceRoom.setMorningSlotId(morningConferenceRoomDto.getId());
                            conferenceRoom.setMorningSlotPrice(morningConferenceRoomDto.getPreNoonPrice());
                            conferenceRoom.setAfternoonSlotPrice(morningConferenceRoomDto.getAfterNoonPrice());

                            conferenceRoom.setFulldaySlotPrice(morningConferenceRoomDto.getFullDayPrice());
                        }

                        if (afternoonConferenceRoomDto != null && afternoonConferenceRoomDto.getConferenceRoomName() != null) {
                            Integer getMaxSeat = afternoonConferenceRoomDto.getSeatsDto().stream().map(SeatDto::getNumberOfSeat).max(Integer::compareTo).get();
                            conferenceRoom.setMaxSeats(getMaxSeat);
                            conferenceRoom.setConferenceRoomId(String.valueOf(afternoonConferenceRoomDto.getConferenceRoom()));

                            conferenceRoom.setHasAfternoonSlot(true);
                            conferenceRoom.setRoomId(String.valueOf(afternoonConferenceRoomDto.getId()));
                            conferenceRoom.setRoomName(afternoonConferenceRoomDto.getConferenceRoomName());

                            conferenceRoom.setAfternoonSlotId(afternoonConferenceRoomDto.getId());
                            conferenceRoom.setMorningSlotPrice(afternoonConferenceRoomDto.getPreNoonPrice());
                            conferenceRoom.setAfternoonSlotPrice(afternoonConferenceRoomDto.getAfterNoonPrice());

                            conferenceRoom.setFulldaySlotPrice(afternoonConferenceRoomDto.getFullDayPrice());
                        }

                        conferenceRooms.add(conferenceRoom);

                        return conferenceRoom;
                    })
                    .delay(3, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(conferenceRoom -> {
                        roomDetailListView = new RoomDetailListView(RoomDetailsActivity.this, conferenceRooms);

                        ltv_plants.setAdapter(roomDetailListView);

                        roomDetailListView.notifyDataSetChanged();

                        ProgressBar progressBar = findViewById(R.id.progress_loader);
                        progressBar.setVisibility(View.INVISIBLE);

//                        conferenceRooms = new ArrayList<>();
                    });
        });

        return conferenceRooms;
    }

    private Observable<AvailabilityConferenceRoomDto> getRoomDetails(Integer slotId) {
        if (slotId == null) {
            return Observable.just(new AvailabilityConferenceRoomDto());
        }

        return timeToMeetService.getRoomDetails(String.valueOf(slotId));
    }

    private BookingStartRequest buildBookingStartRequest(Booking booking) {
        BookingStartRequest bookingStartRequest = new BookingStartRequest();
        bookingStartRequest.setPaymentAlternative("1");
        bookingStartRequest.setNumberOfParticipants(String.valueOf(booking.getSeats()));
        bookingStartRequest.setAgreementNumber("");
        bookingStartRequest.setBookingSourceSystem("1");
        bookingStartRequest.setSpecialRequest("");
        bookingStartRequest.setWantActivityInfo("false");
        bookingStartRequest.setWantHotelRoomInfo("false");

        return bookingStartRequest;
    }

    private AvailabilitySlotSearchRequest buildAvailabilitySlotSearchRequest(Booking booking) {
        AvailabilitySlotSearchRequest availabilitySlotSearchRequest = new AvailabilitySlotSearchRequest();

        String searchedDate = Helper.formatFromIsoToSearchDate(booking.getDate());
        availabilitySlotSearchRequest.setFromDate(searchedDate);
        availabilitySlotSearchRequest.setToDate(searchedDate);
        availabilitySlotSearchRequest.setObjectType("plant");
        availabilitySlotSearchRequest.setObjectIds(booking.getPlantId());

        return availabilitySlotSearchRequest;
    }

    private long lastClickTime = 0;

    @SuppressLint("CheckResult")
    public void reserveRoom(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();

        String[] btnTag = (String[]) view.getTag();

        int checkedRadioButtonId = ((RadioGroup) findViewById(Integer.valueOf(btnTag[0]))).getCheckedRadioButtonId();
        RadioButton checkedRadioButton = findViewById(checkedRadioButtonId);

        String isFullday = "false";
        String morningId = null;
        String afternoonId = null;

        String idPattern = checkedRadioButton.getTag().toString();

        if (idPattern.contains("_")) {
            String[] ids = idPattern.split("_");
            if (ids.length > 1) {
                isFullday = "true";
                morningId = ids[0];
                afternoonId = ids[1];
            } else {
                morningId = ids[0];
            }

        } else {
            afternoonId = idPattern;
        }

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();

        if (morningId != null && Integer.valueOf(morningId) > 0) {
            subscribeConferenceRoom(bookingInProgress.getToken(), morningId);
        }

        if (afternoonId != null && Integer.valueOf(afternoonId) > 0) {
            subscribeConferenceRoom(bookingInProgress.getToken(), afternoonId);
        }

        Intent intent = new Intent(RoomDetailsActivity.this, UserReservationActivity.class);
        intent.putExtra("callingActivity", ActivityConstants.ROOM_DETAILS_ACTIVITY);
        intent.putExtra("isFullDay", isFullday);
        intent.putExtra("morningId", morningId);
        intent.putExtra("afternoonId", afternoonId);
        intent.putExtra("roomId", String.valueOf(btnTag[1]));
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    private void subscribeConferenceRoom(String token, String id) {
        timeToMeetService.bookConferenceRoom(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBodyResponse -> {
                    if (!responseBodyResponse.isSuccessful()) {
                        Toast.makeText(RoomDetailsActivity.this, "Something went wrong when booking the room.", Toast.LENGTH_LONG).show();
                    }
                }, throwable -> Toast.makeText(RoomDetailsActivity.this, "Something went wrong when booking the room.", Toast.LENGTH_LONG).show());
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
