package view.Panels;

import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.*;
public class SignUpPanel extends JPanel {

    SignUpPanel() {
        String[] columnNames = {"username", "password", "email", "country", "gender", "first name", "last name"};
        setLayout(new GridLayout(1, columnNames.length));
        JFormattedTextField[] fields = new JFormattedTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            fields[i] = new JFormattedTextField(columnNames[i]);
            add(fields[i]);
        }
        add(new SignUpButton(fields), BorderLayout.EAST);
    }
}
