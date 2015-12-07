package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by chris on 12/5/15.
 * Generates a box with a message, answer field, and submit button.
 * If you want it to be a number value, you can also give it a range.
 */
public class QuestionBox extends JFrame {
    static String ret;
    JButton submit;
    JTextField answer;
    JLabel text;
    int max;
    int min;

    public QuestionBox(String message, int minValue, int maxValue) {

        this.max = maxValue;
        this.min = minValue;

        //frame
        JPanel panel = new JPanel();
        this.setPreferredSize(new Dimension(600, 160));
        Border padding = BorderFactory.createEmptyBorder(2, 2, 2, 5);

        //Textbox asking a question
        text = new JLabel(message);
        text.setPreferredSize(new Dimension(300, 80));
        text.setBorder(padding);

        //Text field for answer
        answer = new JTextField();
        answer.setBorder(padding);
        answer.setPreferredSize(new Dimension(40, 30));

        //Submit button
        submit = new JButton("submit");
        submit.setBorder(padding);

        //Add everything to the frame
        panel.add(text);
        panel.add(answer);
        panel.add(submit);
        panel.setPreferredSize(new Dimension(600, 80));
        this.add(panel);
        this.pack();
    }

    public String getAnswer(boolean numericValue) {
        this.setVisible(true);
        this.submit.addActionListener(e -> {
            try {
                if (numericValue) {
                    int answer = Integer.parseInt(this.answer.getText());
                    if (answer >= min && answer <= this.max) {
                        this.dispose();
                        ret = Integer.toString(answer);
                    } else {
                        this.text.setText("Not within range.");
                    }
                } else {
                    ret = this.answer.getText();
                }
            } catch (Exception exc) {
                System.out.println("error?");
            }
        });
        while (ret == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return ret;
    }
}
