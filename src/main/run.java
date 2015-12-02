package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by chris on 12/1/15.
 * Control class that will be run when the jar is run.
 */
public class run {
    public static void main(String[] args) throws Exception {
        if (!fileExists(new File("settings.txt"))) {
            start.run();
        } else if (!fileExists(new File("api_key.txt"))) {
            alert.display("Make your api_key.txt please.");
        } else {
            gatherData.run();
        }
    }

    public static boolean fileExists(File a) {
        try {
            Scanner sc = new Scanner(a);
            sc.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

}
