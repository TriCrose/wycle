package apixu;

import com.weatherlibrary.datamodel.Condition;

public class WeatherHour {

    //24 hour time, as a string
    private String mTime;

    //Temperature in degrees Celsius
    private double mTemp;

    //Wind speed in mph
    private double mWind;

    //Precipitation in mm
    private double mRain;

    //Class contains one word description of weather conditions and weather icon
    private Condition mCondition;

    //getters and setters for all members

    public WeatherHour(String time, double temp, double wind, double rain, Condition cond) {
        this.mCondition = cond;
        this.mTime = time;
        this.mWind = wind;
        this.mRain = rain;
        this.mTemp = temp;
    }

    public String getmTime() {
        return mTime;
    }

    public double getmTemp() {
        return mTemp;
    }

    public double getmWind() {
        return mWind;
    }

    public double getmRain() {
        return mRain;
    }

    public Condition getmCondition() {
        return mCondition;
    }
}

