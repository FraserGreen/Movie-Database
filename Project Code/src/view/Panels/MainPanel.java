package view.Panels;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(360, 50));
        setLayout(new FlowLayout());
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        add(new SignUpPanel());
        add(new LogInPanel());
        add(new AddShowPanel());
        add(new AddReviewPanel());
    }
}
