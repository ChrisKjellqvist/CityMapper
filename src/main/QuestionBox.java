package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by chris on 12/5/15.
 */
public class QuestionBox extends JFrame {
    JButton submit;
    JTextField answer;
    JLabel text;

    public QuestionBox(String message, boolean numericAnswer, int minValue, int maxValue) {
        //frame
        JFrame frame = new JFrame("QuestionBox");
        JPanel panel = new JPanel();
        frame.setPreferredSize(new Dimension(600, 160));
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
        frame.add(panel);
        frame.pack();
    }

    public String getText() {
        return answer.getText();
    }
}
