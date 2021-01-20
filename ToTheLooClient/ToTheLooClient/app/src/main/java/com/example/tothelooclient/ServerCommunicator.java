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

    public static void main(String[] args) throws IOException {

        ServerCommunicator.MyGETRequest();
        //ServerCommunicator.MyPOSTRequest();

    }

    public static void MyGETRequest() throws IOException {

        URL urlForGetRequest = new URL("basePath/api/loos");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        //connection.setRequestProperty("userId","a1bcdef"); // set userId its a sample here
        connection.setRequestProperty("name","WonderToilet"); // set userId its a sample here
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
            System.out.println("JSON String Result " + response.toString());
            //ServerCommunicator.POSTRequest(response.toString());

        }
        else {

            System.out.println("GET NOT WORKED");

        }

    }

}