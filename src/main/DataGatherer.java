package main;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Created by chris on 9/17/15.
 * Gathers data for the making of an address map.
 */
public class DataGatherer {
    static String apiKey;
    static int iteration = 0;
    static int range;
    private PercentageFrame frame;
    public void run() throws Exception {
        Scanner t = new Scanner(new File(common.getParentDirectory() + "api_key.txt"));
        apiKey = t.nextLine();
        t.close();
        Scanner sc = new Scanner(new File(common.getParentDirectory() + "settings.txt"));
        String[] temp;
        double[] nw = common.getDoubles(sc.nextLine());
        double[] se = common.getDoubles(sc.nextLine());
        int[] divs = common.getIntegers(sc.nextLine());
        range = divs[0] * divs[1];
        int iteration = Integer.parseInt(new QuestionBox("What iteration are you on", 0, range).getAnswer(true));
        frame = new PercentageFrame(new Dimension(300,75));
        //Finding which row and column of the picture we're on.
        int row = iteration % divs[0];
        int column = (int) Math.ceil((double) iteration / (double) divs[0]);
        if (row == 0) {
            row = divs[0];
        }

        /*
        Finding the size of the box we're now working on.
        */
        double dX = findRateOfChange(se[0], nw[0], divs[0]);
        double dY = findRateOfChange(se[1], nw[1], divs[1]);

        double NWx = nw[0] + dX * (row - 1);
        double NWy = nw[1] + dY * (column - 1);
        double[] a = {NWx, NWy};

        double sex = nw[0] + dX * row;
        double sey = nw[1] + dY * column;
        double[] b = {sex, sey};

        /*
        Actual data gathering happens here
         */
        File[] f = {new File("input_" + iteration + ".txt"), new File("err_" + iteration + ".txt")};
        gather(f, a, b, 50);
    }

    private void gather(File[] files, double[] NW, double[] SE, int div) throws Exception {
        frame.setVisible(true);
        //These are basically xD and yD from earlier except for the box itself.
        double HIterator = (NW[0] - SE[0]) / div;
        double VIterator = (NW[1] - SE[1]) / div;
        if (files.length != 2) {
            throw new Exception("Missing either an output file or an error file name");
        }
        BufferedWriter wr = new BufferedWriter(new FileWriter(common.getParentDirectory() + files[0]));
        BufferedWriter wr_er = new BufferedWriter(new FileWriter(common.getParentDirectory() + files[1]));
        wr.write(NW[0] + " " + NW[1] + " " + " " + SE[0] + " " + SE[1] + "\n");

        double x = 0;
        double y = 0;
        try {
            for (int i = 0; i < div; i++) {
                for (int j = 0; j < div; j++) {
                    try {
                        frame.setPerc((double)(i*div+j)/(double)(div*div));
                        x = NW[0] - (j * HIterator);
                        y = NW[1] - (i * VIterator);
                        GoogleAddress current = new GoogleAddress(x, y, apiKey);
                        String address = current.getAddressNumber();
                        int n = Integer.parseInt(address);
                        if (n != 0) {
                            wr.write(i + " " + j + " " + address + "\n");
                        }
                    } catch (Exception e) {
                        wr_er.write(e.toString() + "\t" + x + " " + y + "\n");
                        System.out.println(e.toString() + "\t" + x + " " + y + "\n");
                    }
                }
            }
        } catch (Exception e) {
            wr_er.write(e.toString() + "\t" + x + " " + y + "\n");
            System.out.println(e.toString() + "\t" + x + " " + y + "\n");
        }
        frame.dispose();
        wr.close();
        wr_er.close();
    }

    private double findRateOfChange(double a, double b, int divisions) {
        return (a - b) / divisions;
    }


}