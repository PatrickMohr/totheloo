package com.example.tothelooclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClientDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToiletsLocal.db";
    public static final String TABLE_NAME = "Toilets_Table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Latitude";
    public static final String COL_3 = "Longitude";
    public static final String COL_4 = "Price";
    public static final String COL_5 = "Rating";
    public static final String COL_6 = "Only_Pissior";

    public ClientDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY,"+COL_2+" TEXT,"+COL_3+" TEXT,"+COL_4+" FLOAT,"+COL_5+" FLOAT,"+COL_6+" BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Returns true if inserting Data was successful.
    public boolean insertDataAsString(String input) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id;
        String latitude;
        String longitude;
        float price;
        float rating;
        boolean onlyPissior;

        String[] data = input.split(";");

        id = Integer.parseInt(data[0]);
        latitude = data[1];
        longitude = data[2];
        price = Float.parseFloat(data[3]);
        rating = Float.parseFloat(data[4]);
        onlyPissior = Boolean.parseBoolean(data[5]);

        db.delete(TABLE_NAME, "id=?", new String[]{Integer.toString(id)});
        return insertData(id, latitude, longitude, price, rating, onlyPissior);
    }

    private boolean insertData(int id, String latitude, String longitude, float price, float rating, boolean onlyPissior) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, latitude);
        contentValues.put(COL_3, longitude);
        contentValues.put(COL_4, price);
        contentValues.put(COL_5, rating);
        contentValues.put(COL_6, onlyPissior);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public String extractDataByIDAsString(int id) {
        Cursor data = extractDataByID(id);

        StringBuffer buffer = new StringBuffer();
        while(data.moveToNext()) {
            buffer.append(data.getString(0) + ";");
            buffer.append(data.getString(1) + ";");
            buffer.append(data.getString(2) + ";");
            buffer.append(data.getString(3) + ";");
            buffer.append(data.getString(4) + ";");
            buffer.append(data.getString(5));
        }

        return (buffer.toString());
    }

    public Cursor extractDataByID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COL_1 + " = " + id,null);
    }
}
