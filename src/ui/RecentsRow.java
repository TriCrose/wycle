package ui;

import apixu.WeatherForecast;
import app.AppWindow;
import location.LocationObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Store the relevant data for the recent locations to be displayed on the locationWindow
 */
public class RecentsRow implements Serializable {

    //Frequency: The number of times this location has been used recently
    private int mFrequency;
    private String mPlaceName;
    private ImageIcon mIcon;
    private double mTemp;

    private JPanel mRowPanel;

    private LocationObject mLocationObject;

    private AppWindow parent;


    /**
     * Set the fields and get the weather data for the given location
     *
     * @param lo the location object for this recent location
     */
    public RecentsRow(LocationObject lo, AppWindow parent) {

        mLocationObject = lo;
        mPlaceName = lo.getCity();
        mFrequency = 1;
        this.parent = parent;
    }


    @Override
    public String toString() {

        return "RecentsRow{" + "mFrequency=" + mFrequency + ", mPlaceName='" + mPlaceName + '\'' + ", mIcon=" + mIcon
                + ", mTemp=" + mTemp + ", mRowPanel=" + mRowPanel + ", mLocationObject=" + mLocationObject + '}';
    }


    /**
     * Update the weather data as has been stored in a file or is a new instance, set the according values
     */
    public void updateWeather() {

        WeatherForecast weatherForecast = new WeatherForecast(mLocationObject, false);
        mTemp = weatherForecast.getWeather().getTemp();
        mIcon = weatherForecast.getWeather().getIcon();
    }


    /**
     * Increment the frequency field
     */
    public void incrementFrequency() {

        mFrequency++;
    }


    /**
     * @return the locationObject contained in this object
     */
    public LocationObject getmLocationObject() {

        return mLocationObject;
    }


    /**
     * @return the frequency of how often this one has been selected
     */
    public int getmFrequency() {

        return mFrequency;
    }


    /**
     * Create a new panel to store the recent location's data. This sets up the weather data in the correct
     * display order
     *
     * @return JPanel: the panel which contains the weather data in a GridLayout
     */
    public JPanel getPanel(Color fontColor) {

        mRowPanel = new JPanel(new GridLayout(1, 0));

        // Make labels with data created on object creation
        JLabel labelPlace = new JLabel(mPlaceName);
        JLabel labelIcon = new JLabel(mIcon);
        JLabel labelTemp = new JLabel(mTemp + " °C");

        // Ensure the label text is centered
        labelPlace.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);

        // set the color of the text
        labelPlace.setForeground(fontColor);
        labelIcon.setForeground(fontColor);
        labelTemp.setForeground(fontColor);

        // Add them to the pane
        mRowPanel.add(labelPlace);
        mRowPanel.add(labelIcon);
        mRowPanel.add(labelTemp);

        mRowPanel.setBackground(new Color(255, 255, 255, 100));
        // change cursor for better UX
        mRowPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // mouse listener for click on this panel
        mRowPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                parent.goToMainPage(0, mLocationObject);
                System.out.println("clicked recent " + this.toString());
            }
        });

        return mRowPanel;
    }
}
