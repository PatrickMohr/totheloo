package com.example.tothelooclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClientDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToiletsLocal.db";
    public static final String TOILETS_TABLE_NAME = "Toilets_Table";
    public static final String TOILETS_COL_1 = "ToiletID";
    public static final String TOILETS_COL_2 = "Name";
    public static final String TOILETS_COL_3 = "Price";
    public static final String TOILETS_COL_4 = "Latitude";
    public static final String TOILETS_COL_5 = "Longitude";
    public static final String TOILETS_COL_6 = "Tag";
    public static final String TOILETS_COL_7 = "Navigation_Description";
    public static final String TOILETS_COL_8 = "Description";

    public static final String RATINGS_TABLE_NAME = "Ratings_Table";
    public static final String RATINGS_COL_1 = "RatingID";
    public static final String RATINGS_COL_2 = "User";
    public static final String RATINGS_COL_3 = "RatingText";
    public static final String RATINGS_COL_4 = "Stars";
    public static final String RATINGS_COL_5 = "ToiletID";

    public ClientDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createToiletsTable(db);
        createRatingsTable(db);
    }

    private void createToiletsTable(SQLiteDatabase db) {
        db.execSQL("create table " + TOILETS_TABLE_NAME
                +" ("+ TOILETS_COL_1 +" INTEGER PRIMARY KEY NOT NULL,"
                + TOILETS_COL_2 +" TEXT,"
                + TOILETS_COL_3 +" FLOAT,"
                + TOILETS_COL_4 +" TEXT NOT NULL,"
                + TOILETS_COL_5 +" TEXT NOT NULL,"
                + TOILETS_COL_6 +" TEXT, "
                + TOILETS_COL_7 +" TEXT, "
                + TOILETS_COL_8 +" TEXT)");
    }

    private void createRatingsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RATINGS_TABLE_NAME
                +" ("+ RATINGS_COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RATINGS_COL_2 +" FLOAT,"
                + RATINGS_COL_3 +" TEXT,"
                + RATINGS_COL_4 +" TEXT,"
                + "FOREIGN KEY (" + RATINGS_COL_5 + ") REFERENCES " + TOILETS_TABLE_NAME+"(" + TOILETS_COL_1 + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOILETS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RATINGS_TABLE_NAME);
        onCreate(db);
    }

    //Returns true if inserting Data was successful.
    public void insertToiletsAsString(String input) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id;
        String name;
        float price;
        String latitude;
        String longitude;
        String tag;
        String navigationDescription;
        String description;

        String[] data = input.split(";");

        id = Integer.parseInt(data[0]);
        name = data[1];
        price = Float.parseFloat(data[2]);
        latitude = data[3];
        longitude = data[4];
        tag = data[5];
        navigationDescription = data[6];
        description = data[7];

        db.delete(TOILETS_TABLE_NAME, "id=?", new String[]{Integer.toString(id)});
        insertToilets(id, name, price, latitude, longitude, tag, navigationDescription, description);
    }

    private void insertToilets(int id, String name, Float price, String latitude, String longitude, String tag, String navigationDescription, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOILETS_COL_1, id);
        contentValues.put(TOILETS_COL_2, name);
        contentValues.put(TOILETS_COL_3, price);
        contentValues.put(TOILETS_COL_4, latitude);
        contentValues.put(TOILETS_COL_5, longitude);
        contentValues.put(TOILETS_COL_6, tag);
        contentValues.put(TOILETS_COL_7, navigationDescription);
        contentValues.put(TOILETS_COL_8, description);

        db.insert(TOILETS_TABLE_NAME, null, contentValues);
    }

    public void insertRatingsAsStringByToiletID(String input) {
        int toiletID;
        String user;
        String ratingText;
        Float stars;

        String[] extractedID = input.split("==");

        toiletID = Integer.parseInt(extractedID[0]);

        String[] data = extractedID[1].split("\n");
        for(String i : data) {
            String[] rating = i.split(";");

            user = rating[1];
            ratingText = rating[2];
            stars = Float.parseFloat(rating[3]);

            insertRating(toiletID, user, ratingText, stars);
        }
    }

    private void insertRating(int toiletID, String user, String ratingText, float stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TOILETS_COL_2, user);
        contentValues.put(TOILETS_COL_3, ratingText);
        contentValues.put(TOILETS_COL_4, stars);
        contentValues.put(TOILETS_COL_5, toiletID);

        db.insert(RATINGS_TABLE_NAME, null, contentValues);
    }


    public Cursor extractToiletsByIDAsCursorObject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TOILETS_TABLE_NAME + " WHERE " + TOILETS_COL_1 + " = " + id,null);
    }

    public Cursor extractRatingsByToiletID(int toiletID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + RATINGS_TABLE_NAME + " WHERE " + RATINGS_COL_5 + " = " + toiletID, null);
    }
}
