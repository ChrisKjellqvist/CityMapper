package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chris on 9/21/15.
 * Uses data created by gatherData to construct an overlay for a map image.
 */
public class makeMap {
    public static void main(String[] args) throws IOException {
        makeMap();
    }

    public static void makeMap() throws IOException {
        HSBColor red = new HSBColor(0, 255, 255);
        final int dataSize = 50;
        //INPUT DATA
        BufferedReader read = new BufferedReader(new FileReader("output.txt"));
        String[] coordsSplit = read.readLine().split(" ");
        try {
            double[] NW = {Double.parseDouble(coordsSplit[0]), Double.parseDouble(coordsSplit[1])};
            double[] SE = {Double.parseDouble(coordsSplit[2]), Double.parseDouble(coordsSplit[3])};
        } catch (Exception e) {
            System.out.println(coordsSplit[0] + " " + coordsSplit[1] + " " + coordsSplit[2] + " " + coordsSplit[3]);
            throw e;
        }
        int[][] data = new int[dataSize][dataSize];
        String str = "";
        while ((str = read.readLine()) != null) {
            String[] spl = str.split(" ");
            int x = Integer.parseInt(spl[1]);
            int y = Integer.parseInt(spl[0]);
            int address = Integer.parseInt(spl[2]);
            data[x][y] = address;
        }


        //PUT DATA IN A LIST
        ArrayList<Integer> vals = new ArrayList<>();
        for (int[] a : data) {
            for (int b : a) {
                if (b < 8000) {
                    vals.add(b);
                }
            }
        }
        //FIND MAX
        double max = 0;
        for (int a : vals) {
            if (a > max) {
                max = a;
            }
        }
        //FINDING PERCENTAGES
        double[][] overlayVals = new double[400][400];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dataSize; j++) {
                double percentage = 0.0;
                if (data[j][i] != 0) {
                    percentage = (double) data[j][i] / max;
                }
                for (int k = 0; k < 400 / dataSize; k++) {
                    for (int l = 0; l < 400 / dataSize; l++) {
                        overlayVals[j * (400 / dataSize) + k][i * (400 / dataSize) + l] = percentage;
                    }
                }
            }
        }
        //GET MAP
        BufferedImage map = ImageIO.read(new File("maps3.png"));
        BufferedImage finalMap = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                int finalRGB;
                int mapInt = map.getRGB(j, i);
                Color mapC = new Color(mapInt);
                finalRGB = combine(new HSBColor(mapC.getRed(), mapC.getGreen(), mapC.getBlue()),
                        red, (1 - overlayVals[j][i])).RGBV;
                finalMap.setRGB(j, i, finalRGB);
            }
        }
        ImageIO.write(finalMap, "png", new File("map.png"));
    }

    public static int toRGBValue(int R, int G, int B) {
        return (R * 256 * 256) + G * 256 + B;
    }

    public static int toRGBValue(HSBColor a) {
        return a.RGB[0] * 256 * 256 + a.RGB[1] * 256 + a.RGB[2];
    }

    public static int[] toRGBArray(int n) {
        int[] color = {0, 0, 0};
        while (n - 256 * 256 >= 0) {
            color[0]++;
            n -= (256 * 256);
        }
        while (n - 256 >= 0) {
            color[1]++;
            n -= 256;
        }
        color[2] = n;
        return color;
    }

    public static HSBColor combine(HSBColor a, HSBColor b, double ratio) {
        double[] finalVals = new double[3];
        for (int i = 0; i < 3; i++) {
            finalVals[i] = a.HSV[i] * ratio + b.HSV[i] * (1 - ratio);
        }
        return new HSBColor(finalVals[0], finalVals[1], a.HSV[2]);
    }

    public static class HSBColor {
        double[] HSV = new double[3];
        int[] RGB = new int[3];
        int RGBV;

        public HSBColor(int R, int G, int B) {
            RGB[0] = R;
            RGB[1] = G;
            RGB[2] = B;
            RGBV = (256 * 256) * R + 256 * G + B;
            double r = (double) R / 255.0;
            double g = (double) G / 255.0;
            double b = (double) B / 255.0;
            double min = Math.min(r, Math.min(g, b));
            double max = Math.max(r, Math.max(g, b));
            double delta = max - min;
            if (r == max) {
                HSV[0] = 60 * (((g - b) / delta) % 6);
            } else if (g == max) {
                HSV[0] = 60 * (((b - r) / delta) + 2);
            } else if (b == max) {
                HSV[0] = 60 * (((r - g) / delta) + 4);
            } else {
                HSV[0] = 0;
            }
            if (max == 0) {
                HSV[1] = 0;
            } else {
                HSV[1] = delta / max;
            }
            HSV[2] = max;
        }

        public HSBColor(double H, double S, double V) {
            HSV[0] = H;
            HSV[1] = S;
            HSV[2] = V;
            double C = V * S;
            double X = C * (1 - Math.abs(((H / 60) % 2) - 1));
            double m = V - C;
            double r;
            double g;
            double b;
            if (H < 60) {
                r = C;
                g = X;
                b = 0;
            } else if (H < 120) {
                r = X;
                g = C;
                b = 0;
            } else if (H < 180) {
                r = 0;
                g = C;
                b = X;
            } else if (H < 240) {
                r = 0;
                g = X;
                b = C;
            } else if (H < 300) {
                r = X;
                g = 0;
                b = C;
            } else {
                r = C;
                g = 0;
                b = X;
            }
            RGB[0] = (int) ((r + m) * 255);
            RGB[1] = (int) ((g + m) * 255);
            RGB[2] = (int) ((b + m) * 255);
            RGBV = (256 * 256) * RGB[0] + 256 * RGB[1] + RGB[2];
        }

        @Override
        public String toString() {
            return "HSV: " + this.HSV[0] + " " + this.HSV[1] + " " + this.HSV[2] + "\n" +
                    "RGB: " + this.RGB[0] + " " + this.RGB[1] + " " + this.RGB[2];
        }
    }
}