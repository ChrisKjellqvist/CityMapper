package main;

import json.JSONArray;
import json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by chris on 12/6/15.
 */
public class GoogleAddress {
    private static String prefix = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private static String suffix = "&key=";
    private double x;
    private double y;

    public GoogleAddress(double x, double y, String api) {
        this.x = x;
        this.y = x;
        suffix += api;
    }

    /**
     * The input is expected to be prefix + x coords + , + y coords + suffix. This will
     * return a JSONObject with all the data. Here's google's overview on it if you
     * want to know more about what the API does.
     * https://developers.google.com/maps/documentation/geocoding/intro
     * <p>
     * Since you can't take the raw text return from google and put it into a JSONObject,
     * you have to make it a string first and then make the JSON stuff do its work.
     */
    public static JSONObject getGoogleInput(URL u) throws IOException {
        InputStream b = u.openStream();
        BufferedReader a = new BufferedReader(new InputStreamReader(b));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = a.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }


    /**
     * Gets the address number at a given x and y coordinate. Returns a string because
     * occasionally duplex addresses get returned and those have a letter on the end.
     */
    public String getAddressNumber() throws IOException {
        String url = prefix + this.x + "," + this.y + suffix;
        JSONObject input = getGoogleInput(new URL(url));
        JSONArray results = input.getJSONArray("results");
        JSONObject acresults = new JSONObject(results.get(0).toString());
        String[] formattedAddress = acresults.get("formatted_address").toString().split(" ")[0].split("-");
        return formattedAddress[0];
    }
}
