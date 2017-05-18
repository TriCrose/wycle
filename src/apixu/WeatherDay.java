package apixu;

public class WeatherDay {

    //Current day
    private String mDay;

    //Max wind speed in mph
    private double mMaxWind;

    //Mean temperature in degrees Celsius
    private double mAvgTemp;

    //Max temperature in degrees Celsius
    private double mMaxTemp;

    //Min temperature in degrees Celsius
    private double mMinTemp;

    //Total rainfall in mm
    private double mTotalRain;

    //getters and setters for all members

    public String getmDay() {
        return mDay;
    }
    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public double getmMaxWind() {
        return mMaxWind;
    }
    public void setmMaxWind(double mWind) {
        this.mMaxWind = mWind;
    }

    public double getmAvgTemp() {
        return mAvgTemp;
    }
    public void setmAvgTemp(double mAvgTemp) {
        this.mAvgTemp = mAvgTemp;
    }

    public double getmMaxTemp() {
        return mMaxTemp;
    }
    public void setmMaxTemp(double mMaxTemp) {
        this.mMaxTemp = mMaxTemp;
    }

    public double getmMinTemp() {
        return mMinTemp;
    }
    public void setmMinTemp(double mMinTemp) {
        this.mMinTemp = mMinTemp;
    }

    public double getmTotalRain() {
        return mTotalRain;
    }
    public void setmTotalRain(double mTotalRain) {
        this.mTotalRain = mTotalRain;
    }
}
