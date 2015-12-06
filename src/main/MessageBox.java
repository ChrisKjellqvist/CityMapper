package main;

import javax.swing.*;

/**
 * Created by chris on 12/5/15.
 */
public class MessageBox extends JFrame {
    String message;

    public MessageBox(String message) {
        this.message = message;
        JLabel messageLabel = new JLabel(this.message);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 5));
        this.add(messageLabel);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
