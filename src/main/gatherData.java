package main;

import json.JSONArray;
import json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by chris on 9/17/15.
 * Gathers data for the making of an address map.
 */
public class gatherData {

    //When asking google for geocoding data, you put it in the format below.
    static String prefix = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    static String suffix = "&key=";
    static int iteration = 0;
    static int range;

    /*
    run does most of the startup work for the gathering of data. It finds
    the coordinates of the box that you're getting addresses for, it asks
    you what iteration you're on, and then it starts gather().
     */
    public static void run() throws Exception {
        Scanner t = new Scanner(new File(common.getParentDirectory() + "api_key.txt"));
        suffix = suffix + t.nextLine();
        t.close();
        Scanner sc = new Scanner(new File(common.getParentDirectory() + "settings.txt"));
        String[] temp;
        double[] nw = new double[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            nw[i] = Double.parseDouble(temp[i]);
        }
        double[] se = new double[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            se[i] = Double.parseDouble(temp[i]);
        }
        int[] divs = new int[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            divs[i] = Integer.parseInt(temp[i]);
        }
        range = divs[0] * divs[1];
        getIteration();
        while (iteration == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Somehow an error occured?");
            }
        }

        //Finding which row and column of the picture we're on.
        int row;
        int column;
        row = iteration % divs[0];
        if (row == 0) {
            row = divs[0];
        }
        column = (int) Math.ceil((double) iteration / (double) divs[0]);

        /*
        Finding the coordinate constraints of the box of the picture we're on
        Since divs[0] are the number of times we're cutting up the x axis, we
        use the size of the division to find top left and bottom right corners
        of the box we're finding. dX and dY are the sizes of the x and y
        divisions, respectively. NWx, NWy are the coords of the northwest
        bounds and SEx, SEy are the coords of the southeast bounds.

        */
        double dX = (se[0] - nw[0]) / (double) divs[0];
        double dY = (se[1] - nw[1]) / (double) divs[1];

        double NWx = nw[0] + dX * (row - 1);
        double NWy = nw[1] + dY * (column - 1);
        double[] a = {NWx, NWy};

        double sex = nw[0] + dX * row;
        double sey = nw[1] + dY * column;
        double[] b = {sex, sey};
        File[] f = {new File("input_" + iteration + ".txt"), new File("err_" + iteration + ".txt")};
        gather(f, a, b, 50);
    }

    /*
    This is a box that pops up and asks you which box number
     */
    private static void getIteration() {
        QuestionBox box = new QuestionBox("Which iteration are you on?", true, 0, range);
        box.submit.addActionListener(e -> {
            try {
                int iter = Integer.parseInt(box.getText());
                if (common.fileExists(common.getParentDirectory() + "input_" + iter + ".txt")) {
                    box.text.setText("You've already done that one! Try again.");
                } else if (iter > range || iter <= 0) {
                    box.text.setText("Not in the iteration range! Try again.");
                } else {
                    iteration = iter;
                    box.dispose();
                }
            } catch (Exception exc) {
                System.out.println("error?");
            }
        });
    }

    private static void gather(File[] files, double[] NW, double[] SE, int div) throws Exception {

        //These are basically xD and yD from earlier except for the box itself.
        double HIterator = (NW[0] - SE[0]) / div;
        double VIterator = (NW[1] - SE[1]) / div;
        if (files.length != 2) {
            throw new Exception("Missing either an output file or an error file name");
        }

        /*
        Output file writers
         */
        BufferedWriter wr = new BufferedWriter(new FileWriter(common.getParentDirectory() + files[0]));
        BufferedWriter wr_er = new BufferedWriter(new FileWriter(common.getParentDirectory() + files[1]));
        wr.write(NW[0] + " " + NW[1] + " " + " " + SE[0] + " " + SE[1] + "\n");

        /*
        Just going across the map, one by one, and asking google via the getAddressNumber
        method what the address at each point is. If it's valid, it writes it down.
         */
        for (int i = 0; i < div; i++) {
            for (int j = 0; j < div; j++) {
                double x = NW[0] - (j * HIterator);
                double y = NW[1] - (i * VIterator);
                String address;
                address = getAddressNumber(x, y);
                try {
                    int n = Integer.parseInt(address);
                    if (n != 0) {
                        wr.write(i + " " + j + " " + address + "\n");
                    }
                } catch (Exception e) {
                    try {
                        int n_maybe = Integer.parseInt(address.substring(0, address.length() - 1));
                        wr.write(n_maybe);
                    } catch (Exception ex) {
                        wr_er.write(ex.toString());
                    }
                }
            }
        }
        wr.close();
        wr_er.close();
    }

    /*
    The input is expected to be prefix + x coords + , + y coords + suffix. This will
     return a JSONObject with all the data. Here's google's overview on it if you
     want to know more about what the API does.
     https://developers.google.com/maps/documentation/geocoding/intro

     Since you can't take the raw text return from google and put it into a JSONObject,
     you have to make it a string first and then make the JSON stuff do its work.
     */
    private static JSONObject getGoogleInput(URL u) throws IOException {
        InputStream b = u.openStream();
        BufferedReader a = new BufferedReader(new InputStreamReader(b));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = a.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    /*
    Gets the address number at a given x and y coordinate. Returns a string because
    occasionally duplex addresses get returned and those have a letter on the end.
     */
    private static String getAddressNumber(double x, double y) throws IOException {
        String url = prefix + x + "," + y + suffix;
        JSONObject input = getGoogleInput(new URL(url));
        JSONArray results = input.getJSONArray("results");
        JSONObject acresults = new JSONObject(results.get(0).toString());
        String[] formattedAddress = acresults.get("formatted_address").toString().split(" ")[0].split("-");
        return formattedAddress[0];
    }

    /*
    Gets the working directory path.
     */
    private static String getFilePath(String a) {
        File asdf = new File((new File(new File(new File(run.class.getResource("run.class").getFile()).getParent()).getParent()).getParent() + "/" + a).substring(5));
        return asdf.toString();
    }


}