package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final RatingBar ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        Button sendeButton = (Button) findViewById(R.id.sendeButton);
        final TextView textviewSendRating = (TextView) findViewById(R.id.textViewSendRating);

        sendeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textviewSendRating.setText("Bewertung abgeschickt!");
            }
        });


    }
}
