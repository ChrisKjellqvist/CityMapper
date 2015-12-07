package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by chris on 12/6/15.
 * Loads the image given by the Google Maps API with the given parameters.
 */
public class GoogleMap {
    String place;
    int width;
    int height;
    int zoom;

    public GoogleMap(String place, int width, int height, int zoom) throws Exception {
        for (int i = 0; i < place.length(); i++) {
            if (place.charAt(i) == ' ') {
                place = place.substring(0, i) + "_"
                        + place.substring(i + 1, place.length());
            }
        }
        this.place = place;
        this.width = width;
        this.height = height;
        this.zoom = zoom;

    }

    public BufferedImage getMap() {
        String url = "https://maps.googleapis.com/maps/api/staticmap?maptype=satellite&center=" + place + "&zoom="
                + zoom + "&scale=2&size=" + width + "x" + height + "&key=AIzaSyAG3MBBOC0LVqRE3ZOYiRqOvZ7DyTzoRzU";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try {
            URL u = new URL(url);
            InputStream stream = u.openStream();
            image = ImageIO.read(stream);
        } catch (Exception ex) {
            System.out.println("getMap() error in GoogleMap");
        }
        return image;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public void setZoom(int z) {
        this.zoom = z;
    }
}
