package view;

import view.Panels.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {


    public MainFrame() {
        setupUI();
    }

    private void setupUI() {
        setBounds(0, 0, 1000, 700); //dimensions of the window
        setLocationRelativeTo(null); //centres the window
        add(new MainPanel());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}