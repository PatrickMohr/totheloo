package com.example.tothelooclient;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerCommunicator {

    public static void pullLoosFromServerToLocalDatabase() throws IOException, JSONException {

            String serverResponse = fetchDataFromDatabase();
            JSONObject jsonObject = new JSONObject(serverResponse);
            JSONArray looArray = (JSONArray) jsonObject.get("data");
            for (int i = 0; i < looArray.length(); i++) {
                org.json.JSONObject jsonLooObject = looArray.getJSONObject(i);
                addLooToDatabase(jsonLooObject, i);
                addRatingToDatabase(jsonLooObject);
            }
        }



    private static String fetchDataFromDatabase() throws IOException {

        URL urlForGetRequest = new URL("http://18.197.143.96:8080/api/loos");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            return response.toString();
        } else {
            return "ERROR!";
        }
    }

    private static void addLooToDatabase(org.json.JSONObject jsonLooObject, int index) throws  JSONException {
        //TODO: maybe we can make the id in the Frontend database to String
        //String id = jsonLooObject.getString("_id");

        int id = index;

        String name = jsonLooObject.getString("name");
        //TODO: think about storing price as double in frontend
        int priceAsInt = jsonLooObject.getInt("price");
        boolean price = priceAsInt > 0;


        org.json.JSONObject placeJsonObject = (JSONObject) jsonLooObject.get("place");
        String latitude = placeJsonObject.getString("latitude");
        String longitude = placeJsonObject.getString("longitude");
        String tag = placeJsonObject.getString("tag");
        String navigationDescription = placeJsonObject.getString("navigationDescription");

        org.json.JSONObject kindJsonObject = (JSONObject) jsonLooObject.get("kind");
        String description = kindJsonObject.getString("description");

        ClientDatabase.getInstance().insertToilets(id, name, price, latitude, longitude, tag, navigationDescription, description);

    }

    private static void addRatingToDatabase(org.json.JSONObject jsonLooObject) throws IOException, JSONException {
       //TODO: add code for saving ratings here
    }
}