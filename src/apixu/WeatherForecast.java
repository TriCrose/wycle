package apixu;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;
import com.weatherlibrary.datamodel.Day;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.Repository;
import com.weatherlibraryjava.RequestBlocks;
import com.weathertest.MainWeather;
import location.LocationObject;

import java.util.ArrayList;


public class WeatherForecast {

    //Our personal API key for making requests
    private String key = "eb2a0633229b456ba6093557151106";

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
            mWeatherModel = repo.GetWeatherDataByLatLong(key, location.getLat(), location.getLng(), RequestBlocks.Days.Six);
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

    /**
     * Get hourly weather data for a specified day
     *
     * @param day indicates the number of days from today that the user wishes
     *            to retrieve, 0 is today, 1 is tomorrow etc.
     * @return list of 24 WeatherRow objects, each containing wind, rain, temperature
     *         and condition for every hour of the requested day
     */
    public ArrayList<WeatherRow> getWeather(int day) {
        ArrayList<WeatherRow> table = new ArrayList<>();
        for (int i = 0; i<24; i++) {
            WeatherRow row = new WeatherRow();
            String time = "";
            if (i < 10) time = "0";
            time += Integer.toString(i) + ":00";

            row.setmTime(time);
            row.setmWind(mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getWindMph());
            row.setmTemp(mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getTempC());
            row.setmRain(mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getPrecipMm());
            row.setmCondition(mWeatherModel.forecast.forecastday.get(day).getHour().get(i).getCondition());
            table.add(row);
        }
        return table;
    }

    /**
     * Get weather data for the current time
     *
     * @return a single WeatherRow object, containing the temp, wind, rain and condition
     *         for the current time
     */
    public WeatherRow getWeather() {
        WeatherRow row = new WeatherRow();
        row.setmCondition(mWeatherModel.current.getCondition());
        row.setmRain(mWeatherModel.current.getPrecipMm());
        row.setmTemp(mWeatherModel.current.getTempC());
        row.setmWind(mWeatherModel.current.getWindMph());
        return row;
    }

}
