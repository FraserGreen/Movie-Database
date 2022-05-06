package view.Buttons;

import controller.Buttons.AddReviewButtonController;
import controller.Buttons.AddShowButtonController;
import controller.Buttons.SignUpButtonController;

import javax.swing.*;
public class AddReviewButton extends JButton {


    public AddReviewButton(JFormattedTextField[] fields) {
        setText("Add Review");
        new AddReviewButtonController(this, fields) ;
    }

}
