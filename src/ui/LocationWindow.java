package ui;

import apixu.WeatherForecast;
import location.LocationObject;
import location.LocationStore;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private static GridBagConstraints constraints = new GridBagConstraints();

    // store the weather forecast to reduce api calls
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
    // color for the font, changes depending isDay
    private Color fontColor;


    /**
     * Create a new LocationWindow.
     * <p>
     * This sets the window to have the searchbar, suggestions list and recentLocations
     */
    public LocationWindow() {

        super("Wycle");

        // get the latest weather
        mWeatherForecast = MainWindow.getmWeatherForecast();

        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set font colour depending on day or night
        if (mWeatherForecast.getWeather().getIsDay() == 1) {
            fontColor = Color.black;
        } else {
            fontColor = Color.white;
        }

        // make, add and draw the panels
        mSearchBarPanel = addPanel(new JPanel(new BorderLayout()), 0, 0.01);
        mSearchBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        drawSearchBar();

        mSuggestionsPanel = addPanel(new JPanel(new BorderLayout()), 1, 0.24);
        mSuggestionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        drawSuggestions();

        mRecentLocationsPanel = addPanel(new JPanel(new GridLayout(0, 1, 0, 10)), 2, 0.75);
        mRecentLocationsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        drawRecentLocations();

        // set a method to run on shutdown which saves the recents list
        Runtime.getRuntime().addShutdownHook(new Thread(this::setRecentLocations, "Shutdown-thread"));
        // show the window
        setVisible(true);
    }


    public static void main(String[] args) {

        new LocationWindow();
    }


    /**
     * Set the background to the appropriate image depending on whether it is daytime or nighttime
     */
    private void setBackgroundImage() {

        boolean isDay = mWeatherForecast.getWeather().getIsDay() == 1;

        // set path to image
        String path = "art/use_these/backgrounds/";
        if (isDay) path += "background_day[bg].png";
        else path += "background_night[bg].png";

        // load the image and set it to be the background
        try {
            Image backgroundImage = ImageIO.read(new File(path));
            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            setContentPane(background);
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

        // color for the searchBar
        Color searchBarBackground = new Color(255, 255, 255);

        // get and set the icon for the search bar
        ImageIcon icon = new ImageIcon("art/use_these/other_icons/magnifying_glass.png"); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back

        // add to panel and style
        JLabel searchIcon = new JLabel(icon);
        searchIcon.setOpaque(true);
        searchIcon.setBackground(searchBarBackground);

        mSearchBarPanel.add(searchIcon, BorderLayout.LINE_START);

        // Make the new textfield object and set style
        JTextField textField = new JTextField(10);
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD, textField.getFont().getSize()));
        textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
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
                if (list.getSelectedIndex() > -1) {
                    // List item selected
                    System.out.println("clicked " + list.getSelectedValue());

                    incrementFrequency(list.getSelectedValue());
                }
            }
        });
        // show hand for better UX
        list.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Scrollable list so that all results can be found
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        // hide ugly scrollbars
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

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
                RecentsRow rr = new RecentsRow(cities.get((new Random()).nextInt(cities.size())), null);
                mRecentsList.add(rr);

                rr.updateWeather();

                JPanel panel = rr.getPanel(fontColor);
                mRecentLocationsPanel.add(panel);
            }
        } else {

            // Draw the recent panels with their locations
            for (int i = 0; i < 6; i++) {
                mRecentsList.get(i).updateWeather();
                JPanel panel = mRecentsList.get(i).getPanel(fontColor);
                mRecentLocationsPanel.add(panel);
            }
        }
    }


    /**
     * Find the appropriate recent row and update the frequency, allowing for updating of the recent rows as users
     * select locations.
     *
     * @param lo locationObject which is used to find the corresponding RecentRow
     */
    private void incrementFrequency(LocationObject lo) {

        boolean updated = false;

        // try to find the object
        for (RecentsRow rr : mRecentsList) {
            // intended to test by reference
            if (rr.getmLocationObject() == lo) {
                updated = true;

                rr.incrementFrequency();
            }
        }
        // didn't find one so make a new one
        if (!updated) {
            RecentsRow rr = new RecentsRow(lo, null);
            mRecentsList.add(rr);
        }
    }


    /**
     * Read the recents list from the file, it is already sorted so no extra work is needed.
     */
    private void getRecentLocations() {
        // Read in current frequency data from file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mFrequentsPath))) {

            mRecentsList = (ArrayList<RecentsRow>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("The file to input from could not be found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sort the recents list before writing it to file using serialising
     */
    private void setRecentLocations() {

        // sort on frequencies
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


    /**
     * Custom renderer for the list containing the search suggestions.
     * This colours cells in alternating colours to more easily distinguish the rows
     */
    public class CustomListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // remove borders
            label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            // alternate colour
            if (index % 2 == 0) setBackground(new Color(230, 230, 230, 200));
            else setBackground(new Color(200, 200, 200, 200));

            // if selected show darker to indicate
            if (isSelected) {
                setBackground(new Color(170, 170, 170, 200));
            }

            // give the label the correct text using reflection
            try {
                String cityName = (String) value.getClass().getDeclaredMethod("getCity").invoke(value);
                String countryName = (String) value.getClass().getDeclaredMethod("getCountry").invoke(value);
                label.setText(cityName + ", " + countryName);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return label;
        }
    }
}
