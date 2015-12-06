package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by chris on 12/6/15.
 */
public class CoordinateEntries extends JPanel {
    Dimension textFieldDimensions = new Dimension(200, 20);
    private Border defaultBorder = BorderFactory.createEmptyBorder(2, 2, 2, 5);
    private JLabel title;
    private JTextField x;
    private JTextField y;

    public CoordinateEntries(String title, LayoutManager l) {
        super();
        this.setLayout(l);

        this.title = new JLabel(title);
        this.title.setBorder(defaultBorder);
        this.add(this.title, 0);

        x.setText("x");
        y.setText("y");
        x.setBorder(defaultBorder);
        y.setBorder(defaultBorder);
        x.setPreferredSize(textFieldDimensions);
        y.setPreferredSize(textFieldDimensions);

        this.add(x, 1);
        this.add(y, 2);
    }

    public void setDefaultBorder(Border defaultBorder) {
        this.defaultBorder = defaultBorder;
    }

    public void setTextFieldDimensions(Dimension textFieldDimensions) {
        this.textFieldDimensions = textFieldDimensions;
    }

    public JLabel getTitle() {
        return title;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public JTextField getxcoord() {
        return x;
    }

    public void setxcoord(JTextField x) {
        this.x = x;
    }

    public JTextField getycoord() {
        return y;
    }

    public void setycoord(JTextField y) {
        this.y = y;
    }

}
