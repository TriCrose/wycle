package ui;

import apixu.WeatherDay;
import apixu.WeatherForecast;
import apixu.WeatherHour;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 394;
    private static final int HEIGHT = 700;
    private static final Color backColour = new Color(138, 192, 239);
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static WeatherForecast mWeatherForecast = new WeatherForecast();
    private JPanel mDayPanel;
    private JPanel mIconsPanel;
    private JPanel mRainWindPanel;
    private JPanel mDetailedPanel;
    private int mDayIndex = 0; // change when switching days
    private String mCurrentDay;


    public MainWindow() {

        super("Wycle");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backColour);

        mDayPanel = addPanel(new JPanel(new BorderLayout()), 0, 0.04);
        drawDay();
        mIconsPanel = addPanel(new JPanel(new GridLayout(1, 2)), 1, 0.1);
        drawIcons();
        mRainWindPanel = addPanel(new JPanel(new GridLayout(1, 3)), 2, 0.1);
        drawRainWind();
        mDetailedPanel = addPanel(new JPanel(new GridLayout(0, 1)), 3, 0.5);
        drawDetailed();

        setVisible(true);
    }


    public static void main(String args[]) {

        new MainWindow();
    }


    public static WeatherForecast getmWeatherForecast() {

        return mWeatherForecast;
    }


    public void setmDayIndex(int mDayIndex) {

        this.mDayIndex = mDayIndex;
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


    private void drawDay() {

        if (mDayIndex != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, mDayIndex);
            mCurrentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        } else {
            mCurrentDay = "Today";
        }

        JLabel labelDay = new JLabel(mCurrentDay);
        labelDay.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
        labelDay.setHorizontalAlignment(JLabel.CENTER);
        mDayPanel.add(labelDay);

        JButton buttonLocation = new JButton();
        mDayPanel.add(buttonLocation, BorderLayout.LINE_END);

        buttonLocation.addActionListener(e -> {
            // Location button pressed
            System.out.println("Location button clicked");
        });
    }


    private void drawIcons() {

        JLabel labelBikeCoefficient = new JLabel("bike coefficient");
        JLabel labelWeatherIcon = new JLabel("weather icon");

        labelBikeCoefficient.setHorizontalAlignment(JLabel.CENTER);
        labelWeatherIcon.setHorizontalAlignment(JLabel.CENTER);

        mIconsPanel.add(labelBikeCoefficient);
        mIconsPanel.add(labelWeatherIcon);
    }


    private void drawRainWind() {

        double rain;
        double temp;
        double wind;

        if (mDayIndex == 0) {

            WeatherHour currentWeather = mWeatherForecast.getWeather();

            rain = currentWeather.getmRain();
            temp = currentWeather.getmTemp();
            wind = currentWeather.getmWind();
        } else {
            WeatherDay currentWeather = mWeatherForecast.getDaySummary(mDayIndex);

            // TODO: Is it possible to have this do the average rain?
            rain = currentWeather.getTotalRain();
            temp = currentWeather.getAvgTemp();
            wind = currentWeather.getMaxWind();
        }

        JLabel labelRain = new JLabel(rain + " mm");
        JLabel labelTemp = new JLabel(temp + " °C");
        JLabel labelWind = new JLabel(wind + " mph");

        labelRain.setHorizontalAlignment(JLabel.CENTER);
        labelRain.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelWind.setHorizontalAlignment(JLabel.CENTER);
        labelWind.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));

        mRainWindPanel.add(labelRain);
        mRainWindPanel.add(labelTemp);
        mRainWindPanel.add(labelWind);
    }


    private void drawDetailed() {

        ArrayList<WeatherHour> weatherHour = mWeatherForecast.getWeather(mDayIndex);
        ArrayList<WeatherHour> tomorrowWeatherHour = null;

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        for (int i = 0; i < 10; i++) {

            if (i + hour < weatherHour.size()) {
                DetailedRow dr = new DetailedRow();
                mDetailedPanel.add(dr.getPanel(weatherHour.get(i + hour)));
            } else if (i + hour == weatherHour.size()) {

                tomorrowWeatherHour = mWeatherForecast.getWeather(1);

                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground(new Color(138, 192, 239));

                JLabel label = new JLabel("Tomorrow");
                label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.add(label);
                mDetailedPanel.add(panel);
            } else {
                DetailedRow dr = new DetailedRow();
                mDetailedPanel.add(dr.getPanel(tomorrowWeatherHour.get((i - 1 + hour) % 24)));
            }
        }
    }
}