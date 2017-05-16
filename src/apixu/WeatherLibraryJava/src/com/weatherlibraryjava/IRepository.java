package com.weatherlibraryjava;

import com.weatherlibrary.datamodel.WeatherModel;

public interface IRepository {
	
	WeatherModel GetWeatherData(String key, RequestBlocks.GetBy getBy, String value, RequestBlocks.Days ForecastOfDays) throws Exception;
	WeatherModel GetWeatherDataByLatLong( String key, String latitude, String longitude, RequestBlocks.Days ForecastOfDays) throws Exception;
	WeatherModel GetWeatherDataByAutoIP(String key, RequestBlocks.Days ForecastOfDays) throws Exception;

    WeatherModel GetWeatherData(String key, RequestBlocks.GetBy getBy, String value) throws Exception;
    WeatherModel GetWeatherDataByLatLong( String key, String latitude, String longitude) throws Exception;
    WeatherModel GetWeatherDataByAutoIP(String key) throws Exception;

}
