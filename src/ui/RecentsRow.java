package ui;

import apixu.WeatherForecast;
import location.LocationObject;

import javax.swing.*;
import java.awt.*;

public class RecentsRow {

    private int mFrequency;
    private String mPlaceName;
    private int mIcon;
    private double mTemp;

    private JPanel mRowPanel;


    public RecentsRow(LocationObject lo) {

        mPlaceName = lo.getCity();
        mFrequency = 0;

        WeatherForecast weatherForecast = new WeatherForecast(lo);

        mTemp = weatherForecast.getWeather().getmTemp();
    }


    public void incrementFrequency() {

        mFrequency++;
    }


    public int getmFrequency() {

        return mFrequency;
    }


    public JPanel getPanel() {

        mRowPanel = new JPanel(new GridLayout(1, 0));

        JLabel labelPlace = new JLabel(mPlaceName);
        JLabel labelIcon = new JLabel("" + mIcon);
        JLabel labelTemp = new JLabel(mTemp + " °C");

        labelPlace.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);

        mRowPanel.add(labelPlace);
        mRowPanel.add(labelIcon);
        mRowPanel.add(labelTemp);

        mRowPanel.setBackground(new Color(138, 192, 239));

        return mRowPanel;
    }
}
