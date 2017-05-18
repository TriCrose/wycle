package ui;

import apixu.WeatherHour;

import javax.swing.*;
import java.awt.*;

public class DetailedRow {

    private JPanel row;

    public DetailedRow() {

    }


    public JPanel getPanel(WeatherHour weatherHour) {

        row = new JPanel(new GridLayout(1, 0));

        JLabel labelTime = new JLabel(weatherHour.getmTime());
        JLabel labelrain = new JLabel(String.valueOf(weatherHour.getmRain()) + " mm");
        JLabel labeltemp = new JLabel(String.valueOf(weatherHour.getmTemp()) + "°C");
        JLabel labelwind = new JLabel(String.valueOf(weatherHour.getmWind()) + "mph");
        JLabel labelicon = new JLabel(weatherHour.getmCondition().getIcon());

        labelTime.setHorizontalAlignment(JLabel.CENTER);
        labelrain.setHorizontalAlignment(JLabel.CENTER);
        labeltemp.setHorizontalAlignment(JLabel.CENTER);
        labelwind.setHorizontalAlignment(JLabel.CENTER);
        labelicon.setHorizontalAlignment(JLabel.CENTER);

        row.add(labelTime);
        row.add(labelrain);
        row.add(labeltemp);
        row.add(labelwind);
        row.add(labelicon);

        row.setBackground(new Color(138, 192, 239));

        return row;
    }
}
