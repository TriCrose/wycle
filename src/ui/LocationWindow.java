package ui;

import location.LocationStore;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LocationWindow extends JFrame {

    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private JPanel searchBar;
    private JPanel suggestions;
    private JPanel recentLocations;

    private ArrayList<JPanel> suggestionPanels = new ArrayList<>(5);


    public LocationWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        searchBar = addPanel(new JPanel(), 0, 0.1);
        drawSearchBar();

        suggestions = addPanel(new JPanel(new GridLayout(5, 1)), 0, 0.3);
        drawSuggestions();

        recentLocations = addPanel(new JPanel(new GridLayout(0, 1)), 2, 0.5);
        drawRecentLocations();

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

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String searchData = textField.getText();
                ArrayList<String> suggestions = LocationStore.search(searchData);
                updateSuggestions(suggestions);
            }
        });

        searchBar.add(textField);
    }


    private void updateSuggestions(ArrayList<String> suggestions) {

    }


    private void drawSuggestions() {

        JPanel grid = new JPanel(new GridLayout(0, 1));
        suggestions.add(grid);
    }


    private void drawRecentLocations() {

        JPanel grid = new JPanel(new GridLayout(0, 1));
        recentLocations.add(grid);
    }
}
