package com.example.tothelooclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ServerCommunicator {

    //private static JSONParser jSONParser;

    public static void main(String[] args) throws IOException {

        ServerCommunicator.MyGETRequest();
        //ServerCommunicator.MyPOSTRequest();

    }

    public static void MyGETRequest() throws IOException {

        JSONParser jSONParser = new JSONParser();

        URL urlForGetRequest = new URL("http://ec2-18-159-109-98.eu-central-1.compute.amazonaws.com:8080/api/loos");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {

                response.append(readLine);

            }
            in .close();

            //print result
            //System.out.println("JSON String Result: " + response.toString());
            String[] oneLoo = jSONParser.splitAllJSONLoos(response.toString());
            System.out.println(jSONParser.stringTransformer(oneLoo[0]));

        }
        else {

            System.out.println("GET DIDNT WORK");

        }

    }

}