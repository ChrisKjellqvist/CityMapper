package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by chris on 12/1/15.
 * Control class that will be run when the jar is run.
 */
public class run {
    public static void main(String[] args) {
        alert.display(run.class.getResource("run.class").toString());
        if (!(fileExists(new File("./settings.txt")))) {
            alert.display("couldn't find settings.");
            //start.run();
        }
        if (!(fileExists(new File("./api_key.txt")))) {
            alert.display("Make your api_key.txt pleaseaa.");
        }

        /*else {
            gatherData.run();
        }*/
    }

    public static boolean fileExists(File a) {
        try {
            Scanner sc = new Scanner(a);
        } catch (FileNotFoundException exc) {
            return false;
        }
        return true;
    }
}
