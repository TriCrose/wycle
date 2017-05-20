package ui;

import apixu.WeatherHour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Store method for creating panels for the detailed view on the MainWindow
 */
public class DetailedRow {

    private JPanel mRowPanel;


    public DetailedRow() {

    }


    /**
     * Make a panel with the data from the current hour and return it
     *
     * @param weatherHour the current weather data for this certain panel to use
     * @return panel with weather data and time added
     */
    public JPanel getPanel(WeatherHour weatherHour) {

        mRowPanel = new JPanel(new GridLayout(1, 0));

        // Set the labels with the relevant weather data
        JLabel labelTime = new JLabel(weatherHour.getTime());
        JLabel labelrain = new JLabel(weatherHour.getRain() + " mm");
        JLabel labeltemp = new JLabel(weatherHour.getTemp() + " °C");
        JLabel labelwind = new JLabel(weatherHour.getWind() + " mph");
        //TODO resize icons
        JLabel labelicon = new JLabel(weatherHour.getIcon());

        // Set the text to be centered
        labelTime.setHorizontalAlignment(JLabel.CENTER);
        labelrain.setHorizontalAlignment(JLabel.CENTER);
        labeltemp.setHorizontalAlignment(JLabel.CENTER);
        labelwind.setHorizontalAlignment(JLabel.CENTER);
        labelicon.setHorizontalAlignment(JLabel.CENTER);


        // Add the labels to the panel
        mRowPanel.add(labelTime);
        mRowPanel.add(labelrain);
        mRowPanel.add(labeltemp);
        mRowPanel.add(labelwind);
        mRowPanel.add(labelicon);

        mRowPanel.setBackground(new Color(138, 192, 239));

        return mRowPanel;
    }
}
