package apixu;

import com.weatherlibrary.datamodel.Condition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WeatherHour {

    //24 hour time, as a string
    private String mTime;

    //Temperature in degrees Celsius
    private double mTemp;

    //Wind speed in mph
    private double mWind;

    //Precipitation in mm
    private double mRain;

    //Class contains one word description of weather conditions
    private Condition mCondition;

    //Weather icon
    private ImageIcon mIcon;

    //Is the hour during the day 1=true, 0=false
    private int mIsDay;

    //getters and setters for all members

    public WeatherHour(String time, double temp, double wind, double rain, Condition cond, int isDay) {
        this.mCondition = cond;
        try {
            String filepath = "art/use_these/weather_icons/";
            if (isDay == 1) {
                filepath += "day/";
            } else {
                filepath += "night/night_";
            }
            if (cond.getText().contains("rain")) {
                filepath += "rainy.png";
            }
            else if (cond.getText().contains("Sunny") || cond.getText().contains("Clear")) {
                filepath += "sunny.png";
            }
            else if (( cond.getText().contains("Cloudy")
                    || cond.getText().contains("Overcast"))
                    || cond.getText().contains("cloudy")
                    || cond.getText().contains("Mist")) {
                filepath += "cloudy.png";
            }
            ImageIcon icon = new ImageIcon(filepath);
            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newimg);  // transform it back
            this.mIcon = icon;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mTime = time;
        this.mWind = wind;
        this.mRain = rain;
        this.mTemp = temp;
        this.mIsDay = isDay;
    }

    public String getTime() {
        return mTime;
    }

    public double getTemp() {
        return mTemp;
    }

    public double getWind() {
        return mWind;
    }

    public double getRain() {
        return mRain;
    }

    public Condition getCondition() {
        return mCondition;
    }

    public int getIsDay() {
        return mIsDay;
    }

    public ImageIcon getIcon() {
        return mIcon;
    }
}

