package com.iot.timetomeet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.api.TimeToMeetService;
import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.helper.Helper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserReservationActivity extends AppCompatActivity {

    private String morningId = "";

    private TextView txtConfirmationRoomName;
    private TextView txtConfirmationDate;
    private TextView txtConfirmationTime;
    private TextView txtConfirmationPrice;

    private TimeToMeetService timeToMeetService;
    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

        txtConfirmationRoomName = findViewById(R.id.txt_confirmation_room_name);
        txtConfirmationDate = findViewById(R.id.txt_confirmation_date);
        txtConfirmationTime = findViewById(R.id.txt_confirmation_time);
        txtConfirmationPrice = findViewById(R.id.txt_confirmation_price);

        timeToMeetService = new TimeToMeetService();
        timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);

        morningId = getIntent().getStringExtra("morningId");
        String roomId = getIntent().getStringExtra("roomId");
        boolean isFullDay = Boolean.valueOf(getIntent().getStringExtra("isFullDay"));

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();

       if (roomId != null) {
            timeToMeetService.findConferenceRoom(roomId)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(conferenceRoomDto -> {
                txtConfirmationRoomName.setText(conferenceRoomDto.getName());
                txtConfirmationDate.setText(Helper.formatFromIsoToSearchDate(bookingInProgress.getDate()));

                if (isFullDay) {
                    String time = conferenceRoomDto.getPreNoonAvailabilityHourStart() + " - " + conferenceRoomDto.getAfterNoonAvailabilityHourEnd();
                    txtConfirmationTime.setText(time);
                    txtConfirmationPrice.setText(conferenceRoomDto.getFullDayPrice());
                } else {
                    String time;


                    if (morningId == null) {
                        time = conferenceRoomDto.getAfterNoonAvailabilityHourStart() + " - " + conferenceRoomDto.getAfterNoonAvailabilityHourEnd();
                        txtConfirmationPrice.setText(conferenceRoomDto.getAfterNoonPrice());
                    }
                    else {
                        time = conferenceRoomDto.getPreNoonAvailabilityHourStart() + " - " + conferenceRoomDto.getPreNoonAvailabilityHourEnd();
                        txtConfirmationPrice.setText(conferenceRoomDto.getPreNoonPrice());
                    }

                    txtConfirmationTime.setText(time);
                }
            })
            .subscribe();
        }
    }

    public void onClickBook(View view) {
        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();

        timeToMeetService.completeBooking(bookingInProgress.getToken()).doOnComplete(() -> {
            Intent intent = new Intent(UserReservationActivity.this, BookingConfirmationActivity.class);
            intent.putExtra("callingActivity", ActivityConstants.USER_REGISTRATION_ACTIVITY);
            startActivity(intent);
            finish();
        }).subscribe();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
