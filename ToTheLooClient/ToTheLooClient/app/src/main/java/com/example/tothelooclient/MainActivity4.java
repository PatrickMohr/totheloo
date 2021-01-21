package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity4 extends AppCompatActivity {

    private static final int REQUEST_GET_MAP_LOCATION = 0;

 private Button positionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Restaurant", "Einkaufszentrum", "Öffentlich"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        positionButton = (Button) findViewById(R.id.positionButton);
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity3();
            }
        });
    }


    public void openMapsActivity3() {
        Intent intent = new Intent(this, MapsActivity3.class);

        startActivityForResult(intent, REQUEST_GET_MAP_LOCATION);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GET_MAP_LOCATION && resultCode == MapsActivity3.RESULT_OK) {
            int latitude = data.getIntExtra("latitude", 0);
            int longitude = data.getIntExtra("longitude", 0);

            // do something with B's return values
        }
    }
}