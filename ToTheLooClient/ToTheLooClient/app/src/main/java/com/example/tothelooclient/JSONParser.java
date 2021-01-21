package com.example.tothelooclient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser {

    //nimmt das JSON als String entgegen und gibt ein Array aus Strings mit jeweils einem Klo zurück
    public String[] splitAllJSONLoos(String allJSONLoos){

        String[] singleLoos = allJSONLoos.split("\\},\\{\"place\"");
        return singleLoos;
    }

    String fertigerString;

    // wandelt den JSONString eines Klos in einen String um, den man für die Klientendatenbank braucht
    public String stringTransformer(String jsonLoo) {
        Pattern r1 = Pattern.compile("\"id\":(.*),\"version\"");
        Matcher m1 = r1.matcher(jsonLoo);
        if (m1.find( )) {
            String id = m1.group(1);
            fertigerString = id+";";
        } else {
            return "FAILED";
        }

        Pattern r2 = Pattern.compile("name\":\"(.*)\",\"_id\"");
        Matcher m2 = r2.matcher(jsonLoo);
        if (m2.find( )) {
            String name = m2.group(1);
            fertigerString = fertigerString+name+";";
        } else {
            return "FAILED";
        }

        Pattern r3 = Pattern.compile("price\":(.*),\"__v\"");
        Matcher m3 = r3.matcher(jsonLoo);
        if (m3.find( )) {
            String price = m3.group(1);
            fertigerString = fertigerString+price+";";
        } else {
            return "FAILED";
        }

        Pattern r4 = Pattern.compile("latitude\":\"(.*)\",\"tag\"");
        Matcher m4 = r4.matcher(jsonLoo);
        if (m4.find( )) {
            String latitude = m4.group(1);
            fertigerString = fertigerString+latitude+";";
        } else {
            return "FAILED";
        }

        Pattern r5 = Pattern.compile("longitude\":\"(.*)\",\"latitude\"");
        Matcher m5 = r5.matcher(jsonLoo);
        if (m5.find( )) {
            String longitude = m1.group(1);
            fertigerString = fertigerString+longitude+";";
        } else {
            return "FAILED";
        }

        Pattern r6 = Pattern.compile("tag\":\"(.*)\",\"navigationDescription\"");
        Matcher m6 = r6.matcher(jsonLoo);
        if (m6.find( )) {
            String tag = m6.group(1);
            fertigerString = fertigerString+tag+";";
        } else {
            return "FAILED";
        }

        Pattern r7 = Pattern.compile("navigationDescription\":\"(.*)\"\\},\"kind\"");
        Matcher m7 = r7.matcher(jsonLoo);
        if (m7.find( )) {
            String navigationDescription = m7.group(1);
            fertigerString = fertigerString+navigationDescription+";";
        } else {
            return "FAILED";
        }

        Pattern r8 = Pattern.compile("description\":\"(.*)\",\"icon\"");
        Matcher m8 = r8.matcher(jsonLoo);
        if (m8.find( )) {
            String description = m8.group(1);
            fertigerString = fertigerString+description;
        } else {
            return "FAILED";
        }

        return fertigerString;
    }
}