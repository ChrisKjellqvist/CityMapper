package main;

import json.JSONArray;
import json.JSONObject;

import java.io.*;
import java.net.URL;

/**
 * Created by chris on 9/17/15.
 */
public class gatherData {
    final static String prefix = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    final static String suffix = "&key=AIzaSyAG3MBBOC0LVqRE3ZOYiRqOvZ7DyTzoRzU";

    public static void gatherData(File[] files, double[] NW, double[] SE, int div) throws Exception {

        double HIterator = (NW[0] - SE[0]) / div;
        double VIterator = (NW[1] - SE[1]) / div;
        if (files.length != 2) {
            throw new Exception("Missing either an output file or an error file name");
        }
        BufferedWriter wr = new BufferedWriter(new FileWriter(files[0]));
        BufferedWriter wr_er = new BufferedWriter(new FileWriter(files[1]));

        wr.write(NW[0] + " " + NW[1] + " " + " " + SE[0] + " " + SE[1] + "\n");
        for (int i = 0; i < div; i++) {
            for (int j = 0; j < div; j++) {
                double x = NW[0] - (j * HIterator);
                double y = NW[1] - (i * VIterator);
                int address = 0;
                try {
                    address = getAddressNumber(x, y);
                    if (address != 0) {
                        wr.write(i + " " + j + " " + address + "\n");
                    }
                } catch (Exception e) {
                    wr_er.write(e + "\n");
                }
            }
        }
        wr.close();
        wr_er.close();
    }

    public static JSONObject getGoogleInput(URL u) throws IOException {
        InputStream b = u.openStream();
        BufferedReader a = new BufferedReader(new InputStreamReader(b));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = a.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    public static int getAddressNumber(double x, double y) throws IOException {
        String url = prefix + x + "," + y + suffix;
        JSONObject input = getGoogleInput(new URL(url));
        JSONArray results = input.getJSONArray("results");
        JSONObject acresults = new JSONObject(results.get(0).toString());
        String[] formattedAddress = acresults.get("formatted_address").toString().split(" ")[0].split("-");
        return Integer.parseInt(formattedAddress[0]);
    }
}