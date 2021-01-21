package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
    int ratingInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        mainButton = (Button) findViewById(R.id.mainButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });
    }


    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("ratingInt", this.ratingInt);
        startActivity(intent);
    }
}

