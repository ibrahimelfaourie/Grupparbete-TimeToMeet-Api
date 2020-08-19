package com.iot.timetomeet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.api.TimeToMeetService;
import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.user.LoginRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;

    private EditText txtPassword;

    private TimeToMeetService timeToMeetService;

    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();
        if (bookingInProgress.getToken() != null) {
            int callingActivity = getIntent().getIntExtra("callingActivity", 0);

            Intent intent;

            if (callingActivity == ActivityConstants.PLANT_DETAILS_ACTIVITY) {
                intent = new Intent(LoginActivity.this, RoomDetailsActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, PlantSearchActivity.class);
            }

            intent.putExtra("callingActivity", ActivityConstants.LOGIN_ACTIVITY);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        timeToMeetService = new TimeToMeetService();

        txtUsername = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
    }

    @SuppressLint("CheckResult")
    public void authenticate(View view) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(txtUsername.getText().toString());
        loginRequest.setPassword(txtPassword.getText().toString());

        timeToMeetService.login(loginRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenDto -> {
                    Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();
                    bookingInProgress.setToken(tokenDto.getToken());

                    timeToMeetDatabaseHelper.update(bookingInProgress);

                    Toast.makeText(LoginActivity.this, "Authenticated successfully!.", Toast.LENGTH_LONG).show();
                    finish();
                }, throwable -> Toast.makeText(LoginActivity.this, "Wrong username or password.", Toast.LENGTH_LONG).show());
    }

    public void registerUser(View view) {
        Intent intent = new Intent(LoginActivity.this, UserRegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
