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
        return (new File(new File(new File(run.class.getResource("common.class")
                .getFile()).getParent()).getParent()).getParent() + "/").substring(5);

    }

    public static String getFilePath(String a) {
        File asdf = new File((new File(new File(new File(run.class.getResource("run.class").getFile()).getParent()).getParent()).getParent() + "/" + a).substring(5));
        return asdf.toString();
    }

    public static double[] getDoubles(String a) {
        String[] split = a.split(" ");
        double[] b = new double[2];
        for (int i = 0; i < 2; i++) {
            b[i] = Double.parseDouble(split[i]);
        }
        return b;
    }

    public static int[] getIntegers(String a) {
        String[] split = a.split(" ");
        int[] b = new int[2];
        for (int i = 0; i < 2; i++) {
            b[i] = Integer.parseInt(split[i]);
        }
        return b;
    }
}
