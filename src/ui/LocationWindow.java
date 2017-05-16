package ui;

import location.LocationStore;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LocationWindow extends JFrame {

    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private JPanel searchBar;
    private JPanel suggestions;
    private JPanel recentLocations;

    private DefaultListModel suggestionListModel;

    private ArrayList<RecentsRow> recentsList;


    public LocationWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        searchBar = addPanel(new JPanel(), 0, 0.01);
        drawSearchBar();

        suggestions = addPanel(new JPanel(), 1, 0.01);
        drawSuggestions();

        recentLocations = addPanel(new JPanel(new GridLayout(0, 1)), 2, 0.75);
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

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

                onUpdate();
            }


            @Override
            public void removeUpdate(DocumentEvent e) {

                onUpdate();
            }


            @Override
            public void changedUpdate(DocumentEvent e) {

                onUpdate();
            }


            private void onUpdate() {

                String searchData = textField.getText();
                ArrayList<String> suggestions = LocationStore.search(searchData);
                updateSuggestions(suggestions);
            }
        });

        searchBar.add(textField);
    }


    private void updateSuggestions(List<String> searchResult) {

        suggestionListModel.removeAllElements();
        if (searchResult.size() > 0) {
            for (String s : searchResult) {
                suggestionListModel.addElement(s);
            }
        }
    }


    private void drawSuggestions() {

        suggestionListModel = new DefaultListModel();

        Object[] cities = LocationStore.getCities().toArray();
        for (Object o : cities) {
            suggestionListModel.addElement(o);
        }

        JList list = new JList(suggestionListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(list);

        suggestions.add(scrollPane);
    }


    private void drawRecentLocations() {

        recentsList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            RecentsRow rr = new RecentsRow("place");
            recentsList.add(rr);
            recentLocations.add(rr.getPanel());
        }
    }
}
