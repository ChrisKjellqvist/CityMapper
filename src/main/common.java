package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by chris on 11/30/15.
 */
public class common {
    /**
     * Finds the working directory of the jar file and checks if the given file is present.
     */
    public static boolean fileExists(String a) {
        File asdf = new File((new File(new File(new File(common.class.getResource("common.class").getFile()).getParent()).
                getParent()).getParent() + "/" + a).substring(5));
        try {
            Scanner sc = new Scanner(asdf);
            sc.close();
        } catch (FileNotFoundException exc) {
            return false;
        }
        return true;
    }

    /*
    Get's the parent directory to the working directory
     */
    public static String getParentDirectory() {
        return (new File(new File(new File(common.class.getResource("common.class")
                .getFile()).getParent()).getParent()).getParent() + "/").substring(5);

    }

    /**
     * Gives the file path for the given file
     */
    public static String getFilePath(String a) {
        File asdf = new File((new File(new File(new File(common.class.getResource("common.class").getFile()).getParent()).getParent()).getParent() + "/" + a).substring(5));
        return asdf.toString();
    }

    /**
     * Given two doubles in a string (1.12532134 2.48395102) for instance, it
     * returns those two values in an array. The same applies for the associated
     * integer method but of course it is all done in integers instead of doubles.
     */
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
