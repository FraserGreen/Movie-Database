package controller.Buttons;

import model.DBDist;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SignUpButtonController implements ActionListener {

    JFormattedTextField[] fields;

    public SignUpButtonController(SignUpButton button, JFormattedTextField[] fields) {
        this.fields = fields;
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String[] inputs = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            inputs[i] = fields[i].getText();
        }
        DBDist.db.signUp(inputs);
    }
}
