package app;

import apixu.WeatherForecast;
import ui.AppParams;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Main class which holds the home screen, this includes: day; icons; rain and wind; detailed view;
 */
public class AppWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    // store the weather forecast to reduce api calls
    private static WeatherForecast mWeatherForecast = new WeatherForecast();
    // index to keep track of which day we are displaying
    private int mDayIndex = 0; // change when switching days
    // current day to display
    private String mCurrentDay;
    private Color fontColor;

    public AppWindow() {
        super("Wycle");

        // set the background image depending on the time of day
        setBackgroundImage();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(AppParams.WIDTH, AppParams.HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());

        // Set font colour depending on day or night
        if (mWeatherForecast.getWeather().getIsDay() == 1) fontColor = Color.black;
        else fontColor = Color.white;

        setVisible(true);
    }

    /**
     * @return WeatherForecast object containing the current weather and forecast
     */
    public static WeatherForecast getmWeatherForecast() {
        return mWeatherForecast;
    }
    
    /**
     * Get the appropriate background image and then display it
     */
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
    
    public static void main(String args[]) {
        new AppWindow();
    }
}