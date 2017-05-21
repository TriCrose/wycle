package ui;

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

public class WeekWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static GridBagConstraints constraints = new GridBagConstraints();
    private static WeatherForecast mWeatherForecast;
    private JPanel mHeaderPanel;
    private JPanel mDayIconsPanel;


    public WeekWindow() {

        super("Wycle");

        mWeatherForecast = MainWindow.getmWeatherForecast();

        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        mHeaderPanel = addPanel_Header(0, 0.04);
        JLabel labelLoc = new JLabel(mWeatherForecast.getLocation());
        labelLoc.setFont(new Font(labelLoc.getFont().getName(), Font.PLAIN, 24));
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
        panel.setBackground(new Color(255, 255, 255, 100));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                // change to new screen with this location
                System.out.println("clicked recent " + this.toString());
            }
        });

        JLabel labelDay = new JLabel(currentDay);
        labelDay.setFont(new Font(labelDay.getFont().getName(), Font.BOLD, 14));
        panel.add(labelDay);

        labelDay.setHorizontalAlignment(JLabel.CENTER);

        double temp;
        ImageIcon icon;
        if (dayIndex != 0) {
            WeatherDay day = mWeatherForecast.getDaySummary(dayIndex);
            temp = day.getAvgTemp();
            icon = day.getIcon(100, 100);
        } else {
            WeatherHour hour = mWeatherForecast.getWeather();
            temp = hour.getTemp();
            icon = hour.getIcon(100, 100);
        }

        //TODO maybe show min/max temp for each day?
        JLabel labelTemp = new JLabel(temp + " °C");
        JLabel labelIcon = new JLabel(icon);

        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelIcon.setHorizontalAlignment(JLabel.CENTER);

        panel.add(labelTemp);
        panel.add(labelIcon);

        return panel;
    }


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


    private void setBackgroundImage() {

        boolean isDay = mWeatherForecast.getWeather().getIsDay() == 1;

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