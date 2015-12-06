package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * Created by chris on 10/13/15.
 * This is the GUI for generating the settings file. It's really only useful if you're
 * making your own settings file for doing this personally. If you're working with
 * other people, it's just a lot simpler to send them the settings file yourself.
 *
 * To add: the numbers for the x and y values for the NW and SE coordinates have been
 * looked up manually by me. Google maps doesn't let you get a map based off of coordinates,
 * you have to use a location and a zoom level. I tried a little to find if I could come
 * up with a relationship between zoom and coordinate difference from the center of the
 * picture but it was too much work compared to just doing it manually so that's how I'm
 * doing it.
 */
public class startupbox {
    public static void run() throws IOException {

        //Designing all of the Rows. The names for the components are pretty self explanatory
        Dimension TextFieldDimensions = new Dimension(200, 20);
        JFrame frame = new JFrame("CityMapper");
        JPanel full = new JPanel();
        BoxLayout box = new BoxLayout(full, BoxLayout.Y_AXIS);
        full.setLayout(box);
        Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);
        frame.add(full);

        JPanel row1 = new JPanel(new FlowLayout());
        JLabel nWCoords = new JLabel("Northwest Coordinates:");
        nWCoords.setBorder(padding);
        row1.add(nWCoords, 0);
        JTextField nWx = new JTextField("X"); //MAKE THIS A CLASS - NAMING CONVENTIONS
        nWx.setPreferredSize(TextFieldDimensions);
        nWx.setBorder(padding);
        row1.add(nWx, 1);
        JTextField nWy = new JTextField("Y");
        nWy.setPreferredSize(TextFieldDimensions);
        nWy.setBorder(padding);
        row1.add(nWy, 2);
        full.add(row1);


        JPanel row2 = new JPanel(new FlowLayout());
        JLabel sECoords = new JLabel("SouthEast Coordinates:");
        sECoords.setBorder(padding);
        row2.add(sECoords, 0);
        JTextField sEx = new JTextField("X");
        sEx.setPreferredSize(TextFieldDimensions);
        sEx.setBorder(padding);
        row2.add(sEx, 1);
        JTextField sEy = new JTextField("Y");
        sEy.setPreferredSize(TextFieldDimensions);
        sEy.setBorder(padding);
        row2.add(sEy, 2);
        full.add(row2);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel divisions = new JLabel("divisions:");
        divisions.setBorder(padding);
        divisions.setAlignmentX(Component.LEFT_ALIGNMENT);
        row3.add(divisions);
        full.add(row3);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel xDivLabel = new JLabel("X divisions: ");
        xDivLabel.setBorder(padding);
        JSlider xDivs = new JSlider(1, 10, 4);
        xDivs.setBorder(padding);
        xDivs.setPreferredSize(new Dimension(200, 40));
        xDivLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        xDivs.setAlignmentX(Component.LEFT_ALIGNMENT);
        xDivs.setMajorTickSpacing(1);
        xDivs.setPaintTicks(true);
        xDivs.setSnapToTicks(true);
        xDivs.setPaintLabels(true);
        JLabel daysToComplete = new JLabel(" ");
        daysToComplete.setBorder(padding);
        daysToComplete.setText("123");
        daysToComplete.setAlignmentX(Component.LEFT_ALIGNMENT);
        row4.add(xDivLabel);
        row4.add(xDivs);
        row4.add(daysToComplete);
        full.add(row4);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel yDivLabel = new JLabel("Y divisions: ");
        yDivLabel.setBorder(padding);
        JSlider yDivs = new JSlider(1, 10, 4);
        yDivs.setBorder(padding);
        yDivs.setPreferredSize(new Dimension(200, 40));
        yDivs.setAlignmentX(Component.LEFT_ALIGNMENT);
        yDivLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        yDivs.setMajorTickSpacing(1);
        yDivs.setPaintTicks(true);
        yDivs.setSnapToTicks(true);
        yDivs.setPaintLabels(true);
        JTextField city = new JTextField("City");
        city.setAlignmentX(Component.LEFT_ALIGNMENT);
        city.setBorder(padding);
        city.setPreferredSize(TextFieldDimensions);
        row5.add(yDivLabel);
        row5.add(yDivs);
        row5.add(city);
        full.add(row5);

