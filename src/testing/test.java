package testing;

import javax.swing.*;

/**
 * Created by chris on 10/11/15.
 */
public class test {
    private JTextField NWx;
    private JTextField NWy;
    private JTextField SEx;
    private JTextField SEy;
    private JSlider slider1;
    private JSlider slider2;
    private JPanel something;

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test().something);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
