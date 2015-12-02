package main;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by chris on 10/13/15.
 * This is the program run the first time the program is run. All other times, gatherData should go instead.
 */
public class start {
    public static void run() throws IOException {

        //Designing all of the Rows
        Dimension TextFieldDimensions = new Dimension(200, 20);
        JFrame frame = new JFrame("CityMapper");
        JPanel full = new JPanel();
        BoxLayout box = new BoxLayout(full, BoxLayout.Y_AXIS);
        full.setLayout(box);
        Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);
        frame.add(full);

        JPanel Row1 = new JPanel(new FlowLayout());
        JLabel NWCoords = new JLabel("Northwest Coordinates:");
        NWCoords.setBorder(padding);
        Row1.add(NWCoords, 0);
        JTextField NWx = new JTextField("X");
        NWx.setPreferredSize(TextFieldDimensions);
        NWx.setBorder(padding);
        Row1.add(NWx, 1);
        JTextField NWy = new JTextField("Y");
        NWy.setPreferredSize(TextFieldDimensions);
        NWy.setBorder(padding);
        Row1.add(NWy, 2);
        full.add(Row1);


        JPanel Row2 = new JPanel(new FlowLayout());
        JLabel SECoords = new JLabel("SouthEast Coordinates:");
        SECoords.setBorder(padding);
        Row2.add(SECoords, 0);
        JTextField SEx = new JTextField("X");
        SEx.setPreferredSize(TextFieldDimensions);
        SEx.setBorder(padding);
        Row2.add(SEx, 1);
        JTextField SEy = new JTextField("Y");
        SEy.setPreferredSize(TextFieldDimensions);
        SEy.setBorder(padding);
        Row2.add(SEy, 2);
        full.add(Row2);

        JPanel Row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel Divisions = new JLabel("Divisions:");
        Divisions.setBorder(padding);
        Divisions.setAlignmentX(Component.LEFT_ALIGNMENT);
        Row3.add(Divisions);
        full.add(Row3);

        JPanel Row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        Row4.add(xDivLabel);
        Row4.add(xDivs);
        Row4.add(daysToComplete);
        full.add(Row4);

        JPanel Row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        JTextField City = new JTextField("City");
        City.setAlignmentX(Component.LEFT_ALIGNMENT);
        City.setBorder(padding);
        City.setPreferredSize(TextFieldDimensions);
        Row5.add(yDivLabel);
        Row5.add(yDivs);
        Row5.add(City);
        full.add(Row5);

        JPanel Row6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel MapInfo = new JLabel("Map Info:");
        MapInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        MapInfo.setBorder(padding);
        Row6.add(MapInfo);
        full.add(Row6);

        JPanel Row7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel ZoomLabel = new JLabel("Zoom Level: ");
        ZoomLabel.setBorder(padding);
        ZoomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JSlider Zoom = new JSlider(0, 20, 14);
        Zoom.setMajorTickSpacing(5);
        Zoom.setMinorTickSpacing(1);
        Zoom.setPaintLabels(true);
        Zoom.setSnapToTicks(true);
        Zoom.setPaintTicks(true);
        Zoom.setAlignmentX(Component.LEFT_ALIGNMENT);
        Zoom.setBorder(padding);
        JButton FetchMap = new JButton("Fetch Map");
        FetchMap.setBorder(padding);
        FetchMap.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton save = new JButton("Save Settings");
        save.setBorder(padding);
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        Row7.add(ZoomLabel);
        Row7.add(Zoom);
        Row7.add(FetchMap);
        Row7.add(save);
        full.add(Row7);

        ImagePanel mapPane = new ImagePanel();
        mapPane.setBackground(Color.cyan);
        mapPane.setPreferredSize(new Dimension(400, 400));
        mapPane.setSize(400, 400);
        mapPane.setBorder(padding);
        full.add(mapPane);

        //Listeners
        FetchMap.addActionListener(e -> {

            String str1 = "https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=";
            String CityS = City.getText();
            for (int i = 0; i < CityS.length(); i++) {
                if (CityS.charAt(i) == ' ') {
                    CityS = CityS.substring(0, i) + "_"
                            + CityS.substring(i + 1, CityS.length());
                }
            }
            String str2 = CityS + "&";
            String str3 = "zoom=" + Integer.toString(Zoom.getValue()) + "&scale=2&";
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

        yDivs.addChangeListener(e -> daysToComplete.setText(Integer.toString(xDivs.getValue() * yDivs.getValue())));

        xDivs.addChangeListener(e -> daysToComplete.setText(Integer.toString(xDivs.getValue() * yDivs.getValue())));

        save.addActionListener(e -> {
            double[] NW = new double[2];
            double[] SE = new double[2];
            try {
                NW[0] = Double.parseDouble(NWx.getText());
                NW[1] = Double.parseDouble(NWy.getText());
                SE[0] = Double.parseDouble(SEx.getText());
                SE[1] = Double.parseDouble(SEy.getText());
            } catch (Exception excpetion) {
                alert.display("There's something wrong with the coordinates you entered!");
            }
            int divs[] = {xDivs.getValue(), yDivs.getValue()};
            int zoom = Zoom.getValue();
            String city = City.getText();
            try {
                BufferedWriter wr = new BufferedWriter(new FileWriter(new File("settings.txt")));
                wr.write(Double.toString(NW[0]) + " " + Double.toString(NW[1]) + "\n");
                wr.write(Double.toString(SE[0]) + " " + Double.toString(SE[1]) + "\n");
                wr.write(Integer.toString(divs[0]) + " " + Integer.toString(divs[1]) + "\n");
                wr.write(Integer.toString(zoom) + " " + city);
                if (fileExists(new File("settings.txt"))) {
                    alert.display("overwriting previous settings!!!");
                }
                wr.close();
            } catch (IOException exc) {
                System.out.println("error?");
            }

        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static boolean fileExists(File a) throws IOException {
        try {
            Scanner sc = new Scanner(a);
            sc.close();
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public static class ImagePanel extends JPanel {
        Image pic;

        public void setPic(Image pic) {
            this.pic = pic;
            repaint();
        }

        @Override
        public void paintComponent(Graphics g) {
            //Paint background first
            super.paintComponent(g);
            g.drawImage(pic, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
