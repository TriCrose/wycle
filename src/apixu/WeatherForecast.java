package apixu;

import com.weatherlibrary.datamodel.Condition;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.Repository;
import com.weatherlibraryjava.RequestBlocks;
import location.LocationObject;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {

    //Our personal API key for making requests
    private String key = "bdc5142f54be4c62b03132022171605";

    //Weather model contains current and forecasted weather for a given location
    private WeatherModel mWeatherModel;


    /**
     * @param location uses the latitude and longitude from the location object
     *                 to set the location for the weather request a create the
     *                 Weather Model
     */
    public WeatherForecast(LocationObject location) {

        try {
            Repository repo = new Repository();
            WeatherModel now = repo.GetWeatherDataByLatLong(key, location.getLat(), location.getLng());
            mWeatherModel = repo
                    .GetWeatherDataByLatLong(key, location.getLat(), location.getLng(), RequestBlocks.Days.Six);
            mWeatherModel.current = now.current;
        } catch (Exception e) {
            //TODO handle real exceptions
            e.printStackTrace();
        }
    }


    /**
     * Overloaded constructor method, not specifying a location will derive
     * location from your IP address and find weather for that location
     */
    public WeatherForecast() {

        try {
            Repository repo = new Repository();
            WeatherModel now = repo.GetWeatherDataByAutoIP(key);
            mWeatherModel = repo.GetWeatherDataByAutoIP(key, RequestBlocks.Days.Six);
            mWeatherModel.current = now.current;
        } catch (Exception e) {
            //TODO handle real exceptions
            e.printStackTrace();
        }
    }


    public WeatherForecast(LocationObject location, boolean getForecast) {

        try {
            Repository repo = new Repository();
            if (getForecast) {
                mWeatherModel = repo
                        .GetWeatherDataByLatLong(key, location.getLat(), location.getLng(), RequestBlocks.Days.Six);
            } else {
                WeatherModel now = repo.GetWeatherDataByLatLong(key, location.getLat(), location.getLng());
                mWeatherModel = now;
                mWeatherModel.current = now.current;
            }
        } catch (Exception e) {
            //TODO handle real exceptions
            e.printStackTrace();
        }
    }


    public WeatherForecast(boolean getForecast) {

        try {
            Repository repo = new Repository();
            if (getForecast) {
                mWeatherModel = repo.GetWeatherDataByAutoIP(key, RequestBlocks.Days.Six);
            } else {
                WeatherModel now = repo.GetWeatherDataByAutoIP(key);
                mWeatherModel = now;
                mWeatherModel.current = now.current;
            }
        } catch (Exception e) {
            //TODO handle real exceptions
            e.printStackTrace();
        }
    }


    /**
     * Get hourly weather data for a specified day
     *
     * @param day the day 'x' days after today that you want to retrieve, 0 is today,
     *            1 is tomorrow etc.
     * @return list of 24 WeatherHour objects, each containing wind, rain, temperature
     * and condition for every hour of the requested day
     */
    public ArrayList<WeatherHour> getWeather(int day) {

        ArrayList<WeatherHour> table = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            String time = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getTime();
            //getTime returns yyyy-mm-dd hh:mm format, so take last 5 characters
            time = time.substring(time.length() - 5);
            double wind = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getWindMph();
            double temp = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getTempC();
            double rain = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getPrecipMm();
            Condition cond = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getCondition();
            int isDay = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getIsDay();
            WeatherHour wHour = new WeatherHour(time, temp, wind, rain, cond, isDay);
            table.add(wHour);
        }

        return table;
    }


    /**
     * Overloaded method, not specifying a day will retrieve weather data for the current time
     * Returns a single set of data i.e. for the current hour
     *
     * @return a single WeatherHour object, containing the temp, wind, rain and condition
     * for the current hour
     */
    public WeatherHour getWeather() {

        Condition cond = mWeatherModel.current.getCondition();
        double rain = mWeatherModel.current.getPrecipMm();
        double temp = mWeatherModel.current.getTempC();
        double wind = mWeatherModel.current.getWindMph();
        int isDay = mWeatherModel.current.getIsDay();
        return new WeatherHour("now", temp, wind, rain, cond, isDay);
    }


    /**
     * Takes a day and returns a weather summary for that day, containing min/max/avg temp,
     * max wind speed, and total rainfall
     *
     * @param day the day 'x' days after today that you want to retrieve,
     *            0 is today, 1 is tomorrow etc.
     * @return WeatherDay object that contains all the weather data
     */
    public WeatherDay getDaySummary(int day) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, day);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayName = sdf.format(c.getTime());
        double avgT = mWeatherModel.forecast.forecastday.get(day).getDay().avgtemp_c;
        double maxT = mWeatherModel.forecast.forecastday.get(day).getDay().maxtemp_c;
        double minT = mWeatherModel.forecast.forecastday.get(day).getDay().mintemp_c;
        double wind = mWeatherModel.forecast.forecastday.get(day).getDay().maxwind_mph;
        double rain = mWeatherModel.forecast.forecastday.get(day).getDay().totalprecip_mm;

        //find most common condition from 7am onwards
        Map<Condition, Integer> conditionCount = new HashMap<>();
        for (int i = 6; i<24; i++) {
            Condition cond = mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getCondition();
            if (!(conditionCount.containsKey(cond))) {
                conditionCount.put(cond, 0);
            }
            conditionCount.put(cond, conditionCount.get(cond) + 1);
        }
        Condition cond = null;
        for (Condition key : conditionCount.keySet()) {
            if (cond == null || conditionCount.get(key).compareTo(conditionCount.get(cond)) > 0) {
                cond = key;
            }
        }

        return new WeatherDay(dayName, wind, avgT, maxT, minT, rain, cond);
    }


    /**
     * Returns the current location
     *
     * @return String containing the location used by the current model
     */
    public String getLocation() {

        return mWeatherModel.getLocation().getName();
    }
}
