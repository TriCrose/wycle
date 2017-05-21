package ui;

import apixu.WeatherForecast;
import location.LocationObject;
import location.LocationStore;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The main window for the Location search and recent locations. This class pulls the required weather data in and
 * shows the overview weather for the recent locations.
 */
public class LocationWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private static WeatherForecast mWeatherForecast;

    // Panels for the windows
    private JPanel mSearchBarPanel;
    private JPanel mSuggestionsPanel;
    private JPanel mRecentLocationsPanel;

    // List model for the JList for mSuggestionsPanel
    private DefaultListModel<LocationObject> mSuggestionListModel;

    // List of recent locations and their weather
    private ArrayList<RecentsRow> mRecentsList;
    // File location for location frequencies
    private String mFrequentsPath = "data/location_frequencies";


    /**
     * Create a new LocationWindow.
     * <p>
     * This sets the window to have the searchbar, suggestions list and recentLocations
     */
    public LocationWindow() {

        super("Wycle");

        mWeatherForecast = MainWindow.getmWeatherForecast();

        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        mSearchBarPanel = addPanel(new JPanel(new BorderLayout()), 0, 0.01);
        mSearchBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        drawSearchBar();

        mSuggestionsPanel = addPanel(new JPanel(new BorderLayout()), 1, 0.24);
        mSuggestionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        drawSuggestions();

        mRecentLocationsPanel = addPanel(new JPanel(new GridLayout(0, 1, 0, 10)), 2, 0.75);
        mRecentLocationsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        drawRecentLocations();

        Runtime.getRuntime().addShutdownHook(new Thread(this::setRecentLocations, "Shutdown-thread"));

        setVisible(true);
    }


    public static void main(String[] args) {

        new LocationWindow();
    }


    private void setBackgroundImage() {

        boolean isDay = mWeatherForecast.getWeather().getIsDay() == 1;

        String path = "art/use_these/backgrounds/";
        if (isDay) path += "background_day[bg].png";
        else path += "background_night[bg].png";

        try {
            Image backgroundImage = ImageIO.read(new File(path));
            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            this.setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param panel  created panel which needs to be added to the main frame
     * @param gridY  The position in the layout for this panel to go
     * @param weight The weight for the GridBagLayout
     * @return The panel which was given
     */
    private JPanel addPanel(JPanel panel, int gridY, double weight) {

        panel.setOpaque(false);

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

        Color searchBarBackground = new Color(186, 215, 240);

        ImageIcon icon = new ImageIcon("art/use_these/other_icons/magnifying_glass.png"); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back

        JLabel searchIcon = new JLabel(icon);
        searchIcon.setOpaque(true);
        searchIcon.setBackground(searchBarBackground);

        mSearchBarPanel.add(searchIcon, BorderLayout.LINE_START);

        // Make the new textfield object
        JTextField textField = new JTextField(10);
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD, textField.getFont().getSize()));
        textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        // Same colour as main background
        textField.setBackground(searchBarBackground);

        // Add a listener for when a character is typed or removed in the textfield
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {onUpdate();}


            @Override
            public void removeUpdate(DocumentEvent e) {onUpdate();}


            @Override
            public void changedUpdate(DocumentEvent e) {onUpdate();}


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

        mSearchBarPanel.add(textField, BorderLayout.CENTER);
    }


    /**
     * Update the current suggestions to show the matching cities
     *
     * @param searchResult The list of locations which match the current text in the textfield
     */
    private void updateSuggestions(List<LocationObject> searchResult) {

        // Ensure the model has no elements left
        mSuggestionListModel.removeAllElements();
        // Repopulate the model
        if (searchResult.size() > 0) {
            for (LocationObject s : searchResult) {
                mSuggestionListModel.addElement(s);
            }
        }
    }


    /**
     * Draw the suggestions list
     */
    private void drawSuggestions() {

        mSuggestionListModel = new DefaultListModel<>();

        // Get cities and format them before adding them to the listModel
        ArrayList<LocationObject> cities = LocationStore.getCities();
        for (LocationObject lo : cities) {
            mSuggestionListModel.addElement(lo);
        }

        // Make a new list from the model above
        JList<LocationObject> list = new JList<>(mSuggestionListModel);
        list.setCellRenderer(new CustomListCellRenderer());
        list.setOpaque(false);

        // Can only click on one item at a time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // When an item is clicked and the cursor is not adjusting this value (ensures only act on click release)
        // the action is carried out
        // TODO: change action to switch to selected weather page
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // List item selected
                System.out.println("clicked " + list.getSelectedValue());

                incrementFrequency(list.getSelectedValue());
            }
        });

        list.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Scrollable list so that all results can be found
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {

                JButton button = super.createDecreaseButton(orientation);

                button.setBorder(BorderFactory.createEmptyBorder());

                return button;
            }


            @Override
            protected JButton createIncreaseButton(int orientation) {

                JButton button = super.createIncreaseButton(orientation);

                button.setBorder(BorderFactory.createEmptyBorder());

                return button;
            }
        });
        mSuggestionsPanel.add(scrollPane);
    }


    /**
     * Draw the panels for the recent locations
     */
    private void drawRecentLocations() {

        // Get the cities

        getRecentLocations();

        if (mRecentsList == null) {
            // Need to auto generate some random cities

            ArrayList<LocationObject> cities = LocationStore.getCities();
            mRecentsList = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                RecentsRow rr = new RecentsRow(cities.get((new Random()).nextInt(cities.size())));
                mRecentsList.add(rr);

                rr.updateWeather();

                JPanel panel = rr.getPanel();
                mRecentLocationsPanel.add(panel);
            }
        } else {

            // Draw the recent panels with their locations
            for (int i = 0; i < 6; i++) {
                mRecentsList.get(i).updateWeather();
                JPanel panel = mRecentsList.get(i).getPanel();
                mRecentLocationsPanel.add(panel);
            }
        }
    }


    private void incrementFrequency(LocationObject lo) {

        boolean updated = false;

        for (RecentsRow rr : mRecentsList) {
            // intended to test by reference
            if (rr.getmLocationObject() == lo) {
                updated = true;

                rr.incrementFrequency();

                System.out.println(rr.getmFrequency());
            }
        }

        if (!updated) {
            RecentsRow rr = new RecentsRow(lo);
            mRecentsList.add(rr);

            System.out.println(rr.getmFrequency());
        }

        mRecentsList.forEach(r -> {
            System.out.println(r);
            System.out.println();
        });
    }


    private void getRecentLocations() {
        // Read in current frequency data from file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mFrequentsPath))) {

            mRecentsList = (ArrayList<RecentsRow>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("The file to output to could not be found");
            System.out.println("A new file will be created");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void setRecentLocations() {

        mRecentsList.sort((o1, o2) -> o2.getmFrequency() - o1.getmFrequency());

        // Write the current data out to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mFrequentsPath))) {

            oos.writeObject(mRecentsList);
        } catch (FileNotFoundException e) {
            System.out.println("The file to output to could not be found");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class CustomListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            if (index % 2 == 0) setBackground(new Color(230, 230, 230, 200));
            else setBackground(new Color(200, 200, 200, 200));

            if (isSelected) {
                setBackground(new Color(170, 170, 170, 200));
            }

            try {
                String cityName = (String) value.getClass().getDeclaredMethod("getCity", null).invoke(value);
                String countryName = (String) value.getClass().getDeclaredMethod("getCountry", null).invoke(value);
                label.setText(cityName + ", " + countryName);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return label;
        }
    }
}
