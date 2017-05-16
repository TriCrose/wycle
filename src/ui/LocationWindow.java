package ui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class LocationWindow extends JFrame {

    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private JPanel searchBar;
    private JPanel suggestions;
    private JPanel recentLocations;


    public LocationWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        searchBar = addPanel(new JPanel(), 0, 0.5);
        drawSearchBar();

        suggestions = addPanel(new JPanel(new GridLayout(0, 1)), 0, 0.5);

        recentLocations = addPanel(new JPanel(new GridLayout(0, 1)), 2, 0.5);

        setVisible(true);
    }


    public static void main(String[] args) {

        new LocationWindow();
    }


    private JPanel addPanel(JPanel panel, int gridY, double weight) {

        panel.setBackground(backColour);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.weightx = 1.0;
        constraints.weighty = weight;

        add(panel, constraints);
        return panel;
    }


    private void drawSearchBar() {

        JTextField textField = new JTextField(10);

        searchBar.add(textField);
    }
}
