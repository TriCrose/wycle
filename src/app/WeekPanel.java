package app;

import apixu.WeatherDay;
import apixu.WeatherForecast;
import apixu.WeatherHour;
import ui.AppParams;
import ui.MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import apixu.WeatherDay;
import apixu.WeatherForecast;
import apixu.WeatherHour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class WeekPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private AppWindow parent;

    private static GridBagConstraints constraints = new GridBagConstraints();

    private static WeatherForecast mWeatherForecast;

    private JPanel mHeaderPanel;
    private JPanel mDayIconsPanel;

    private Color fontColor;


    public WeekPanel(AppWindow parent) {

        this.parent = parent;

        mWeatherForecast = AppWindow.getmWeatherForecast();

        // set the background image depending on isDay
        //setBackgroundImage();

        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        //setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set font colour depending on day or night
        if (mWeatherForecast.getWeather().getIsDay() == 1) {
            fontColor = Color.black;
        } else {
            fontColor = Color.white;
        }

        // make, add and style the panels
        mHeaderPanel = addPanel_Header(0, 0.04);
        JLabel labelLoc = new JLabel(mWeatherForecast.getLocation());
        labelLoc.setFont(new Font(labelLoc.getFont().getName(), Font.PLAIN, 24));
        labelLoc.setForeground(fontColor);
        labelLoc.setHorizontalAlignment(JLabel.CENTER);

        mHeaderPanel.add(labelLoc);

        mDayIconsPanel = addPanel_DayIcons(1, 1.0);
        for (int i = 0; i < 6; i++) {
            mDayIconsPanel.add(createDayIcon(i));
        }
        setVisible(true);
    }


    public static void main(String args[]) {

        new ui.WeekWindow();
    }


    /**
     * @param day to which the panel should correspond
     * @return the JPanel created with the day
     */
    private JPanel createDayIcon(int day) {

        return createPanelWithText(day);
    }


    /**
     * @param dayIndex offset of current day from 'today'
     * @return JPanel with the day and weather
     */
    private JPanel createPanelWithText(int dayIndex) {

        String currentDay;

        // Get the text for the given day
        if (dayIndex != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, dayIndex);
            currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()).toUpperCase();
        } else {
            currentDay = "NOW";
        }

        // make the panel
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBackground(new Color(255, 255, 255, 100));
        // show hand for better UX
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // when selected, on release go to new screen
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                // TODO: change to new screen with this location
                System.out.println("clicked recent " + this.toString());
            }
        });

        // make new label for the day text
        JLabel labelDay = new JLabel(currentDay);
        labelDay.setFont(new Font(labelDay.getFont().getName(), Font.BOLD, 16));
        labelDay.setForeground(fontColor);
        panel.add(labelDay);
        // center it
        labelDay.setHorizontalAlignment(JLabel.CENTER);

        String temp;
        ImageIcon icon;
        // get the min / max temp or average temp
        if (dayIndex != 0) {
            WeatherDay day = mWeatherForecast.getDaySummary(dayIndex);
            temp = Integer.toString((int) day.getMaxTemp()) + " °C     " + Integer
                    .toString((int) day.getMinTemp()) + " °C";
            icon = day.getIcon(100, 100);
        } else {
            WeatherHour hour = mWeatherForecast.getWeather();
            temp = Double.toString(hour.getTemp()) + " °C";
            icon = hour.getIcon(100, 100);
        }

        // set the labels
        JLabel labelTemp = new JLabel(temp);
        JLabel labelIcon = new JLabel(icon);

        // change text color
        labelTemp.setForeground(fontColor);
        labelIcon.setForeground(fontColor);

        // center text
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        // add them to panel
        panel.add(labelTemp);
        panel.add(labelIcon);

        return panel;
    }


    /**
     * Make a new panel for the location and add it to the frame
     *
     * @param gridY  position to put into
     * @param weight weight of the panel
     * @return The JPanel with new constraints and properties
     */
    private JPanel addPanel_Header(int gridY, double weight) {

        JPanel panel = new JPanel(new BorderLayout());
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
     * Make a new panel for the days and add it to the frame
     * @param gridY position to put into
     * @param weight weight of the panel
     * @return The JPanel with new constraints and properties
     */
    private JPanel addPanel_DayIcons(int gridY, double weight) {

        int spacing = 10;

        JPanel panel = new JPanel(new GridLayout(2, 3, spacing, spacing));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = gridY;
        constraints.weightx = 1.0;
        constraints.weighty = weight;

        add(panel, constraints);
        return panel;
    }


    /**
     * Set the background depending on the time of day
     */
    private void setBackgroundImage() {

        boolean isDay = mWeatherForecast.getWeather().getIsDay() == 1;

        String path = "art/use_these/backgrounds/";
        if (isDay) path += "background_day[bg].png";
        else path += "background_night[bg].png";

        try {
            Image backgroundImage = ImageIO.read(new File(path));
            JLabel background = new JLabel(new ImageIcon(backgroundImage));
            //this.setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
