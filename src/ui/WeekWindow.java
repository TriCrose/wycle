package ui;

import apixu.WeatherForecast;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Locale;

public class WeekWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();
    WeatherForecast mWeatherForecast;
    private JPanel mHeaderPanel;
    private JPanel mDayIconsPanel;


    public WeekWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        mWeatherForecast = MainWindow.getmWeatherForecast();

        mHeaderPanel = addPanel_Header(0, 0.04);
        JLabel labelLoc = new JLabel(mWeatherForecast.getLocation());
        labelLoc.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        labelLoc.setHorizontalAlignment(JLabel.CENTER);
        mHeaderPanel.add(labelLoc);

        mDayIconsPanel = addPanel_DayIcons(1, 1.0);
        for (int i = 0; i < 6; i++) {
            //int x = i % 2;
            //int y = (i + 1) / 2;

            mDayIconsPanel.add(createDayIcon(i));
        }
        setVisible(true);
    }


    public static void main(String args[]) {

        new WeekWindow();
    }


    // this just for testing, because the days will have to be chosen dynamically
    private String dayName(int day) {

        switch (day) {
            case 0:
                return "NOW";
            case 1:
                return "MON";
            case 2:
                return "TUE";
            case 3:
                return "WED";
            case 4:
                return "THU";
            case 5:
                return "FRI";
            case 6:
                return "SAT";
            case 7:
                return "SUN";
            default:
                return "OOPS";
        }
    }


    private JPanel createDayIcon(int day) {

        return createPanelWithText(day);
    }


    private JPanel createPanelWithText(int dayIndex) {

        String currentDay;

        if (dayIndex != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, dayIndex);
            currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
                                 .toUpperCase();
        } else {
            currentDay = "NOW";
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(160, 220, 255));

        JLabel labelDay = new JLabel(currentDay);
        labelDay.setFont(new Font(labelDay.getFont().getName(), Font.BOLD, 14));
        panel.add(labelDay);

        labelDay.setHorizontalAlignment(JLabel.CENTER);

        double temp;

        if (dayIndex != 0) {
            temp = mWeatherForecast.getDaySummary(dayIndex).getAvgTemp();
        } else {
            temp = mWeatherForecast.getWeather().getmTemp();
        }

        JLabel labelTemp = new JLabel(temp + " °C");
        JLabel labelIcon = new JLabel("icon");

        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        panel.add(labelTemp);
        panel.add(labelIcon);

        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        return panel;
    }


    private JPanel addPanel_Header(int gridY, double weight) {

        JPanel panel = new JPanel(new BorderLayout());
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


    private JPanel addPanel_DayIcons(int gridY, double weight) {

        int spacing = 10;

        JPanel panel = new JPanel(new GridLayout(2, 3, spacing, spacing));
        panel.setBackground(backColour);
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        panel.setBorder(BorderFactory
                .createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(spacing, spacing,
                        spacing, spacing)));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.weightx = 1.0;
        constraints.weighty = weight;

        add(panel, constraints);
        return panel;
    }
}