package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private Button routeButton;
    private Button rateButton;

    private double currentMarkerLat;
    private double currentMarkerLng;

    private String currentMarkerTitle;
    private String currentMarkerId;
    private String kosten;
    private String tag;



    private int id;
    private ClientDatabase clientDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double markerLat = extras.getDouble("Latitude");
            double markerLng = extras.getDouble("Longitude");
            String markerTitle = extras.getString("Title");
            String markerId = extras.getString("id");

            currentMarkerLat = markerLat;
            currentMarkerLng = markerLng;
            currentMarkerTitle = markerTitle;
            currentMarkerId = markerId;



        }


        setContentView(R.layout.activity_main3);

        id = Integer.parseInt(currentMarkerId);

        clientDatabase =  ClientDatabase.getInstance();

        clientDatabase.getToiletsByIDAsCursorObject(id);

        TextView nameView = (TextView) findViewById(R.id.nameView) ;
        nameView.setText("Name: "+currentMarkerTitle);

        TextView bewertungView = (TextView) findViewById(R.id.bewertungView) ;
        TextView tagView = (TextView) findViewById(R.id.tagView) ;
        TextView kostenView = (TextView) findViewById(R.id.kostenView) ;


        try{

            Cursor cursor = clientDatabase.getToiletsByIDAsCursorObject(id);

            if (cursor.moveToFirst()) {

                kosten = cursor.getString(2);
                tag = cursor.getString(5);

                if (kosten.equals("1")) {
                    kostenView.setText("Kostenpflichtig: Ja");
                }
                else {
                    kostenView.setText("Kostenpflichtig: Nein");

                }

                tagView.setText("Tag: "+ tag);
            }
            cursor.close();
        } catch (SQLException e) {

        }
        routeButton = (Button) findViewById(R.id.routeButton);
        routeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    openMapsActivity2();
            }
        });
        rateButton = (Button) findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity2();
            }

        });
    }

    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openMapsActivity2() {
        Intent MarkerToMap2 = new Intent(this, MapsActivity2.class);
        MarkerToMap2.putExtra("Latitude",currentMarkerLat);
        MarkerToMap2.putExtra("Longitude",currentMarkerLng);
        MarkerToMap2.putExtra("Title",currentMarkerTitle);

        startActivity(MarkerToMap2);
    }

    public void openMainActivity2() {
        Intent OpenDetails = new Intent(this, MainActivity2.class);
        OpenDetails.putExtra("Latitude",currentMarkerLat);
        OpenDetails.putExtra("Longitude",currentMarkerLng);
        OpenDetails.putExtra("Title",currentMarkerTitle);
        OpenDetails.putExtra("id",currentMarkerId);
        startActivity(OpenDetails);
    }

}
