package com.iot.timetomeet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.components.PlantDetailListView;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.plants.PlantDetails;

import java.util.List;

public class PlantDetailsActivity extends AppCompatActivity {

    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    public static Activity plantDetailsActivity;

    public static List<PlantDetails> plantDetailsList;

    public static PlantDetailListView plantDetailListView;

    public PlantDetailsActivity() {
        this.timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        plantDetailsActivity = this;

        ListView ltv_rooms = findViewById(R.id.ltv_plants);

        Intent intent = getIntent();
        plantDetailsList = (List<PlantDetails>) intent.getSerializableExtra("plantDetailsList");

        plantDetailListView = new PlantDetailListView(this, plantDetailsList);

        ltv_rooms.setAdapter(plantDetailListView);
    }

    private long lastClickTime = 0;

    public void openLogin(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();

        String plantId = (String) view.getTag();

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();

        bookingInProgress.setPlantId(Integer.parseInt(plantId));
        timeToMeetDatabaseHelper.update(bookingInProgress);

        if (bookingInProgress.getToken() != null && !bookingInProgress.getToken().isEmpty()) {
            Intent intent = new Intent(PlantDetailsActivity.this, RoomDetailsActivity.class);
            startActivity(intent);

            return;
        }

        Intent intent = new Intent(PlantDetailsActivity.this, LoginActivity.class);
        intent.putExtra("callingActivity", ActivityConstants.PLANT_DETAILS_ACTIVITY);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
