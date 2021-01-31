package com.example.tothelooclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClientDatabase extends SQLiteOpenHelper {
    private static ClientDatabase INSTANCE = null;

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
    public static final String RATINGS_COL_2 = "ToiletID";
    public static final String RATINGS_COL_3 = "User";
    public static final String RATINGS_COL_4 = "RatingText";
    public static final String RATINGS_COL_5 = "Stars";

    private ClientDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static ClientDatabase getFirstInstance(Context context) {
        INSTANCE = new ClientDatabase(context);
        return INSTANCE;
    }

    public static ClientDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createToiletsTable(db);
        createRatingsTable(db);
    }

    private void createToiletsTable(SQLiteDatabase db) {
        db.execSQL("create table " + TOILETS_TABLE_NAME
                +" ("+ TOILETS_COL_1 +" INTEGER PRIMARY KEY,"
                + TOILETS_COL_2 +" TEXT,"
                + TOILETS_COL_3 +" BOOLEAN,"
                + TOILETS_COL_4 +" TEXT NOT NULL,"
                + TOILETS_COL_5 +" TEXT NOT NULL,"
                + TOILETS_COL_6 +" TEXT, "
                + TOILETS_COL_7 +" TEXT, "
                + TOILETS_COL_8 +" BOOLEAN)");
    }

    private void createRatingsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RATINGS_TABLE_NAME
                +" ("+ RATINGS_COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RATINGS_COL_2 +" INTEGER NOT NULL,"
                + RATINGS_COL_3 +" FLOAT,"
                + RATINGS_COL_4 +" TEXT,"
                + RATINGS_COL_5 +" TEXT,"
                + "FOREIGN KEY (" + RATINGS_COL_2 + ") REFERENCES " + TOILETS_TABLE_NAME+"(" + TOILETS_COL_1 + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOILETS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RATINGS_TABLE_NAME);
        onCreate(db);
    }

    // input format:
    // {id};{name};{price};{latitude};{longitude};{tag};{navigationDescription};{description}
    public void insertToiletsAsString(String input) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id;
        String name;
        boolean price;
        String latitude;
        String longitude;
        String tag;
        String navigationDescription;
        boolean description;

        String[] data = input.split(";");

        id = Integer.parseInt(data[0]);
        name = data[1];
        price = Boolean.parseBoolean(data[2]);
        latitude = data[3];
        longitude = data[4];
        tag = data[5];
        navigationDescription = data[6];
        description = Boolean.parseBoolean(data[7]);

        db.delete(TOILETS_TABLE_NAME, TOILETS_COL_1 + "=?", new String[]{Integer.toString(id)});
        insertToilets(id, name, price, latitude, longitude, tag, navigationDescription, description);
    }

    public void insertToilets(int id, String name, boolean price, String latitude, String longitude, String tag, String navigationDescription, boolean description) {
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
        try {
            db.insertOrThrow(TOILETS_TABLE_NAME, null, contentValues);

        } catch ( android.database.SQLException e) {
            System.out.println("Loo with id " + String.valueOf(id) + " does already exists. I did not add it to the database!");
        }
    }

    // input format:
    // {toiletID}==
    //        {user};{ratingText};{stars}\n
    //        {user};{ratingText};{stars}...
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

            user = rating[0];
            ratingText = rating[1];
            stars = Float.parseFloat(rating[2]);

            insertRating(toiletID, user, ratingText, stars);
        }
    }

    // input format:
    // {id}=={name};{price};{latitude};{longitude};{tag};{navigationDescription};{description}=={user};{ratingText};{stars}\n
    //                                                                                          {user};{ratingText};{stars}...
    public void insertToiletsWithRatings(String input) {
        String toiletIDString;
        String toiletString;
        String ratingsString;

        String[] data = input.split("==");

        toiletIDString = data[0];
        toiletString = data[1];
        ratingsString = data[2];
        insertToiletsAsString(toiletIDString + ";" + toiletString);
        insertRatingsAsStringByToiletID(toiletIDString + "==" + ratingsString);
    }

    private void insertRating(int toiletID, String user, String ratingText, float stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RATINGS_COL_2, toiletID);
        contentValues.put(RATINGS_COL_3, user);
        contentValues.put(RATINGS_COL_4, ratingText);
        contentValues.put(RATINGS_COL_5, stars);

        db.insert(RATINGS_TABLE_NAME, null, contentValues);
    }

    // output format:
    // {id};{name};{latitude};{longitude};{averageRating};{description}
    public String getAllToiletsAsString(int rating, boolean price) {
        SQLiteDatabase db = this.getWritableDatabase();

        int booleanPrice;

        if(price = false) {
            booleanPrice = 0;
        } else {
            booleanPrice = 1;
        }

        Cursor loosWithRating = db.rawQuery("select t." + TOILETS_COL_1 + ", t." + TOILETS_COL_2 + ", t." + TOILETS_COL_4 + ", t." + TOILETS_COL_5 + ", ROUND(AVG(r." + RATINGS_COL_5 + "),2)" + " AS averageStars, t." + TOILETS_COL_8
                + " from " + TOILETS_TABLE_NAME + " AS t"
                + " INNER JOIN " + RATINGS_TABLE_NAME + " AS r ON r." + RATINGS_COL_2 + " = t." + TOILETS_COL_1
                + " WHERE t." + TOILETS_COL_3 + " = " + booleanPrice + " OR t." + TOILETS_COL_3 + " = 0"
                + " GROUP BY t." + TOILETS_COL_1
                + " HAVING AVG(r. " + RATINGS_COL_5 + ") >= " + rating, null);

        Cursor loosWithoutRatings = db.rawQuery("select t." + TOILETS_COL_1 + ", t." + TOILETS_COL_2 + ", t." + TOILETS_COL_4 + ", t." + TOILETS_COL_5 + ", t." + TOILETS_COL_8
                + " from " + TOILETS_TABLE_NAME + " AS t"
                + " where not exists (select * from " + RATINGS_TABLE_NAME + " as r where t." + TOILETS_COL_1 + " = r." + RATINGS_COL_2 + ")"
                + " AND t." + TOILETS_COL_3 + " = " + booleanPrice + " OR t." + TOILETS_COL_3 + " = 0",null);

        StringBuffer buffer = new StringBuffer();
        while(loosWithRating.moveToNext()) {
            buffer.append(loosWithRating.getString(0) + ";");
            buffer.append(loosWithRating.getString(1) + ";");
            buffer.append(loosWithRating.getString(2) + ";");
            buffer.append(loosWithRating.getString(3) + ";");
            buffer.append(loosWithRating.getString(4) + ";");
            buffer.append(loosWithRating.getString(5) + "\n");
        }

        while(loosWithoutRatings.moveToNext()) {
            buffer.append(loosWithoutRatings.getString(0) + ";");
            buffer.append(loosWithoutRatings.getString(1) + ";");
            buffer.append(loosWithoutRatings.getString(2) + ";");
            buffer.append(loosWithoutRatings.getString(3) + ";999;");
            buffer.append(loosWithoutRatings.getString(4) + "\n");
        }

        return buffer.toString();
    }

    public Cursor getToiletsByIDAsCursorObject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TOILETS_TABLE_NAME + " WHERE " + TOILETS_COL_1 + " = " + id,null);
    }

    public Cursor getRatingsByToiletID(int toiletID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + RATINGS_TABLE_NAME + " WHERE " + RATINGS_COL_2 + " = " + toiletID, null);
    }
}
