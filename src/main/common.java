package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by chris on 11/30/15.
 * These are all a bunch of methods I use frequently throughout.
 */
public class common {

    /*
    This padding makes the text in a box by itself readable.
     */
    final static Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);


    /*
    Makes a popup window with whatever text you want alone in it.
     */
    public static void display(String message) {
        JFrame frame = new JFrame();
        JLabel ma = new JLabel(message);
        ma.setBorder(padding);
        frame.add(ma);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /*
    Originally I was going to navigate to files with "./"s but that didn't work. So instead I
    had to get the absolute filename. This gets the filepath for the run class (which is
    completely arbitrary but it's what I chose), and since that file is two files down from
    the jar file itself, you have to go up 3 to get the file containing the jar and then add
    the filename of the actual file you're looking for. The substring at the end is just to
    chop the "file:" off the beginning of the filepath which was for some reason part of the
    generated path.
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
        return (new File(new File(new File(run.class.getResource("run.class").getFile()).getParent()).getParent()).getParent() + "/").substring(5);

    }
}
