package com.iot.timetomeet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.api.TimeToMeetService;
import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.booking.Status;
import com.iot.timetomeet.city.CityDto;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.helper.Helper;
import com.iot.timetomeet.plants.PlantDetails;
import com.iot.timetomeet.plants.PlantDetailsSearchRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlantSearchActivity extends AppCompatActivity {

    private TextView textDateFrom;
    private EditText textPersons;
    private Spinner spnCity;

    private DatePickerDialog.OnDateSetListener textDateFromListener;

    private static List<CityDto> citiesDto;

    private TimeToMeetService timeToMeetService;

    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    public PlantSearchActivity() {
        timeToMeetService = new TimeToMeetService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_search);

        new TimeToMeetDatabaseHelper(this);

        timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);

        spnCity = findViewById(R.id.spinner);
        textDateFrom = findViewById(R.id.txt_from);
        textPersons = findViewById(R.id.txt_persons);

        textDateFrom.setOnClickListener(v -> {
            openDatePicker();
        });

        textDateFromListener = (view, year, month, day) -> textDateFrom.setText(Helper.formatDate(day, month, year));

        timeToMeetDatabaseHelper.deleteAllBookings();

        ArrayAdapter<String> cityArrayAdapter = listCities();
        spnCity.setAdapter(cityArrayAdapter);

        textPersons.setText("50");
        textDateFrom.setText("10/06/2020");
    }

    public ArrayAdapter<String> listCities() {
        citiesDto = timeToMeetService.listCities().subscribeOn(Schedulers.newThread()).blockingSingle();

        List<String> citiesName = Objects.requireNonNull(citiesDto).stream().map(CityDto::getName).collect(Collectors.toList());

        return new ArrayAdapter<>(PlantSearchActivity.this, android.R.layout.simple_spinner_item, citiesName);
    }

    private long lastClickTime = 0;

    public void searchPlants(View view) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();

        PlantDetailsSearchRequest plantDetailsSearchRequest = new PlantDetailsSearchRequest();

        int cityId = citiesDto.stream().filter(cityDto -> cityDto.getName().equals(spnCity.getSelectedItem().toString())).findFirst().map(CityDto::getId).get();
        plantDetailsSearchRequest.setCityId(cityId);

        int seats = Integer.valueOf(textPersons.getText().toString());
        plantDetailsSearchRequest.setSeats(seats);

        plantDetailsSearchRequest.setPage(100);

        String formattedDate = Helper.formatDateTime(textDateFrom.getText().toString());
        plantDetailsSearchRequest.setDateTimeFrom(formattedDate);

        Booking booking = new Booking();
        booking.setDate(formattedDate);
        booking.setSeats(seats);
        booking.setCityId(cityId);
        booking.setStatus(Status.IN_PROGRESS);

        searchPlants(plantDetailsSearchRequest, booking);
    }

    private void searchPlants(PlantDetailsSearchRequest plantDetailsSearchRequest, Booking booking) {
        timeToMeetService.findAvailableRooms(plantDetailsSearchRequest)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(plantsOverviewWrapperDto -> {
                    if (plantsOverviewWrapperDto != null && !plantsOverviewWrapperDto.getPlantsOverviewDto().isEmpty()) {

                        if (timeToMeetDatabaseHelper.getBookingInProgress() == null) {
                            timeToMeetDatabaseHelper.insert(booking);
                        }

                        List<PlantDetails> plantDetailsList = new ArrayList<>();
                        plantsOverviewWrapperDto.getPlantsOverviewDto().forEach(plantsOverviewDto -> {
                            PlantDetails plantDetails = new PlantDetails();


                            plantDetails.setPlantId(String.valueOf(plantsOverviewDto.getPlantId()));

                            String plantName = plantsOverviewDto.getPlantName();

                            if (plantName.length() > 30) {
                                plantName = plantName.substring(0, 30) + "...";
                            }
                            plantDetails.setPlantName(plantName);

                            String address = plantsOverviewDto.getVisitingAddressDto().getStreet() + ", " + plantsOverviewDto.getVisitingAddressDto().getCity();
                            plantDetails.setPlantAddress(address);

                            plantDetails.setPlantPriceFrom(plantsOverviewDto.getPriceFrom());

                            String plantFacts = plantsOverviewDto.getPlantFacts();

                            if (plantFacts.length() > 355) {
                                plantFacts = plantFacts.substring(0, 355) + "...";
                            }

                            plantDetails.setPlantFacts(plantFacts);

                            plantDetails.setRoomsAvailable(plantsOverviewDto.getRoomsAvailable());

                            String imageUrl = Helper.formatImageUrl(plantsOverviewDto.getPlantImageDtos().get(0).getImage());
                            plantDetails.setPlantImage(imageUrl);

                            plantDetailsList.add(plantDetails);
                        });

                        Intent intent = new Intent(PlantSearchActivity.this, PlantDetailsActivity.class);
                        intent.putExtra("plantDetailsList", (Serializable) plantDetailsList);
                        intent.putExtra("callingActivity", ActivityConstants.SEARCH_ACTIVIY);

                        startActivity(intent);
                    } else {
                        Toast.makeText(PlantSearchActivity.this, "Could not find any room available for this date.", Toast.LENGTH_LONG).show();
                    }
                }).subscribe();
    }


    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                PlantSearchActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                textDateFromListener,
                year,
                month,
                day
        );

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}


