package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutionException;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DB Name
    public static final String DATABASE_NAME = "final_project.db";

    // Table Names
    public static final String TABLE_NAME1 = "user_table";
    public static final String TABLE_NAME2 = "driver_table";
    public static final String TABLE_NAME3 = "ride_table";

    // User Table Columns
    public static final String USER_PK = "USERID";
    public static final String USER_COL_1 = "FIRSTNAME";
    public static final String USER_COL_2 = "LASTNAME";
    public static final String USER_COL_3 = "PHONENUMBER";
    public static final String USER_COL_4 = "EMAIL";
    public static final String USER_COL_5 = "PASSWORD";
    public static final String USER_COL_6 = "RATING";

    // Driver Table Columns
    public static final String DRIVER_PK = "DRIVERID";
    public static final String DRIVER_COL_1 = "FIRSTNAME";
    public static final String DRIVER_COL_2 = "LASTNAME";
    public static final String DRIVER_COL_3 = "EMAIL";
    public static final String DRIVER_COL_4 = "PASSWORD";
    public static final String DRIVER_COL_5 = "LANGUAGES";
    public static final String DRIVER_COL_6 = "DATEJOINED";
    public static final String DRIVER_COL_7 = "LOCATION";
    public static final String DRIVER_COL_8 = "RATING";
    public static final String DRIVER_COL_9 = "NUMBEROFRIDES";  // to avg the rating
    public static final String DRIVER_COL_10 = "OTHERNOTES";
    public static final String DRIVER_COL_11 = "LICENSEPLATE";

    // Ride Table Columns
    public static final String RIDE_PK = "RIDEID";
    public static final String RIDE_COL_1 = "DATE";
    public static final String RIDE_COL_2 = "DURATION";
    public static final String RIDE_COL_3 = "COST";
    public static final String RIDE_COL_4 = "PICKUP";
    public static final String RIDE_COL_5 = "DESTINATION";
    public static final String RIDE_FK1 = "USERID";
    public static final String RIDE_FK2 = "DRIVERID";

    // Table Create Statements
    public static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME1 +
            " (USERID integer primary key autoincrement," +
            " FIRSTNAME text," +
            " LASTNAME text, " +
            " PHONENUMBER text," +
            " EMAIL text," +
            " PASSWORD text," +
            " RATING integer);";

    public static final String CREATE_DRIVER_TABLE = "CREATE TABLE " + TABLE_NAME2 +
            " (DRIVERID integer primary key autoincrement," +
            " FIRSTNAME text," +
            " LASTNAME text, " +
            " EMAIL text," +
            " PASSWORD text," +
            " LANGUAGES text," +
            " DATEJOINED text," +
            " LOCATION text," +
            " RATING integer," +
            " NUMBEROFRIDES integer," +
            " OTHERNOTES text," +
            " LICENSEPLATE text);";

    public static final String CREATE_RIDE_TABLE = "CREATE TABLE " + TABLE_NAME3 +
            " (RIDEID integer primary key autoincrement," +
            " DATE text," +
            " DURATION integer," +
            " COST double," +
            " PICKUP text," +
            " DESTINATION text," +
            " USERID integer," +
            " DRIVERID integer," +
            " FOREIGN KEY (USERID) REFERENCES " + TABLE_NAME1 + "(USERID)," +
            " FOREIGN KEY (DRIVERID) REFERENCES " + TABLE_NAME2 + "(DRIVERID));";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_DRIVER_TABLE);
        sqLiteDatabase.execSQL(CREATE_RIDE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);

        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(String firstName, String lastName, String phoneNumber, String email,
                              String password, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_1, firstName);
        contentValues.put(USER_COL_2, lastName);
        contentValues.put(USER_COL_3, phoneNumber);
        contentValues.put(USER_COL_4, email);
        contentValues.put(USER_COL_5, password);
        contentValues.put(USER_COL_6, rating);

        long result = db.insert(TABLE_NAME1, null, contentValues);
        return result != -1;
    }

    public boolean insertDriver(String firstName, String lastName, String email, String password,
                                String languages, String dateJoined, String location, int rating,
                                int numberOfRides, String otherNotes, String licensePlate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DRIVER_COL_1, firstName);
        contentValues.put(DRIVER_COL_2, lastName);
        contentValues.put(DRIVER_COL_3, email);
        contentValues.put(DRIVER_COL_4, password);
        contentValues.put(DRIVER_COL_5, languages);
        contentValues.put(DRIVER_COL_6, dateJoined);
        contentValues.put(DRIVER_COL_7, location);
        contentValues.put(DRIVER_COL_8, rating);
        contentValues.put(DRIVER_COL_9, numberOfRides);
        contentValues.put(DRIVER_COL_10, otherNotes);
        contentValues.put(DRIVER_COL_11, licensePlate);

        long result = db.insert(TABLE_NAME2, null, contentValues);
        return result != -1;
    }

    public boolean insertRide(String date, int duration, double cost, String pickup, String destination,
                              int userId, int driverId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RIDE_COL_1, date);
        contentValues.put(RIDE_COL_2, duration);
        contentValues.put(RIDE_COL_3, cost);
        contentValues.put(RIDE_COL_4, pickup);
        contentValues.put(RIDE_COL_5, destination);
        contentValues.put(RIDE_FK1, userId);
        contentValues.put(RIDE_FK2, driverId);

        long result = db.insert(TABLE_NAME3, null, contentValues);
        return result != -1;
    }

    public boolean updateUser(int userId, String firstName, String lastName, String phoneNumber, String email,
                              String password, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_COL_1, firstName);
        contentValues.put(USER_COL_2, lastName);
        contentValues.put(USER_COL_3, phoneNumber);
        contentValues.put(USER_COL_4, email);
        contentValues.put(USER_COL_5, password);
        contentValues.put(USER_COL_6, rating);

        String userIdVal = Integer.toString(userId);
        db.update(TABLE_NAME1, contentValues, "USERID = ? ", new String[]{userIdVal});
        return true;
    }

    @SuppressLint("Range")
    public User getUser(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME1 + " WHERE email = '" + email + "';";

        Cursor res = sqLiteDatabase.rawQuery(selectQuery, null);
        if (res != null)
            res.moveToFirst();

        User user = new User();

        try {
            user.setUserId(res.getInt(res.getColumnIndex(USER_PK)));
            user.setFirstName(res.getString(res.getColumnIndex(USER_COL_1)));
            user.setLastName(res.getString(res.getColumnIndex(USER_COL_2)));
            user.setPhoneNumber(res.getString(res.getColumnIndex(USER_COL_3)));
            user.setEmail(email);
            user.setPassword(res.getString(res.getColumnIndex(USER_COL_5)));
            user.setRating(res.getInt(res.getColumnIndex(USER_COL_6)));
        } catch(Exception ex) {
            return new User();
        }

        return user;
    }

    @SuppressLint("Range")
    public Driver getDriver(int driverId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME2 + " WHERE DRIVERID = " + driverId + ";";

        Cursor res = sqLiteDatabase.rawQuery(selectQuery, null);
        if (res != null)
            res.moveToFirst();

        Driver driver = new Driver();

        try {
            driver.setDriverId(res.getInt(res.getColumnIndex(DRIVER_PK)));
            driver.setFirstName(res.getString(res.getColumnIndex(DRIVER_COL_1)));
            driver.setLastName(res.getString(res.getColumnIndex(DRIVER_COL_2)));
            driver.setEmail(res.getString(res.getColumnIndex(DRIVER_COL_3)));
            driver.setPassword(res.getString(res.getColumnIndex(DRIVER_COL_4)));
            driver.setLanguages(res.getString(res.getColumnIndex(DRIVER_COL_5)));
            driver.setDateJoined(res.getString(res.getColumnIndex(DRIVER_COL_6)));
            driver.setLocation(res.getString(res.getColumnIndex(DRIVER_COL_7)));
            driver.setRating(res.getInt(res.getColumnIndex(DRIVER_COL_8)));
            driver.setNumberOfRides(res.getInt(res.getColumnIndex(DRIVER_COL_9)));
            driver.setOtherNotes(res.getString(res.getColumnIndex(DRIVER_COL_10)));
            driver.setLicensePlate(res.getString(res.getColumnIndex(DRIVER_COL_11)));
        } catch(Exception ex) {
            return new Driver();
        }

        return driver;
    }

    public Cursor getAllRides(int userId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME3
                + " WHERE USERID = " + userId + ";", null);
        return res;
    }
}