package com.example.clock;

import java.util.HashMap;
import java.util.Map;

public class TextFormat {
	private static String data;
	
	public static Map<String, Map<String, String>> threeHour(String text){
		data = "";
		
		// Remove notes
		if(text.contains("#")){
			for(int i = text.length()-1; text.charAt(i) != '#'; i--)
				data = text.charAt(i) + data;
			data = data.substring(1);
		}
		
		// Current weather
		Map<String, String> currentWeather = new HashMap<String, String>();
		currentWeather.put("observationTime", getNext());
		currentWeather.put("tempC", getNext());
		currentWeather.put("tempF", getNext());
		currentWeather.put("weatherCode", getNext());
		currentWeather.put("weatherIconUrl", getNext());
		currentWeather.put("weatherDesc", getNext());
		currentWeather.put("windspeedMiles", getNext());		
		currentWeather.put("windspeedKmph", getNext());		
		currentWeather.put("winddirDegree", getNext());		
		currentWeather.put("winddir16Point", getNext());		
		currentWeather.put("precipMM", getNext());		
		currentWeather.put("humidity", getNext());		
		currentWeather.put("visibilityKm", getNext());		
		currentWeather.put("pressureMB", getNext());		
		currentWeather.put("cloudcover", getNext());
		
		// Current weather based on desc
		// THIS WILL FIX ERROR
		
		// Day weather
		Map<String, String> dayWeather = new HashMap<String, String>();
		dayWeather.put("date", getNext());
		dayWeather.put("maxtempC", getNext());
		dayWeather.put("maxtempF", getNext());
		dayWeather.put("minTempC", getNext());
		dayWeather.put("minTempF", getNext());
		dayWeather.put("sunrise", getNext());
		dayWeather.put("sunset", getNext());
		dayWeather.put("moonrise", getNext());
		dayWeather.put("moonset", getNext());
		
		// Full map
		Map<String, Map<String, String>> weather = new HashMap<String, Map<String, String>>();
		weather.put("current_weather", currentWeather);
		weather.put("day_1_weather", dayWeather);
		for(int i = 1; i <= 8; i++)
			weather.put("hour_"+i+"_weather", hourWeather());
		
		// Day weather
		dayWeather = new HashMap<String, String>();
		dayWeather.put("date", getNext());
		dayWeather.put("maxtempC", getNext());
		dayWeather.put("maxtempF", getNext());
		dayWeather.put("minTempC", getNext());
		dayWeather.put("minTempF", getNext());
		dayWeather.put("sunrise", getNext());
		dayWeather.put("sunset", getNext());
		dayWeather.put("moonrise", getNext());
		dayWeather.put("moonset", getNext());
		
		weather.put("day_2_weather", dayWeather);
		
		for(int i = 9; i <= 16; i++)
			weather.put("hour_"+i+"_weather", hourWeather());
		
		return weather;
	}
	
	private static Map<String, String> hourWeather(){
		// 3 hour weather
		Map<String, String> hourlyWeather = new HashMap<String, String>();
		hourlyWeather.put("date", getNext());
		hourlyWeather.put("time", getNext());
		hourlyWeather.put("tempC", getNext());
		hourlyWeather.put("tempF", getNext());
		hourlyWeather.put("windspeedMiles", getNext());
		hourlyWeather.put("windspeedKmph", getNext());
		hourlyWeather.put("winddirdegree", getNext());
		hourlyWeather.put("winddir16point", getNext());
		hourlyWeather.put("weatherCode", getNext());
		hourlyWeather.put("weatherIconUrl", getNext());
		hourlyWeather.put("weatherDesc", getNext());
		hourlyWeather.put("precipMM", getNext());
		hourlyWeather.put("humidity", getNext());
		hourlyWeather.put("visibilityKm", getNext());
		hourlyWeather.put("pressureMB", getNext());
		hourlyWeather.put("cloudcover", getNext());
		hourlyWeather.put("HeatIndexC", getNext());
		hourlyWeather.put("HeatIndexF", getNext());
		hourlyWeather.put("DewPointC", getNext());
		hourlyWeather.put("DewPointF", getNext());
		hourlyWeather.put("WindChillC", getNext());
		hourlyWeather.put("WindChillF", getNext());
		hourlyWeather.put("WindGustMiles", getNext());
		hourlyWeather.put("WindGustKmph", getNext());
		hourlyWeather.put("FeelsLikeC", getNext());
		hourlyWeather.put("FeelsLikeF", getNext());
		hourlyWeather.put("chanceofrain", getNext());
		hourlyWeather.put("chanceofremdry", getNext());
		hourlyWeather.put("chanceofwindy", getNext());
		hourlyWeather.put("chanceofovercast", getNext());
		hourlyWeather.put("chanceofsunshine", getNext());
		hourlyWeather.put("chanceoffrost", getNext());
		hourlyWeather.put("chanceofhightemp", getNext());
		hourlyWeather.put("chanceoffog", getNext());
		hourlyWeather.put("chanceofsnow", getNext());
		hourlyWeather.put("chanceofthunder", getNext());
		
		return hourlyWeather;
	}
	
	private static String getNext(){
		String next = "";
		while(data.length() != 0 && data.charAt(0) != '\t' ){
			next += data.charAt(0);
			data = data.substring(1);
		}
		if(data.length() != 0)
			data = data.substring(1);

		return next;
	}
}