package testing;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by chris on 10/11/15.
 */
public class test {
    private static String a = "asdf";
    private JTextField NWx;
    private JTextField NWy;
    private JTextField SEx;
    private JTextField SEy;
    private JSlider slider1;
    private JSlider slider2;
    private JPanel something;

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        test b = new test();
        frame.setContentPane(b.something);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                a = b.NWx.getText();
                System.out.println(a);
            }

        });
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


}
