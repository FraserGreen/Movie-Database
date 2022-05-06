package controller.Buttons;

import model.Account;
import model.DBDist;
import view.Buttons.LogInButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LogInButtonController implements ActionListener {

    JFormattedTextField[] fields;

    public LogInButtonController(LogInButton button, JFormattedTextField[] fields) {
        this.fields = fields;
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String[] inputs = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            inputs[i] = fields[i].getText();
        }
        DBDist.db.logIn(new Account(), inputs[0], inputs[1]);
    }
}
