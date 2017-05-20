package ui;

import apixu.WeatherForecast;
import location.LocationObject;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Store the relevant data for the recent locations to be displayed on the locationWindow
 */
public class RecentsRow implements Serializable {

    //Frequency: The number of times this location has been used recently
    private int mFrequency;
    private String mPlaceName;
    private int mIcon;
    private double mTemp;

    private JPanel mRowPanel;

    private LocationObject mLocationObject;


    /**
     * Set the fields and get the weather data for the given location
     *
     * @param lo the location object for this recent location
     */
    public RecentsRow(LocationObject lo) {

        mLocationObject = lo;
        mPlaceName = lo.getCity();
        mFrequency = 1;
    }


    @Override
    public String toString() {

        return "RecentsRow{" + "mFrequency=" + mFrequency + ", mPlaceName='" + mPlaceName + '\'' + ", mIcon=" + mIcon
                + ", mTemp=" + mTemp + ", mRowPanel=" + mRowPanel + ", mLocationObject=" + mLocationObject + '}';
    }


    public void updateWeather() {

        WeatherForecast weatherForecast = new WeatherForecast(mLocationObject);
        mTemp = weatherForecast.getWeather().getmTemp();
        mIcon = 0;
    }


    /**
     * Increment the frequency field
     */
    public void incrementFrequency() {

        mFrequency++;
    }


    public LocationObject getmLocationObject() {

        return mLocationObject;
    }


    public int getmFrequency() {

        return mFrequency;
    }


    /**
     * Create a new panel to store the recent location's data. This sets up the weather data in the correct
     * display order
     *
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

        mRowPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                System.out.println("mouse event here");
                System.out.println("change to new screen with this location");
                System.out.println(mLocationObject.getCity());
            }


            @Override
            public void mouseEntered(MouseEvent e) {

                mRowPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }


            @Override
            public void mouseExited(MouseEvent e) {

                mRowPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return mRowPanel;
    }
}
