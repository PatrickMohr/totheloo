package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private String toiletId;
    private String ratingText;
    private String ratingZahl;
    RatingBar ratingBar2;
    private ClientDatabase clientDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        Button sendeButton = (Button) findViewById(R.id.sendeButton);
        TextView textviewSendRating = (TextView) findViewById(R.id.textViewSendRating);




        sendeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {




                sendRating();

                textviewSendRating.setText("Bewertung abgeschickt!");
                //finish();
            }
        });


    }
    public void sendRating() {


        clientDatabase = ClientDatabase.getInstance();
        Intent OpenDetails = getIntent();
        toiletId = OpenDetails.getStringExtra("id");

        EditText reT = (EditText) findViewById(R.id.ratingEditText) ;
        ratingText = reT.getText().toString();

        ratingZahl = String.valueOf(ratingBar2.getRating());
        clientDatabase.insertRatingsAsStringByToiletID(toiletId+"=="+"Anonym"+";"+ratingText+";"+ratingZahl);
    }


}
