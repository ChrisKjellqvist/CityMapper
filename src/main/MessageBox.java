package main;

import javax.swing.*;

/**
 * Created by chris on 12/5/15.
 * Generates a box that contains a message.
 */
public class MessageBox extends JFrame {
    String message;
    JLabel messageLabel;

    public MessageBox(String message) {
        this.message = message;
        messageLabel = new JLabel(this.message);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));
        this.add(messageLabel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
