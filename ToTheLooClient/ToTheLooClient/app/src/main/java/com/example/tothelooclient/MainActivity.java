package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
    int ratingInt;
    private Button addButton;
    private Switch kostenSwitch;
    private Switch barrierefreiSwitch;
    private Switch pissoirSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        pissoirSwitch = (Switch) findViewById(R.id.pissoirSwitch);
        barrierefreiSwitch = (Switch) findViewById(R.id.barrierefreiSwitch);
        kostenSwitch = (Switch) findViewById(R.id.kostenSwitch);
        mainButton = (Button) findViewById(R.id.mainButton);
        addButton = (Button) findViewById(R.id.addButton);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });



        addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openMainActivity4();

                }
            });
    }


    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("ratingInt", this.ratingInt);
        intent.putExtra("kostenSwitch", kostenSwitch.isChecked());
        intent.putExtra("barrierefreiSwitch", barrierefreiSwitch.isChecked());
        intent.putExtra("pissoirSwitch", pissoirSwitch.isChecked());
        startActivity(intent);
    }

    public void openMainActivity4() {
        Intent intent = new Intent(this, MainActivity4.class);
        startActivity(intent);
    }
}

