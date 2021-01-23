package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
 //   private ServerConnector serverConnector;
    int ratingInt;
    private Button addButton;
    private Switch kostenSwitch;
    private Switch barrierefreiSwitch;
    private Switch pissoirSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: put this code in an own class
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        String url = "http://18.197.143.96:8080/api/loos";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                if(response.isSuccessful()) {
                    //TODO: format data in a way the MapActivity can handle it!
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

        });

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

