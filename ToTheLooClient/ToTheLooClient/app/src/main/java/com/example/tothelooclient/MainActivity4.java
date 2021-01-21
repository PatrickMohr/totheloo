package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity4 extends AppCompatActivity {

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
                openMapsActivity();
            }
        });
    }


    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);

        startActivity(intent);
    }
}