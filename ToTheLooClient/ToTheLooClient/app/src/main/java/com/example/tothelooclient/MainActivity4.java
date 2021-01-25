package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {

    private static final int REQUEST_GET_MAP_LOCATION = 0;
    private Button fertigButton;
    private Button positionButton;
    private ClientDatabase clientdatabase;
    private String id;

    private double markerLatitude;
    private double markerLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Restaurant", "Einkaufszentrum", "Öffentlich"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        fertigButton = (Button) findViewById(R.id.fertigButton);
        positionButton = (Button) findViewById(R.id.positionButton);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity3();
            }
        });

        fertigButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToilette();

                finish();
            }
        }));
    }


    public void openMapsActivity3() {
        Intent intent = new Intent(this, MapsActivity3.class);

        startActivityForResult(intent, REQUEST_GET_MAP_LOCATION);
    }

    public void addToilette() {

        Toast.makeText(getApplicationContext(), "Toilette hinzugefügt", Toast.LENGTH_LONG).show();
        ClientDatabase.getInstance();
        clientdatabase.insertToiletsAsString(id);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_GET_MAP_LOCATION): {
                if (resultCode == Activity.RESULT_OK) {
                    markerLatitude = data.getDoubleExtra("latitude",0);
                    markerLongitude = data.getDoubleExtra("longitude", 0);
                }
                break;
            }
        }
    }
}