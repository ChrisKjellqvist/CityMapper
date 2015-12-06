package main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by chris on 12/6/15.
 */
public class DefaultSlider extends JSlider {
    public DefaultSlider(int min, int max) {
        super();
        this.setMinimum(min);
        this.setMaximum(max);
        this.setValue((max + min) / 2);
        this.setPreferredSize(new Dimension(200, 40));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setMajorTickSpacing(1);
        this.setPaintTicks(true);
        this.setSnapToTicks(true);
        this.setPaintLabels(true);
    }
}
