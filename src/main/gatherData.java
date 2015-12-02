package main;

import json.JSONArray;
import json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by chris on 9/17/15.
 * Gathers data for the making of an address map.
 */
public class gatherData {
    static String prefix = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    static String suffix = "&key=";
    static int iteration = 0;
    static int range = 10;

    public static void run() throws Exception {

        //Startup shit
        if (!fileExists(new File("settings.txt"))) {
            alert.display("Run start first, please.");
            System.exit(0);
        }
        if (!fileExists(new File("api_key.txt"))) {
            alert.display("Make your api_key.txt please.");
            System.exit(0);
        } else {
            Scanner t = new Scanner(new File("api_key.txt"));
            suffix = suffix + t.nextLine();
            t.close();
        }
        Scanner sc = new Scanner(new File("settings.txt"));
        String[] temp;
        double[] NW = new double[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            NW[i] = Double.parseDouble(temp[i]);
        }
        double[] SE = new double[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            SE[i] = Double.parseDouble(temp[i]);
        }
        int[] divs = new int[2];
        temp = sc.nextLine().split(" ");
        for (int i = 0; i < 2; i++) {
            divs[i] = Integer.parseInt(temp[i]);
        }
        temp = sc.nextLine().split(" ");
        int zoom = Integer.parseInt(temp[0]);
        String city = temp[1];
        range = divs[0] * divs[1];
        iterationRequest();
        while (iteration == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

        //Finding which row and column of the picture we're on.
        int row = 0;
        int column = 0;
        row = iteration % divs[0];
        if (row == 0) {
            row = divs[0];
        }
        column = (int) Math.ceil((double) iteration / (double) divs[0]);

        //Finding the coordinate constraints of the box of the picture we're on
        double dX = (SE[0] - NW[0]) / (double) divs[0];
        double dY = (SE[1] - NW[1]) / (double) divs[1];

        double NWx = NW[0] + dX * (row - 1);
        double NWy = NW[1] + dY * (column - 1);
        double[] a = {NWx, NWy};

        double SEx = NW[0] + dX * row;
        double SEy = NW[1] + dY * column;
        double[] b = {SEx, SEy};
        File[] f = {new File("input_" + iteration + ".txt"), new File("err_" + iteration + ".txt")};
        //gatherData(f, a, b, 200);
    }

    private static void iterationRequest() {
        JFrame frame = new JFrame("8==D");
        JPanel panel = new JPanel();
        frame.setPreferredSize(new Dimension(600, 160));
        Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);
        JLabel whichIt = new JLabel("Which Iteration are you on?");
        whichIt.setPreferredSize(new Dimension(300, 80));
        whichIt.setBorder(padding);
        JTextField ans = new JTextField();
        ans.setBorder(padding);
        ans.setPreferredSize(new Dimension(40, 30));
        JButton submit = new JButton("submit");
        submit.setBorder(padding);
        panel.add(whichIt);
        panel.add(ans);
        panel.add(submit);
        panel.setPreferredSize(new Dimension(600, 80));
        frame.add(panel);
        frame.pack();
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int iter = Integer.parseInt(ans.getText());
                    if (fileExists(new File("input_" + iter + ".txt"))) {
                        whichIt.setText("You've already done that one! Try again.");
                    } else if (iter > range || iter <= 0) {
                        whichIt.setText("Not in the iteration range! Try again.");
                    } else {
                        iteration = iter;
                        frame.dispose();
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        });
        frame.setVisible(true);
    }

    private static void gatherData(File[] files, double[] NW, double[] SE, int div) throws Exception {

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

    private static JSONObject getGoogleInput(URL u) throws IOException {
        InputStream b = u.openStream();
        BufferedReader a = new BufferedReader(new InputStreamReader(b));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = a.readLine()) != null) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    private static int getAddressNumber(double x, double y) throws IOException {
        String url = prefix + x + "," + y + suffix;
        JSONObject input = getGoogleInput(new URL(url));
        JSONArray results = input.getJSONArray("results");
        JSONObject acresults = new JSONObject(results.get(0).toString());
        String[] formattedAddress = acresults.get("formatted_address").toString().split(" ")[0].split("-");
        return Integer.parseInt(formattedAddress[0]);
    }
    private static boolean fileExists(File a) throws IOException {
        try {
            Scanner sc = new Scanner(a);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}