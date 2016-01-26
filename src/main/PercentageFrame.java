package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by chris on 1/24/16.
 */
public class PercentageFrame extends JFrame{
    Dimension dimension;
    double perc;
    private PercentagePanel ps;
    public PercentageFrame(Dimension dim){
        setPreferredSize(dim);
        dimension = dim;
        ps = new PercentagePanel(perc, dimension);
        add(ps);
        setPreferredSize(dimension);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setPerc (double p){
        perc = p;
        ps.setPercentage(perc);
        ps.repaint();
    }
}
