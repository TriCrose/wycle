package com.weathertest;

import com.weatherlibrary.datamodel.WeatherModel;
import com.weatherlibraryjava.IRepository;
import com.weatherlibraryjava.Repository;

public class MainWeather {
	
	 String key = "bdc5142f54be4c62b03132022171605";

	public static void main(String[] args) {
		
		MainWeather obj = new MainWeather();
		
		IRepository repo = new Repository();
		
		try {
			WeatherModel weatherModel = repo.GetWeatherDataByAutoIP(obj.key);
			
			System.out.println("WeatherTestJava : location name==============>"+weatherModel.location.name);
			System.out.println("WeatherTestJava : Temp==============>"+weatherModel.current.temp_c);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
