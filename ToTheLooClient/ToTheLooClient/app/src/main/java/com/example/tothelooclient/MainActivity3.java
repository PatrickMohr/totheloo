package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

public class MainActivity3 extends AppCompatActivity {
    private Button routeButton;
    private Button rateButton;
    private double currentMarkerLat;
    private double currentMarkerLng;
    private String currentMarkerTitle;
    private String currentMarkerId;

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

        TextView nameView = (TextView) findViewById(R.id.nameView) ;
        nameView.setText("Name: "+currentMarkerTitle);

        //TextView bewertungView = (TextView) findViewById(R.id.bewertungView) ;
        //bewertungView.setText(currentMarkerRating);

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
