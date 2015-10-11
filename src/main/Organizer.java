package main;

import java.io.*;
import java.util.Scanner;

/**
 * Created by chris on 9/25/15.
 */
public class Organizer {
    public static void main(String[] args) throws Exception {
        //[NW/SW][x/y]
        double[][] wholeCoords = new double[][]{{29.898913, -95.565513}, {29.608675, -95.168841}};
        int ydivs = 4;
        int xdivs = 5;
        double latDif = (wholeCoords[1][0] - wholeCoords[0][0]) / xdivs;
        double lonDif = (wholeCoords[1][1] - wholeCoords[0][1]) / ydivs;
        //[section number][NW/SE][coords]
        double[][][] sectionCoords = new double[20][2][2];
        for (int y = 0; y < ydivs; y++) {
            for (int x = 0; x < xdivs; x++) {
                for (int k = 0; k < 2; k++) {
                    sectionCoords[y * xdivs + x][k] = new double[]{wholeCoords[0][0] + (x + k) * latDif, wholeCoords[0][1] + (y + k) * lonDif};
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Which section do you want to complete today?");
        int sec = Integer.parseInt(sc.nextLine());
        String fileString_pre = "/home/chris/Dropbox/Houston/bin/" + sec;
        File[] files = {new File(fileString_pre + "data.txt"), new File(fileString_pre + "error.txt")};
        File status = new File(fileString_pre + "status.txt");
        if (!fileExists(status)) {
            BufferedWriter wr_status = new BufferedWriter(new FileWriter(status));
            wr_status.write("INCOMPLETE");
            gatherData.gatherData(files, sectionCoords[sec][0], sectionCoords[sec][1], 50);
            wr_status.write("COMPLETE");
            wr_status.close();
        } else {
            System.out.println("That section is already done, pick another.");
        }

    }

    public static boolean fileExists(File a) throws IOException {
        try {
            BufferedReader read = new BufferedReader(new FileReader(a));
            return read.readLine().equals("COMPLETE");
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
