package ui;

import apixu.WeatherForecast;
import location.LocationObject;

import javax.swing.*;
import java.awt.*;

/**
 * Store the relevant data for the recent locations to be displayed on the locationWindow
 */
public class RecentsRow {

    //Frequency: The number of times this location has been used recently
    private int mFrequency;
    private String mPlaceName;
    private int mIcon;
    private double mTemp;

    private JPanel mRowPanel;


    /**
     * Set the fields and get the weather data for the given location
     *
     * @param lo the location object for this recent location
     */
    public RecentsRow(LocationObject lo) {

        mPlaceName = lo.getCity();
        mFrequency = 0;

        WeatherForecast weatherForecast = new WeatherForecast(lo);

        mTemp = weatherForecast.getWeather().getTemp();
    }


    /**
     * Increment the frequency field
     */
    public void incrementFrequency() {

        mFrequency++;
    }


    public int getmFrequency() {

        return mFrequency;
    }


    /**
     * Create a new panel to store the recent location's data. This sets up the weather data in the correct
     * display order
     * @return JPanel: the panel which contains the weather data in a GridLayout
     */
    public JPanel getPanel() {

        mRowPanel = new JPanel(new GridLayout(1, 0));

        // Make labels with data created on object creation
        JLabel labelPlace = new JLabel(mPlaceName);
        JLabel labelIcon = new JLabel("" + mIcon);
        JLabel labelTemp = new JLabel(mTemp + " °C");

        // Ensure the label text is centered
        labelPlace.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);

        // Add them to the pane
        mRowPanel.add(labelPlace);
        mRowPanel.add(labelIcon);
        mRowPanel.add(labelTemp);

        // Same colour as main background
        mRowPanel.setBackground(new Color(138, 192, 239));

        return mRowPanel;
    }
}
