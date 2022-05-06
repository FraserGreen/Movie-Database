package view.Buttons;

import controller.Buttons.AddShowButtonController;
import controller.Buttons.SignUpButtonController;

import javax.swing.*;
public class AddShowButton extends JButton {


    public AddShowButton(JFormattedTextField[] fields) {
        setText("Add Show");
        new AddShowButtonController(this, fields);
    }

}
