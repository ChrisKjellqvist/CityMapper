package main;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by chris on 11/30/15.
 */
public class alert {
    final static Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);

    public static void display(String message) {
        JFrame frame = new JFrame();
        JLabel ma = new JLabel(message);
        ma.setBorder(padding);
        frame.add(ma);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
