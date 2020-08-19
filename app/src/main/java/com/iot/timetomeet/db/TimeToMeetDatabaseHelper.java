package com.iot.timetomeet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iot.timetomeet.booking.Booking;
import com.iot.timetomeet.booking.Status;

public class TimeToMeetDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TimeToMeet.db";
    public static final int DATABASE_VERSION = 2;

    public static final String BOOKING_TABLE_NAME = "booking_table";
    public static final String BOOKING_CREATE_TABLE_QUERY = "CREATE TABLE " + BOOKING_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, TIME TEXT, SEATS INTEGER, CITY_ID INTEGER, PLANT_ID INTEGER, ROOM_ID INTEGER, CONFERENCE_ROOM_ID INTEGER, USER_EMAIL TEXT, STATUS TEXT, TOKEN TEXT)";
    public static final String BOOKING_DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME;
    public static final String BOOKING_COL_ID = "ID";
    public static final String BOOKING_COL_DATE = "DATE";
    public static final String BOOKING_COL_TIME = "TIME";
    public static final String BOOKING_COL_SEATS = "SEATS";
    public static final String BOOKING_COL_CITY_ID = "CITY_ID";
    public static final String BOOKING_COL_PLANT_ID = "PLANT_ID";
    public static final String BOOKING_COL_ROOM_ID = "ROOM_ID";
    public static final String BOOKING_COL_CONFERENCE_ROOM_ID = "CONFERENCE_ROOM_ID";
    public static final String BOOKING_COL_USER_EMAIL = "USER_EMAIL";
    public static final String BOOKING_COL_STATUS = "STATUS";
    public static final String BOOKING_COL_TOKEN = "TOKEN";
    public static final String[] BOOKING_COLUMNS = new String[]{
            BOOKING_COL_ID,
            BOOKING_COL_DATE,
            BOOKING_COL_TIME,
            BOOKING_COL_SEATS,
            BOOKING_COL_CITY_ID,
            BOOKING_COL_PLANT_ID,
            BOOKING_COL_ROOM_ID,
            BOOKING_COL_CONFERENCE_ROOM_ID,
            BOOKING_COL_USER_EMAIL,
            BOOKING_COL_STATUS,
            BOOKING_COL_TOKEN
    };

    public TimeToMeetDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOKING_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BOOKING_DROP_TABLE_QUERY);
        onCreate(db);
        db.close();
    }

    public void insert(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKING_COL_DATE, booking.getDate());
        values.put(BOOKING_COL_SEATS, booking.getSeats());
        values.put(BOOKING_COL_CITY_ID, booking.getCityId());

        values.put(BOOKING_COL_STATUS, booking.getStatus().name());

        db.insert(BOOKING_TABLE_NAME, null, values);
        db.close();
    }

    public void update(Booking booking) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        if (booking.getTime() != null && !booking.getTime().isEmpty()) {
            values.put(BOOKING_COL_TIME, booking.getTime());
        }

        if (booking.getPlantId() > 0) {
            values.put(BOOKING_COL_PLANT_ID, booking.getPlantId());
        }

        if (booking.getRoomId() > 0) {
            values.put(BOOKING_COL_ROOM_ID, booking.getRoomId());
        }

        if (booking.getConferenceRoomId() > 0) {
            values.put(BOOKING_COL_CONFERENCE_ROOM_ID, booking.getConferenceRoomId());
        }

        if (booking.getUserEmail() != null && !booking.getUserEmail().isEmpty()) {
            values.put(BOOKING_COL_USER_EMAIL, booking.getUserEmail());
        }

        if (booking.getStatus() != null) {
            values.put(BOOKING_COL_STATUS, booking.getStatus().name());
        }

        if (booking.getToken() != null && !booking.getToken().isEmpty()) {
            values.put(BOOKING_COL_TOKEN, booking.getToken());
        }

        db.update(BOOKING_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(booking.getId())});
        db.close();
    }

    public Booking getBookingInProgress() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BOOKING_TABLE_NAME, BOOKING_COLUMNS, BOOKING_COL_STATUS + " = ?", new String[]{Status.IN_PROGRESS.name()}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        } else {
            return null;
        }

        Booking booking = new Booking();
        booking.setId(cursor.getInt(0));
        booking.setDate(cursor.getString(1));
        booking.setTime(cursor.getString(2));
        booking.setSeats(cursor.getInt(3));
        booking.setCityId(cursor.getInt(4));
        booking.setPlantId(cursor.getInt(5));
        booking.setRoomId(cursor.getInt(6));
        booking.setConferenceRoomId(cursor.getInt(7));
        booking.setUserEmail(cursor.getString(8));
        booking.setStatus(Status.valueOf(cursor.getString(9)));
        booking.setToken(cursor.getString(10));

        cursor.close();
        db.close();

        return booking;
    }

    public void deleteAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(BOOKING_TABLE_NAME, null, null);
        db.close();
    }
}
