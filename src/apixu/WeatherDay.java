package apixu;

import javax.swing.*;
import java.awt.*;

public class WeatherDay {

    //Current day of the week, expressed in full
    private String mDay;

    //Max wind speed in miles per hour
    private double mMaxWind;

    //Mean temperature in degrees Celsius
    private double mAvgTemp;

    //Max temperature in degrees Celsius
    private double mMaxTemp;

    //Min temperature in degrees Celsius
    private double mMinTemp;

    //Total rainfall in millimetres
    private double mTotalRain;

    //Average weather icon
    private ImageIcon mIcon;

    //getters and setters for all members

    public WeatherDay(String day, double wind, double avgT, double maxT, double minT, double rain) {
        this.mDay = day;
        this.mMaxWind = wind;
        this.mAvgTemp = avgT;
        this.mMaxTemp = maxT;
        this.mMinTemp = minT;
        this.mTotalRain = rain;
        //TODO choose image based on day weather
        ImageIcon icon = new ImageIcon("art/use_these/weather_icons/day/cloudysunny.png"); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        this.mIcon = icon;
    }

    public String getDay() {
        return mDay;
    }

    public double getMaxWind() {
        return mMaxWind;
    }

    public double getAvgTemp() {
        return mAvgTemp;
    }

    public double getMaxTemp() {
        return mMaxTemp;
    }

    public double getMinTemp() {
        return mMinTemp;
    }

    public double getTotalRain() {
        return mTotalRain;
    }

    public ImageIcon getIcon() {
        return mIcon;
    }
}
