package apixu;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import com.weatherlibrary.datamodel.Day;
import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.Repository;
import com.weatherlibraryjava.RequestBlocks;
import com.weathertest.MainWeather;
import location.LocationObject;

/**
 * A class to obtain weather data for a specified time and location
 */
public class WeatherForecast {

    //Our personal API key for making requests
    String key = "eb2a0633229b456ba6093557151106";

    //Weather model contains current and forecasted weather for a given location
    WeatherModel mWeatherModel;

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

}
