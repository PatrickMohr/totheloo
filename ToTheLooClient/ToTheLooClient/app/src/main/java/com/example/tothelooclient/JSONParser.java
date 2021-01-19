package com.example.tothelooclient;

import org.json.*;

public class JSONParser {
    String jsonString = "{\n" +
            "    \"name\": \"WonderToilet\",\n" +
            "    \"price\": 0.5,\n" +
            "    \"place\": {\n" +
            "        \"longitude\": \"53.540307\",\n" +
            "        \"latitude\": \"10.021677\",\n" +
            "        \"tag\": \"ItsATag\",\n" +
            "        \"navigationDescription\": \"Just go around the corner where it smells strange!\"\n" +
            "    },\n" +
            "    \"kind\": {\n" +
            "        \"description\": \"Pissoir\",\n" +
            "        \"icon\": \"ItsANIcon\"\n" +
            "    },\n" +
            "    \"rating\": [\n" +
            "        {\n" +
            "            \"user\": \"Anon\",\n" +
            "            \"ratingText\": \"It was wonderful\",\n" +
            "            \"stars\": \"4\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"user\": \"Anon1\",\n" +
            "            \"ratingText\": \"It smelled a bit!\",\n" +
            "            \"stars\": \"2\"\n" +
            "        },{\n" +
            "            \"user\": \"Anon2\",\n" +
            "            \"ratingText\": \"Like heaven\",\n" +
            "            \"stars\": \"5\"\n" +
            "        }\n" +
            "    ]\n" +
            "}" ; //assign the JSON String here
    JSONObject obj = new JSONObject(jsonString);
    public String getName(JSONObject obj) throws JSONException {
        String name = obj.getString("name");
        return name;
    }
    public double getprice(JSONObject obj) throws JSONException {
        double price = obj.getDouble("price");
        return price;
    }
    public double getLongitude(JSONObject obj) throws JSONException {
        double longitude = obj.getDouble("longitude");
        return longitude;
    }
    public double getLatitude(JSONObject obj) throws JSONException {
        double latitude = obj.getDouble("latitude");
        return latitude;
    }
    public String getTag(JSONObject obj) throws JSONException {
        String tag = obj.getString("tag");
        return tag;
    }
    public String getNavDescr(JSONObject obj) throws JSONException {
        String navDescr = obj.getString("navigationDescription");
        return navDescr;
    }
    public String getdescr(JSONObject obj) throws JSONException {
        String descr = obj.getString("description");
        return descr;
    }
    public String getIcon(JSONObject obj) throws JSONException {
        String icon = obj.getString("icon");
        return icon;
    }

    public String[][] getRatings(JSONObject obj) throws JSONException {
        JSONArray arr = obj.getJSONArray("rating");
        String[][] outerArray;
        outerArray = new String[arr.length()][];

        for (int i = 0; i < arr.length(); i++) {
            String user = arr.getJSONObject(i).getString("user");
            String ratingText = arr.getJSONObject(i).getString("ratingText");
            String stars = Integer.toString(arr.getJSONObject(i).getInt("stars"));
            String[] innerArray = {user,ratingText,stars};
            outerArray[i] = innerArray;
        }
    }

    public JSONParser() throws JSONException {
    }
}