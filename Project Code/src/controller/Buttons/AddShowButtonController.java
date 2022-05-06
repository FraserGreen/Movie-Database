package controller.Buttons;

import model.DBDist;
import view.Buttons.AddShowButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AddShowButtonController implements ActionListener {

    JFormattedTextField[] fields;

    public AddShowButtonController(AddShowButton button, JFormattedTextField[] fields) {
        this.fields = fields;
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] inputs = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            inputs[i] = fields[i].getText();
        }
        DBDist.db.insertInto("show", inputs);
        DBDist.db.printResultSet(DBDist.db.inquire("select * from `show`"));
    }
}
