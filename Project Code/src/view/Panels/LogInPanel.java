package view.Panels;

import view.Buttons.LogInButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.*;
public class LogInPanel extends JPanel {

    LogInPanel() {
        String[] columnNames = {"username", "password"};
        setLayout(new GridLayout(1, columnNames.length));
        JFormattedTextField[] fields = new JFormattedTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            fields[i] = new JFormattedTextField(columnNames[i]);
            add(fields[i]);
        }
        add(new LogInButton(fields), BorderLayout.EAST);
    }
}
