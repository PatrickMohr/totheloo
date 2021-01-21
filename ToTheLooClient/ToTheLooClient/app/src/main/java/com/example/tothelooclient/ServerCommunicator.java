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

    public static void pullLoosFromServerToLocalDatabase() throws IOException {

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

            String[] oneLoo = jSONParser.splitAllJSONLoos(response.toString());
            for (int i = 0; i < oneLoo.length;i++) {
                //System.out.println(jSONParser.stringTransformer(oneLoo[i]));
                ClientDatabase.getInstance().insertToiletsAsString(jSONParser.stringTransformer(oneLoo[i]));
            }
        }
        else {

            System.out.println("GET DIDNT WORK");

        }

    }

}