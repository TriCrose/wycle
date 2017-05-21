package apixu;

import com.weatherlibrary.datamodel.Condition;

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

    //Class contains one word description of weather conditions
    private Condition mCondition;

    //Weather icon
    private ImageIcon mIcon;

    //getters and setters for all members


    public WeatherDay(String day, double wind, double avgT, double maxT, double minT, double rain, Condition cond) {

        this.mCondition = cond;
        this.mDay = day;
        this.mMaxWind = wind;
        this.mAvgTemp = avgT;
        this.mMaxTemp = maxT;
        this.mMinTemp = minT;
        this.mTotalRain = rain;
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


    public Condition getCondition() {

        return mCondition;
    }


    public ImageIcon getIcon(int width, int height) {
        //begin constructing filepath for icon
        String filepath = "art/use_these/weather_icons/";
        //access day folder
        filepath += "day/";
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


    public ImageIcon getIcon() {

        return getIcon(50, 50);
    }
}
