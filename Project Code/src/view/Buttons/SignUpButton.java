package view.Buttons;

import controller.Buttons.SignUpButtonController;

import javax.swing.*;
public class SignUpButton extends JButton {


    public SignUpButton(JFormattedTextField[] fields) {
        setText("Sign Up");
        new SignUpButtonController(this, fields);
    }

}
