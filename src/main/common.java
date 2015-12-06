package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by chris on 11/30/15.
 */
public class common {
    /*
    Finds the working directory of the jar file and checks if the given file is present.
     */
    public static boolean fileExists(String a) {
        File asdf = new File((new File(new File(new File(run.class.getResource("run.class").getFile()).getParent()).
                getParent()).getParent() + "/" + a).substring(5));
        try {
            Scanner sc = new Scanner(asdf);
            sc.close();
        } catch (FileNotFoundException exc) {
            return false;
        }
        return true;
    }
    public static String getParentDirectory() {
        return (new File(new File(new File(run.class.getResource("run.class")
                .getFile()).getParent()).getParent()).getParent() + "/").substring(5);

    }
}
