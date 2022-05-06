package view.Buttons;

import controller.Buttons.LogInButtonController;
import controller.Buttons.SignUpButtonController;

import javax.swing.*;
public class LogInButton extends JButton {


    public LogInButton(JFormattedTextField[] fields) {
        setText("Log In");
        new LogInButtonController(this, fields);
    }

}
