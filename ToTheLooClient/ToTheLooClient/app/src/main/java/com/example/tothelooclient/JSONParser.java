package com.example.tothelooclient;

public class JSONParser {

    //nimmt das JSON als String entgegen und gibt ein Array aus Strings mit jeweils einem Klo zurück
    public String[] splitAllJSONLoos(String allJSONLoos){

        String[] singleLoos = allJSONLoos.split("\\},\\{\"place\"");
        return singleLoos;
    }

    // wandelt den JSONString eines Klos in einen String um, den man für die Klientendatenbank braucht
    public String stringTransformer(String jsonLoo) {
        String[] betterLoo = jsonLoo.split("_id([^;]*)\"");
        String aLoo = betterLoo[0];
        return aLoo;
    }
}