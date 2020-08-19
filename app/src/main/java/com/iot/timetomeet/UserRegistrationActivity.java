package com.iot.timetomeet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iot.timetomeet.api.TimeToMeetService;
import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.db.TimeToMeetDatabaseHelper;
import com.iot.timetomeet.user.CreateUserRequest;
import com.iot.timetomeet.user.LoginRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserRegistrationActivity extends AppCompatActivity {

    private TextView txtCompanyAddress;
    private TextView txtRegisterNumber;
    private TextView txtCompanyName;
    private TextView txtPhoneNumber;
    private TextView txtUserEmail;
    private TextView txtLastName;
    private TextView txtFirstName;
    private TextView txtZipcode;
    private TextView txtCompanyCity;
    private TextView txtPassword;
    private TextView txtConfirmationPassword;

    private TimeToMeetService timeToMeetService;

    private TimeToMeetDatabaseHelper timeToMeetDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        timeToMeetDatabaseHelper = new TimeToMeetDatabaseHelper(this);

        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();
        if (bookingInProgress.getToken() != null) {
            Intent intent = new Intent(UserRegistrationActivity.this, LoginActivity.class);
            intent.putExtra("callingActivity", ActivityConstants.USER_REGISTRATION_ACTIVITY);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        timeToMeetService = new TimeToMeetService();

        txtCompanyAddress = findViewById(R.id.txt_company_address);
        txtRegisterNumber = findViewById(R.id.txt_register_number);
        txtCompanyName = findViewById(R.id.txt_company_name);
        txtPhoneNumber = findViewById(R.id.txt_phone_number);
        txtUserEmail = findViewById(R.id.txt_user_email);
        txtLastName = findViewById(R.id.txt_last_name);
        txtFirstName = findViewById(R.id.txt_first_name);
        txtZipcode = findViewById(R.id.txt_zipcode);
        txtCompanyCity = findViewById(R.id.txt_company_city);
        txtPassword = findViewById(R.id.txt_user_password);
        txtConfirmationPassword = findViewById(R.id.txt_user_confirm_password);
    }

    private long lastClickTime = 0;

    @SuppressLint("CheckResult")
    public void registerUser(View ciew) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return;
        }

        lastClickTime = SystemClock.elapsedRealtime();

        if (!txtPassword.getText().toString().equals(txtConfirmationPassword.getText().toString())) {
            Toast.makeText(UserRegistrationActivity.this, "Password doesn't match", Toast.LENGTH_LONG).show();
            return;
        }

        CreateUserRequest createUserRequest = new CreateUserRequest();

        createUserRequest.setStreet(txtCompanyAddress.getText().toString());
        createUserRequest.setOrganizationNr(txtRegisterNumber.getText().toString());
        createUserRequest.setOrganizationName(txtCompanyName.getText().toString());
        createUserRequest.setPhoneNumber(txtPhoneNumber.getText().toString());
        createUserRequest.setUsername("z_" + txtUserEmail.getText().toString());
        createUserRequest.setEmail(txtUserEmail.getText().toString());
        createUserRequest.setLastName(txtLastName.getText().toString());
        createUserRequest.setFirstName(txtFirstName.getText().toString());
        createUserRequest.setZipCode(txtZipcode.getText().toString());
        createUserRequest.setCityName(txtCompanyCity.getText().toString());
        createUserRequest.setPassword(txtPassword.getText().toString());

        timeToMeetService.createUser(createUserRequest)
                .subscribe(responseBodyResponse -> {
                            if (!responseBodyResponse.isSuccessful()) {
                                Toast.makeText(UserRegistrationActivity.this, "Could not create user.", Toast.LENGTH_LONG).show();
                                return;
                            }

                            LoginRequest loginRequest = new LoginRequest(createUserRequest.getUsername(), createUserRequest.getPassword());
                            timeToMeetService.login(loginRequest)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(tokenDto -> {
                                        Booking bookingInProgress = timeToMeetDatabaseHelper.getBookingInProgress();
                                        bookingInProgress.setToken(tokenDto.getToken());

                                        timeToMeetDatabaseHelper.update(bookingInProgress);

                                        Toast.makeText(UserRegistrationActivity.this, "User created successfully!.", Toast.LENGTH_LONG).show();
                                    }, throwable -> Toast.makeText(UserRegistrationActivity.this, "Could not authenticate user created.", Toast.LENGTH_LONG).show());
                        },
                        throwable -> Toast.makeText(UserRegistrationActivity.this, "Could not create user.", Toast.LENGTH_LONG).show(),
                        () -> {
                            Intent intent = new Intent(UserRegistrationActivity.this, RoomDetailsActivity.class);
                            intent.putExtra("callingActivity", ActivityConstants.USER_REGISTRATION_ACTIVITY);
                            startActivity(intent);
                            finish();
                        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
