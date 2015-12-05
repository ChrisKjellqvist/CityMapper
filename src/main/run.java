package main;

/**
 * Created by chris on 12/1/15.
 * This is the file run when you run the jar file.
 */
public class run {
    public static void main(String[] args) throws Exception {
        /*
        checks to make sure basic stuff like your settings and api key exist.
        If it's all there, then you're good to go.
         */
        if (!(common.fileExists("settings.txt"))) {
            common.display("couldn't find settings.");
            startupbox.run();
        } else if (!(common.fileExists("api_key.txt"))) {
            common.display("Make your api_key.txt pleaseaa.");
        } else {
            gatherData.run();
        }
    }


}
