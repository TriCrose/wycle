package ui;

import apixu.WeatherForecast;
import apixu.WeatherHour;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();

    private JPanel day;
    private JPanel icons;
    private JPanel rainWind;
    private JPanel detailed;

    private String currentDay = "Today";// Please change accordingly
    private ArrayList<DetailedRow> detailedRows;

    private WeatherForecast weatherForecast = new WeatherForecast();


    public MainWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        day = addPanel(new JPanel(new BorderLayout()), 0, 0.04);
        drawDay();
        icons = addPanel(new JPanel(new GridLayout(1, 2)), 1, 0.1);
        drawIcons();
        rainWind = addPanel(new JPanel(new GridLayout(1, 3)), 2, 0.1);
        drawRainWind();
        detailed = addPanel(new JPanel(new GridLayout(0, 1)), 3, 0.5);
        drawDetailed();

        setVisible(true);
    }


    public static void main(String args[]) {
        new MainWindow();
    }


    private JPanel addPanel(JPanel panel, int gridY, double weight) {

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


    private void drawIcons() {

        JLabel labelBikeCoefficient = new JLabel("bike coefficient");
        JLabel labelWeatherIcon = new JLabel("weather icon");

        labelBikeCoefficient.setHorizontalAlignment(JLabel.CENTER);
        labelWeatherIcon.setHorizontalAlignment(JLabel.CENTER);

        icons.add(labelBikeCoefficient);
        icons.add(labelWeatherIcon);
    }


    private void drawDay() {

        JLabel labelDay = new JLabel(currentDay);
        labelDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        labelDay.setHorizontalAlignment(JLabel.CENTER);
        day.add(labelDay);
    }

    private void drawRainWind() {

        JLabel labelRain = new JLabel("Rain");
        JLabel labelTemp = new JLabel("temp here");
        JLabel labelWind = new JLabel("wind here");

        labelRain.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelWind.setHorizontalAlignment(JLabel.CENTER);

        rainWind.add(labelRain);
        rainWind.add(labelTemp);
        rainWind.add(labelWind);
    }


    private void drawDetailed() {

        detailedRows = new ArrayList<>();

        ArrayList<WeatherHour> weatherHour = weatherForecast.getWeather(0);

        Calendar currentCal = Calendar.getInstance();
        int hour = currentCal.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);

        for (int i = 0; i < 10 && i + hour < weatherHour.size(); i++) {
            DetailedRow dr = new DetailedRow();
            detailedRows.add(dr);
            detailed.add(dr.getPanel(weatherHour.get(i + hour)));
        }
    }
}