package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by chris on 1/24/16.
 */
public class PercentagePanel extends JPanel{
    private double percentage;
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private Dimension dim;
    public PercentagePanel(double defPercentage, Dimension d){
        percentage = defPercentage;
        this.setPreferredSize(d);
        dim = d;
    }

    @Override
    public void paint(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, (int)(dim.width * percentage), dim.height);
        g.setColor(Color.red);
        g.drawString(toPercentageString(percentage) + "%", (int) (dim.width*percentage/2.0)-10, dim.height/2);
        tk.sync();
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public static String toPercentageString(double a){
        a*=100;
        char[] ar = Double.toString(a).toCharArray();
        String str = "";
        for (char b: ar){
            if(b!='.'){
                str+=b;
            } else {
                break;
            }
        }
        return str;
    }
}
