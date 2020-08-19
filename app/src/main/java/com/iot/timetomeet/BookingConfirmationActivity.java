package com.iot.timetomeet;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BookingConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
    }

    @Override
    public void onBackPressed() {
        RoomDetailsActivity.roomDetailsActivity.finish();
        PlantDetailsActivity.plantDetailsActivity.finish();
        finish();
    }
}
