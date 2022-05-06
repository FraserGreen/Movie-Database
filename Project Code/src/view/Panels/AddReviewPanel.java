package view.Panels;

import view.Buttons.AddReviewButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.*;
public class AddReviewPanel extends JPanel {

    AddReviewPanel() {
        String[] columnNames = {"review_id", "show_id","user_id", "rating", "review", "date"};
        setLayout(new GridLayout(1, columnNames.length));
        JFormattedTextField[] fields = new JFormattedTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            fields[i] = new JFormattedTextField(columnNames[i]);
            add(fields[i]);
        }
        add(new AddReviewButton(fields), BorderLayout.EAST);
    }
}
