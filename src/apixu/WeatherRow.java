package apixu;

import com.weatherlibrary.datamodel.Condition;

public class WeatherRow {

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

    public String getmTime() {
        return mTime;
    }
    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public double getmTemp() {
        return mTemp;
    }
    public void setmTemp(double mTemp) {
        this.mTemp = mTemp;
    }

    public double getmWind() {
        return mWind;
    }
    public void setmWind(double mWind) {
        this.mWind = mWind;
    }

    public double getmRain() {
        return mRain;
    }
    public void setmRain(double mRain) {
        this.mRain = mRain;
    }

    public Condition getmCondition() {
        return mCondition;
    }
    public void setmCondition(Condition mCondition) {
        this.mCondition = mCondition;
    }
}

