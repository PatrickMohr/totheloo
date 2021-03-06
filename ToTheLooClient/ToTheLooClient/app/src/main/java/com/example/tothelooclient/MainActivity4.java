package com.example.tothelooclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import static java.lang.Math.round;

public class MainActivity4 extends AppCompatActivity {

    private static final int REQUEST_GET_MAP_LOCATION = 0;
    private Button fertigButton;
    private Button positionButton;
    private ClientDatabase clientdatabase;

    private boolean kostenSwitchZustand;
    private boolean barrierefreiSwitchZustand;
    private boolean pissoirSwitchZustand;

    private Switch kostenSwitch;
    private Switch barriereSwitch;
    private Switch pissoirSwitch;

    private int latitudeInt;
    private int longitudeInt;

    private String name;
    private String id;
    private String barriere;
    private String pissoirs;
    private String kosten;
    private String tag;
    private String longitudeString;
    private String latitudeString;

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



        clientdatabase = ClientDatabase.getFirstInstance(this);

        kostenSwitch =  findViewById(R.id.switch1);
        barriereSwitch =  findViewById(R.id.switch2);
        pissoirSwitch =  findViewById(R.id.switch3);

        fertigButton = findViewById(R.id.fertigButton);
        positionButton = findViewById(R.id.positionButton);


        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity3();
            }
        });

        fertigButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latitudeString != null){
                    addToilette();
                   finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Bitte Standort hinzufügen",Toast.LENGTH_LONG).show();

                }
            }
        }));

        //tagDropdown
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tag = parent.getItemAtPosition(position).toString();


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onCheckedChangedKosten(Switch s) {



        if(s.isChecked()){
            kostenSwitchZustand = true;
            kosten = String.valueOf(kostenSwitchZustand);

        } else {
            kostenSwitchZustand = false;
            kosten = String.valueOf(kostenSwitchZustand);

        }

    }

    public void onCheckedChangedBarrierefrei (Switch s) {



        if(s.isChecked()){
            barrierefreiSwitchZustand = true;
            barriere = String.valueOf(barrierefreiSwitchZustand);

        } else {
            barrierefreiSwitchZustand = false;
            barriere = String.valueOf(barrierefreiSwitchZustand);
        }

    }

    public void onCheckedChangedPissoir (Switch s) {



        if(s.isChecked()){
            pissoirSwitchZustand = true;
            pissoirs = String.valueOf(pissoirSwitchZustand);

        } else {
            pissoirSwitchZustand = false;
            pissoirs = String.valueOf(pissoirSwitchZustand);
        }

    }


    public void openMapsActivity3() {
        Intent intent = new Intent(this, MapsActivity3.class);

        startActivityForResult(intent, REQUEST_GET_MAP_LOCATION);
    }

    public void addToilette() {

        EditText reT =  findViewById(R.id.editTextNewToiletName) ;
        name = reT.getText().toString();
        onCheckedChangedKosten(kostenSwitch);
        onCheckedChangedBarrierefrei(barriereSwitch);
        onCheckedChangedPissoir(pissoirSwitch);


        clientdatabase.insertToiletsAsString(id+";"+name+";"+kosten+";"+latitudeString+";"+longitudeString+";"+tag+";"+""+";"+"");
        Toast.makeText(getApplicationContext(), "Toilette hinzugefügt",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_GET_MAP_LOCATION): {
                if (resultCode == Activity.RESULT_OK) {
                    markerLatitude = data.getDoubleExtra("latitude",0);
                    markerLongitude = data.getDoubleExtra("longitude", 0);

                    latitudeInt = (int) Math.round(markerLatitude * 1000);
                    longitudeInt = (int) Math.round(markerLongitude * 1000);

                    longitudeString = String.valueOf(markerLongitude);
                    latitudeString = String.valueOf(markerLatitude);

                    id = String.valueOf(latitudeInt)+String.valueOf(longitudeInt);
                }
                break;
            }
        }
    }
}