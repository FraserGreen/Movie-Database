package view.Panels;

import view.Buttons.AddShowButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.*;
public class AddShowPanel extends JPanel {

    AddShowPanel() {
        String[] columnNames = {"show_id", "show_title", "genre", "length", "is movie?", "is series?", "proco_id",
                                "year"};
        setLayout(new GridLayout(1, columnNames.length));



        JFormattedTextField[] fields = new JFormattedTextField[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            fields[i] = new JFormattedTextField(columnNames[i]);
            add(fields[i]);
        }
        add(new AddShowButton(fields), BorderLayout.EAST);
    }
}
