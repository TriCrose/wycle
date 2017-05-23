package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import apixu.WeatherDay;
import apixu.WeatherHour;
import ui.DetailedRow;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private AppWindow parent; 
	
	private static GridBagConstraints constraints = new GridBagConstraints();
	
    // store the panels in the frame
    private JPanel mDayPanel;
    private JPanel mIconsPanel;
    private JPanel mRainWindPanel;
    private JPanel mDetailedPanel;
	
	public MainPanel(AppWindow parent) {
		this.parent = parent;
		setOpaque(false);
		setLayout(new GridBagLayout());
		// make, add and draw the panels
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
	}
	
	/**
     * @param panel  panel to be used to be added
     * @param gridY  desired position of the panel
     * @param weight weight of the panel
     * @return The panel given, with added constraints and properties
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
        if (parent.getDayIndex() != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, parent.getDayIndex());
            parent.setCurrentDay(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
        } else {
            parent.setCurrentDay("Now");
        }

        // Make a new label and style it
        JLabel labelDay = new JLabel(parent.getCurrentDay());
        labelDay.setFont(new Font(labelDay.getFont().getName(), Font.PLAIN, 24));
        labelDay.setHorizontalAlignment(JLabel.CENTER);
        labelDay.setForeground(parent.getFontColor());
        mDayPanel.add(labelDay, BorderLayout.LINE_START);
        // label for the current location
        JLabel labelLocation = new JLabel(AppWindow.getmWeatherForecast().getLocation());
        labelLocation.setHorizontalAlignment(JLabel.CENTER);
        labelLocation.setFont(new Font(labelLocation.getFont().getName(), Font.PLAIN, 24));
        labelLocation.setForeground(parent.getFontColor());
        mDayPanel.add(labelLocation, BorderLayout.CENTER);
        // Button for going to the location screen
        JButton buttonLocation = new JButton(getCompassIcon());
        mDayPanel.add(buttonLocation, BorderLayout.LINE_END);

        // style button
        buttonLocation.setBorder(BorderFactory.createEmptyBorder());
        buttonLocation.setOpaque(false);
        buttonLocation.setContentAreaFilled(false);
        buttonLocation.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // listener for when clicked
        buttonLocation.addActionListener(e -> {
            // Location button pressed
            System.out.println("Location button clicked");
        });

    }


    /**
     * Draw the icons for the main weather display, bike coefficient and symbol
     */
    private void drawIcons() {

        WeatherHour weatherForecast = AppWindow.getmWeatherForecast().getWeather();

        // Get scaled icon to display
        ImageIcon icon = weatherForecast.getIcon(100, 100);

        // Set up the labels
        JLabel labelBikeCoefficient = new JLabel(getCyclingCoefficientIcon(getCyclingCoefficient()));
        JLabel labelWeatherIcon = new JLabel(icon);

        // Set font parameters for BikeCoefficient message
        labelBikeCoefficient.setForeground(parent.getFontColor());
        labelBikeCoefficient.setFont(new Font(labelBikeCoefficient.getFont().getName(), Font.BOLD, 16));

        // Mouse over event that displays a brief description of the cycling coefficient
        labelBikeCoefficient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelBikeCoefficient.setIcon(null);
                labelBikeCoefficient.setText(getCyclingCoefficientMessage(getCyclingCoefficient()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelBikeCoefficient.setIcon(getCyclingCoefficientIcon(getCyclingCoefficient()));
                labelBikeCoefficient.setText(null);
            }
        });

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
        if (parent.getDayIndex() == 0) {

            WeatherHour currentWeather = AppWindow.getmWeatherForecast().getWeather();

            rain = currentWeather.getRain();
            temp = currentWeather.getTemp();
            wind = currentWeather.getWind();
        } else {
            WeatherDay currentWeather = AppWindow.getmWeatherForecast().getDaySummary(parent.getDayIndex());

            rain = currentWeather.getTotalRain();
            temp = currentWeather.getAvgTemp();
            wind = currentWeather.getMaxWind();
        }

        // Make the labels
        JLabel labelRain = new JLabel(rain + " mm");
        JLabel labelTemp = new JLabel(temp + " °C");
        JLabel labelWind = new JLabel(getWindIcon(wind, 65, 25));

        // Mouse over event that displays actual wind speed
        labelWind.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelWind.setIcon(null);
                labelWind.setText(Double.toString(wind) + "mph");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelWind.setText(null);
                labelWind.setIcon(getWindIcon(wind, 65, 25));
            }
        });

        // Style the labels
        labelRain.setHorizontalAlignment(JLabel.CENTER);
        labelRain.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelRain.setForeground(parent.getFontColor());
        labelTemp.setHorizontalAlignment(JLabel.CENTER);
        labelTemp.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelTemp.setForeground(parent.getFontColor());
        labelWind.setHorizontalAlignment(JLabel.CENTER);
        labelWind.setFont(new Font(labelRain.getFont().getName(), Font.BOLD, 20));
        labelWind.setForeground(parent.getFontColor());
        // Add the labels
        mRainWindPanel.add(labelRain);
        mRainWindPanel.add(labelTemp);
        mRainWindPanel.add(labelWind);
    }


    /**
     * Detailed rows will be drawn for each hour of the current day
     */
    private void drawDetailed() {

        ArrayList<WeatherHour> weatherHour = AppWindow.getmWeatherForecast().getWeather(parent.getDayIndex());
        // maybe needed so leave as null for now
        ArrayList<WeatherHour> tomorrowWeatherHour = null;

        // current hour of the day
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        // draw the panels, conditional for whether we can fill the space with just today's weather or need to add
        // tomorrows
        for (int i = 0; i < 10; i++) {

            if (i + hour < weatherHour.size()) { // still today
                DetailedRow dr = new DetailedRow();
                JPanel panel = dr.getPanel(weatherHour.get(i + hour), parent.getFontColor());
                alternateColourPanel(panel, i);

                mDetailedPanel.add(panel);
            } else if (i + hour == weatherHour.size() && i != 9) { // enough room for tomorrow

                tomorrowWeatherHour = AppWindow.getmWeatherForecast().getWeather(1);

                JPanel panel = new JPanel(new BorderLayout());
                alternateColourPanel(panel, i);

                JLabel label = new JLabel("Tomorrow");
                label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(parent.getFontColor());
                panel.add(label);
                mDetailedPanel.add(panel);
            } else if (i + hour > weatherHour.size()) { // tomorrow
                DetailedRow dr = new DetailedRow();
                assert tomorrowWeatherHour != null;
                JPanel panel = dr.getPanel(tomorrowWeatherHour.get((i - 1 + hour) % 24), parent.getFontColor());
                alternateColourPanel(panel, i);
                mDetailedPanel.add(panel);
            }
        }
    }


    /**
     * Alternately colour panels depending on the given index
     * @param panel panel to be coloured
     * @param i index for the alternation
     */
    private void alternateColourPanel(JPanel panel, int i) {

        if (i % 2 == 0) {
            panel.setBackground(new Color(255, 255, 255, 100));
        } else {
            panel.setBackground(new Color(195, 195, 195, 100));
        }
    }


    /**
     * Get the cycling coefficient for the current weather
     *
     * @return cycling coefficient as a double
     */
    private double getCyclingCoefficient() {
        //all weather data referenced in README
        double wind = AppWindow.getmWeatherForecast().getWeather().getWind();
        double temp = AppWindow.getmWeatherForecast().getWeather().getTemp();
        double rain = AppWindow.getmWeatherForecast().getWeather().getRain();
        //standardized temperature, subtracting the mean of 12 and measuring increments per 2.5 degrees
        temp = (temp - 15) / 2.5;
        //standardized wind, subtracting the mean of 9 and measuring increments per 5mph
        wind = (wind - 9) / 5;
        if (wind < 0) wind = 0;
        //units of rain are fairly small, so scale them up to match
        rain = rain * 5;

        //calculate coefficient
        double coeff = 1 + temp * 0.1 - (Math.pow(wind, 2)) * 0.075 - rain * 0.15;
        if (coeff > 1) coeff = 1;
        if (coeff < 0) coeff = 0;

        return coeff;
    }

    /**
     * @param coeff the cycling coefficient value
     * @return the relevant description for that value
     */
    private String getCyclingCoefficientMessage(double coeff) {
        String message;
        if (coeff < 0.2) {
            message = "<html><div style='text-align: center;'>Terrible conditions for cycling, be careful!</html>";
        } else if (coeff < 0.4) {
            message = "<html><div style='text-align: center;'>Poor conditions for cycling</html>";
        } else if (coeff < 0.6) {
            message = "<html><div style='text-align: center;'>Okay conditions for cycling</html>";
        } else if (coeff < 0.8) {
            message = "<html><div style='text-align: center;'>Good conditions for cycling</html>";
        } else {
            message = "<html><div style='text-align: center;'>Great conditions for cycling, have fun!</html>";
        }
        return message;
    }


    /**
     * Get the corresponding image for the coefficient value given and scale it
     * @param coeff value to use to select image
     * @return icon of cycling coefficient
     */
    private ImageIcon getCyclingCoefficientIcon(double coeff) {
        //begin constructing filepath for icon
        String filepath = "art/use_these/bike_coeffs_bg/";
        //access appropriate bike icon depending on given coefficient
        if (coeff < 0.2) {
            filepath += "bike1xx.png";
        } else if (coeff < 0.4) {
            filepath += "bike2xx.png";
        } else if (coeff < 0.6) {
            filepath += "bike3xx.png";
        } else if (coeff < 0.8) {
            filepath += "bike4xx.png";
        } else {
            filepath += "bike5xx.png";
        }

        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(100, 75, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }


    /**
     * Get the image for the compass and scale it to fit
     * @return image icon for the compass
     */
    private ImageIcon getCompassIcon() {
        //construct filepath for the icon
        String filepath = "art/use_these/other_icons/compass.png";

        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }
    
    /**
     * Returns the wind icon scaled to the desired values and the icon depends on the wind value
     *
     * @param wind   current wind value
     * @param width  desired width of the icon
     * @param height desired height of the icon
     * @return ImageIcon of wind of desired size and strength
     */
    public static ImageIcon getWindIcon(double wind, int width, int height) {

        //begin constructing filepath for icon
        String filepath = "art/use_these/wind_icons/";
        //access appropriate wind icon depending on wind strength
        if (wind < 6) {
            filepath += "wind0.png";
        } else if (wind < 8) {
            filepath += "wind1.png";
        } else if (wind < 10) {
            filepath += "wind2.png";
        } else {
            filepath += "wind3.png";
        }
        // get, scale and return the icon
        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        return icon;
    }
}