        JPanel row6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel mapInfo = new JLabel("Map Info:");
        mapInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapInfo.setBorder(padding);
        row6.add(mapInfo);
        full.add(row6);

        JPanel row7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel zoomLabel = new JLabel("Zoom Level: ");
        zoomLabel.setBorder(padding);
        zoomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JSlider zoom = new JSlider(0, 20, 14);
        zoom.setMajorTickSpacing(5);
        zoom.setMinorTickSpacing(1);
        zoom.setPaintLabels(true);
        zoom.setSnapToTicks(true);
        zoom.setPaintTicks(true);
        zoom.setAlignmentX(Component.LEFT_ALIGNMENT);
        zoom.setBorder(padding);
        JButton fetchMap = new JButton("Fetch Map");
        fetchMap.setBorder(padding);
        fetchMap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton save = new JButton("Save Settings");
        save.setBorder(padding);
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        row7.add(zoomLabel);
        row7.add(zoom);
        row7.add(fetchMap);
        row7.add(save);
        full.add(row7);

        ImagePanel mapPane = new ImagePanel();
        mapPane.setBackground(Color.cyan);
        mapPane.setPreferredSize(new Dimension(400, 400));
        mapPane.setSize(400, 400);
        mapPane.setBorder(padding);
        full.add(mapPane);

        /*
        This grabs a map of the area you might be making a map of.
         */
        fetchMap.addActionListener(e -> {

            String str1 = "https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=";
            String cityS = city.getText();
            for (int i = 0; i < cityS.length(); i++) {
                if (cityS.charAt(i) == ' ') {
                    cityS = cityS.substring(0, i) + "_"
                            + cityS.substring(i + 1, cityS.length());
                }
            }
            String str2 = cityS + "&";
            String str3 = "zoom=" + Integer.toString(zoom.getValue()) + "&scale=2&";
            String str4 = "size=400x400&key=AIzaSyAG3MBBOC0LVqRE3ZOYiRqOvZ7DyTzoRzU";
            BufferedImage image;
            try {
                URL u = new URL(str1 + str2 + str3 + str4);
                InputStream stream = u.openStream();
                image = ImageIO.read(stream);
                mapPane.setPic(image);
            } catch (Exception ex) {
                System.out.println("error?");
            }
        });
        /*
        These next two tell you how many boxes you have in your complete picture. Supposing
        you do 1 box per day, it should be about how many days it takes you to complete the
        picture too.
         */

        yDivs.addChangeListener(e -> daysToComplete.setText(Integer.toString(xDivs.getValue() * yDivs.getValue())));

        xDivs.addChangeListener(e -> daysToComplete.setText(Integer.toString(xDivs.getValue() * yDivs.getValue())));

        /*
        This saves the data you entered into a settings file.
         */
        save.addActionListener(e -> {
            double[] nw = new double[2];
            double[] se = new double[2];
            try {
                nw[0] = Double.parseDouble(nWx.getText());
                nw[1] = Double.parseDouble(nWy.getText());
                se[0] = Double.parseDouble(sEx.getText());
                se[1] = Double.parseDouble(sEy.getText());
            } catch (Exception exception) {
                new MessageBox("There's something wrong with the coordinates you entered!").setVisible(true);
            }
            int divs[] = {xDivs.getValue(), yDivs.getValue()};
            int z = zoom.getValue();
            String c = city.getText();
            try {
                BufferedWriter wr = new BufferedWriter(new FileWriter(new File(common.getParentDirectory() + "settings.txt")));
                wr.write(Double.toString(nw[0]) + " " + Double.toString(nw[1]) + "\n");
                wr.write(Double.toString(se[0]) + " " + Double.toString(se[1]) + "\n");
                wr.write(Integer.toString(divs[0]) + " " + Integer.toString(divs[1]) + "\n");
                wr.write(Integer.toString(z) + " " + city);
                if (common.fileExists("settings.txt")) {
                    new MessageBox("overwriting previous settings!!!").setVisible(true);
                }
                wr.close();
                new MessageBox("data written").setVisible(true);
                frame.dispose();
            } catch (IOException exc) {
                new MessageBox("error?").setVisible(true);
            }

        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
