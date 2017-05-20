package ui;

import apixu.WeatherDay;
import apixu.WeatherForecast;
import apixu.WeatherHour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Main class which holds the home screen, this includes: day; icons; rain and wind; detailed view;
 */
public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
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

        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        mDayPanel = addPanel(new JPanel(new BorderLayout()), 0, 0.04);
        mDayPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        drawDay();
        mIconsPanel = addPanel(new JPanel(new GridLayout(1, 2)), 1, 0.2);
        drawIcons();
        mRainWindPanel = addPanel(new JPanel(new GridLayout(1, 3)), 2, 0.2);
        mRainWindPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        drawRainWind();
        mDetailedPanel = addPanel(new JPanel(new GridLayout(0, 1)), 3, 0.5);
        mDetailedPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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


    /**
     * @param panel  panel to be used to be added
     * @param gridY  desired position of the panel
     * @param weight weight of the panel
     * @return The panel given
     */
    private JPanel addPanel(JPanel panel, int gridY, double weight) {

        panel.setOpaque(false);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.weightx = 1.0;
        constraints.weighty = weight;

        add(panel, constraints);
        return panel;
    }


    /**
     * Draw the day info, changes day automatically depending on the dayIndex (acts like offset)
     */
    private void drawDay() {

        // Set the text for the day to be the correct text
        if (mDayIndex != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, mDayIndex);
            mCurrentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        } else {
            mCurrentDay = "Now";
        }

        // Make a new label and style it
        JLabel labelDay = new JLabel(mCurrentDay);
        labelDay.setFont(new Font(labelDay.getFont().getName(), Font.PLAIN, 24));
        labelDay.setHorizontalAlignment(JLabel.CENTER);
        mDayPanel.add(labelDay, BorderLayout.LINE_START);

        JLabel labelLocation = new JLabel(mWeatherForecast.getLocation());
        labelLocation.setHorizontalAlignment(JLabel.CENTER);
        labelLocation.setFont(new Font(labelLocation.getFont().getName(), Font.PLAIN, 20));
        mDayPanel.add(labelLocation, BorderLayout.CENTER);
        // Button for going to the location screen
        JButton buttonLocation = new JButton();
        buttonLocation.setBackground(new Color(255, 0, 0));
        mDayPanel.add(buttonLocation, BorderLayout.LINE_END);

        buttonLocation.addActionListener(e -> {
            // Location button pressed
            System.out.println("Location button clicked");
        });
    }


    /**
     * Draw the icons for the main weather display, bike coefficient and symbol
     */
    private void drawIcons() {

        WeatherHour weatherForecast = new WeatherForecast().getWeather();

        ImageIcon icon = weatherForecast.getIcon(100, 100);

        // Set up the labels
        JLabel labelBikeCoefficient = new JLabel("bike coefficient");
        JLabel labelWeatherIcon = new JLabel(icon);

        // Center them
        labelBikeCoefficient.setHorizontalAlignment(JLabel.CENTER);
        labelWeatherIcon.setHorizontalAlignment(JLabel.CENTER);

        // Add them to the panel
        mIconsPanel.add(labelBikeCoefficient);
        mIconsPanel.add(labelWeatherIcon);
    }


    /**
     * Add the rain, wind and temperature to the main panel
     */
    private void drawRainWind() {

        double rain;
        double temp;
        double wind;

        // Get current weather for today or get average day weather for other days
        if (mDayIndex == 0) {

            WeatherHour currentWeather = mWeatherForecast.getWeather();

            rain = currentWeather.getRain();
            temp = currentWeather.getTemp();
            wind = currentWeather.getWind();
        } else {
            WeatherDay currentWeather = mWeatherForecast.getDaySummary(mDayIndex);

            // TODO: Is it possible to have this do the average rain?
            rain = currentWeather.getTotalRain();
            temp = currentWeather.getAvgTemp();
            wind = currentWeather.getMaxWind();
        }

        // Make the labels
        JLabel labelRain = new JLabel(rain + " mm");
        JLabel labelTemp = new JLabel(temp + " °C");
        JLabel labelWind = new JLabel(wind + " mph");

        // Style the labels
        labelRain.setHorizontalAlignment(JLabel.CENTER);
        labelRain.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelWind.setHorizontalAlignment(JLabel.CENTER);
        labelWind.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));

        // Add the labels
        mRainWindPanel.add(labelRain);
        mRainWindPanel.add(labelTemp);
        mRainWindPanel.add(labelWind);
    }


    /**
     * Detailed rows will be drawn for each hour of the current day
     */
    private void drawDetailed() {

        ArrayList<WeatherHour> weatherHour = mWeatherForecast.getWeather(mDayIndex);
        ArrayList<WeatherHour> tomorrowWeatherHour = null;

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        // draw the panels, conditional for whether we can fill the space with just today's weather or need to add
        // tomorrows
        for (int i = 0; i < 10; i++) {

            if (i + hour < weatherHour.size()) {
                DetailedRow dr = new DetailedRow();
                JPanel panel = dr.getPanel(weatherHour.get(i + hour));
                alternateColourPanel(panel, i);

                mDetailedPanel.add(panel);
            } else if (i + hour == weatherHour.size()) {

                tomorrowWeatherHour = mWeatherForecast.getWeather(1);

                JPanel panel = new JPanel(new BorderLayout());
                alternateColourPanel(panel, i);

                JLabel label = new JLabel("Tomorrow");
                label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.CENTER);
                panel.add(label);
                mDetailedPanel.add(panel);
            } else {
                DetailedRow dr = new DetailedRow();
                assert tomorrowWeatherHour != null;
                JPanel panel = dr.getPanel(tomorrowWeatherHour.get((i - 1 + hour) % 24));
                alternateColourPanel(panel, i);
                mDetailedPanel.add(panel);
            }
        }
    }


    private void alternateColourPanel(JPanel panel, int i) {

        if (i % 2 == 0) {
            panel.setBackground(new Color(255, 255, 255, 100));
        } else {
            panel.setBackground(new Color(195, 195, 195, 100));
        }
    }


    private void setBackgroundImage() {

        WeatherForecast weatherForecast = new WeatherForecast();
        boolean isDay = weatherForecast.getWeather().getIsDay() == 1;

        String path = "art/use_these/backgrounds/";
        if (isDay) path += "background_day[bg].png";
        else path += "background_night[bg].png";

        try {
            Image backgroundImage = ImageIO.read(new File(path));
            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            this.setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}