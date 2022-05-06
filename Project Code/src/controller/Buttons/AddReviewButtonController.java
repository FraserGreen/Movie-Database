package controller.Buttons;

import model.DBDist;
import view.Buttons.AddReviewButton;
import view.Buttons.SignUpButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AddReviewButtonController implements ActionListener {

    JFormattedTextField[] fields;

    public AddReviewButtonController(AddReviewButton button, JFormattedTextField[] fields) {
        this.fields = fields;
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String[] inputs = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            inputs[i] = fields[i].getText();
        }
        DBDist.db.insertInto("user_review", inputs);
    }
}
