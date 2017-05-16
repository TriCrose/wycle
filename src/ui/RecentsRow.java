package ui;

import javax.swing.*;
import java.awt.*;

public class RecentsRow {

    private int frequency;
    private String placeName;
    // TODO: change below to either be the weather data or just the icon
    private int icon;
    private int temp;

    private JPanel row;


    public RecentsRow() {

        frequency = 0;
        // on creation maybe get up to date weather for the location
    }


    public RecentsRow(String place) {

        placeName = place;
        frequency = 0;
        //Gather weather data
    }


    public void incrementFrequency() {

        frequency++;
    }


    public int getFrequency() {

        return frequency;
    }


    public JPanel getPanel() {

        row = new JPanel(new GridLayout(1, 0));

        JLabel labelPlace = new JLabel(placeName);
        JLabel labelIcon = new JLabel("" + icon);
        JLabel labelTemp = new JLabel("" + temp);

        labelPlace.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);

        row.add(labelPlace);
        row.add(labelIcon);
        row.add(labelTemp);

        row.setBackground(new Color(138, 192, 239));

        return row;
    }
}
