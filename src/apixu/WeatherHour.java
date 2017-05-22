package apixu;

import com.weatherlibrary.datamodel.Condition;

import javax.swing.*;
import java.awt.*;

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


    /**
     * Getter for the hour's icon. The icon used is determine by some parsing
     * of the weather condition and whether or not it is day
     *
     * @param width specify the width of the icon to be returned
     * @param height specify the height of the icon to be returned
     * @return appropriate weather icon of requested width and height
     */
    public ImageIcon getIcon(int width, int height) {
        //begin constructing filepath for icon
        String filepath = "art/use_these/weather_icons/";
        //access day folder or night folder depending on isDay
        if (mIsDay == 1) {
            filepath += "day/";
        } else {
            filepath += "night/night_";
        }
        //search the condition text for key words to select the icon
        if (mCondition.getText().contains("rain")) {
            if (mCondition.getText().contains("Patchy") || mCondition.getText().contains("Light")) {
                filepath += "cloudysunny.png";
            } else {
                filepath += "rainy.png";
            }
        } else if (mCondition.getText().contains("Sunny") || mCondition.getText().contains("Clear")) {
            filepath += "sunny.png";
        } else if ((mCondition.getText().contains("Cloudy") || mCondition.getText().contains("Overcast")) || mCondition
                .getText().contains("cloudy") || mCondition.getText().contains("Mist")) {
            filepath += "cloudy.png";
        }
        ImageIcon icon = new ImageIcon(filepath); //convert png to ImageIcon
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        this.mIcon = icon;
        return mIcon;
    }


    /**
     * Overloaded getter, not specifying a width and height returns
     * a default size of 50x50
     *
     * @return appropriate weather icon 50x50 size
     */
    public ImageIcon getIcon() {

        return getIcon(50, 50);
    }
}

