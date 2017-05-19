package ui;

import location.LocationObject;
import location.LocationStore;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main window for the Location search and recent locations. This class pulls the required weather data in and
 * shows the overview weather for the recent locations.
 */
public class LocationWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    // Panels for the windows
    private JPanel mSearchBarPanel;
    private JPanel mSuggestionsPanel;
    private JPanel mRecentLocationsPanel;

    // List model for the JList for mSuggestionsPanel
    private DefaultListModel mSuggestionListModel;

    // List of recent locations and their weather
    private ArrayList<RecentsRow> mRecentsList;


    /**
     * Create a new LocationWindow.
     *
     * This sets the window to have the searchbar, suggestions list and recentLocations
     */
    public LocationWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        mSearchBarPanel = addPanel(new JPanel(new BorderLayout()), 0, 0.01);
        drawSearchBar();

        mSuggestionsPanel = addPanel(new JPanel(new BorderLayout()), 1, 0.01);
        drawSuggestions();

        mRecentLocationsPanel = addPanel(new JPanel(new GridLayout(0, 1, 0, 10)), 2, 0.75);
        mRecentLocationsPanel.setBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory
                        .createEmptyBorder(5, 5, 5, 5)));
        drawRecentLocations();

        setVisible(true);
    }


    public static void main(String[] args) {

        new LocationWindow();
    }


    /**
     * @param panel  created panel which needs to be added to the main frame
     * @param gridY  The position in the layout for this panel to go
     * @param weight The weight for the GridBagLayout
     * @return The panel which was given
     */
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


    /**
     * Draw the searchbar, adding required listeners
     */
    private void drawSearchBar() {

        // Make the new textfield object
        JTextField textField = new JTextField(10);

        // Same colour as main background
        textField.setBackground(new Color(138, 192, 239));

        // Add a listener for when a character is typed or removed in the textfield
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


            /**
             * Get the current data in the textfield, calls a search with this text and updates the suggestion list
             * with the result of the search
             */
            private void onUpdate() {

                String searchData = textField.getText();
                ArrayList<LocationObject> suggestions = LocationStore.search(searchData);
                updateSuggestions(suggestions);
            }
        });

        mSearchBarPanel.add(textField);
    }


    /**
     * Update the current suggestions to show the matching cities
     * @param searchResult The list of locations which match the current text in the textfield
     */
    private void updateSuggestions(List<LocationObject> searchResult) {

        // Ensure the model has no elements left
        mSuggestionListModel.removeAllElements();
        // Repopulate the model
        if (searchResult.size() > 0) {
            for (LocationObject s : searchResult) {
                mSuggestionListModel.addElement(s.getCity());
            }
        }
    }


    /**
     * Draw the suggestions list
     */
    private void drawSuggestions() {

        mSuggestionListModel = new DefaultListModel();

        // Get cities and format them before adding them to the listModel
        ArrayList<LocationObject> cities = LocationStore.getCities();
        for (LocationObject lo : cities) {
            mSuggestionListModel.addElement(lo.getCity() + ", " + lo.getCountry());
        }

        // Make a new list from the model above
        JList list = new JList(mSuggestionListModel);

        // Styling background
        list.setBackground(new Color(138, 192, 239));
        // Can only click on one item at a time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // When an item is clicked and the cursor is not adjusting this value (ensures only act on click release)
        // the action is carried out
        // TODO: change action to switch to switch to selected weather page
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // List item selected
                System.out.println("clicked " + list.getSelectedValue());
            }
        });

        // Scrollable list so that all results can be found
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        scrollPane.setBackground(new Color(138, 192, 239));

        mSuggestionsPanel.add(scrollPane);
    }


    /**
     * Draw the panels for the recent locations
     */
    private void drawRecentLocations() {

        // Get the cities
        mRecentsList = new ArrayList<>();
        ArrayList<LocationObject> cities = LocationStore.getCities();

        // Draw the recent panels with their locations
        for (int i = 0; i < 6; i++) {
            // Select random city as we currently don't use the app enough to generate these properly
            RecentsRow rr = new RecentsRow(cities.get((new Random()).nextInt(cities.size())));
            mRecentsList.add(rr);

            JPanel panel = rr.getPanel();
            mRecentLocationsPanel.add(panel);

            panel.setBackground(new Color(160, 220, 255));
        }
    }
}
