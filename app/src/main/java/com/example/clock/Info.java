package com.example.clock;

import java.util.Calendar;
import java.util.Map;

public class Info {

	private static Map<String, Map<String, String>> weather;
	
	// Gets year
	public static String getYear(){
		String date = Calendar.getInstance().getTime().toString();
		String year = date.substring(date.length()-4);
		return year;
	}
	
	// Gets day of the week
	public static String getDayOfWeek(){
		String date = Calendar.getInstance().getTime().toString();
		String day = "";
		for(int i = 0; date.charAt(i) != ' '; i++)
			day += date.charAt(i);
		return day;
	}
	
	// Get month
	public static String getMonth(){
		String date = Calendar.getInstance().getTime().toString();
		String month = "";
		int spaces = 0;
		for(int i = 0; i < date.length(); i++)
			if(date.charAt(i) == ' ')
				spaces++;
			else if(spaces == 1)
				month += date.charAt(i);
		return month;
	}
	
	// Gets day
	public static int getDay(){
		String date = Calendar.getInstance().getTime().toString();
		String day = "";
		int spaces = 0;
		for(int i = 0; i < date.length(); i++)
			if(date.charAt(i) == ' ')
				spaces++;
			else if(spaces == 2)
				day += date.charAt(i);
		return Integer.parseInt(day);
	}
	
	// Get full time
	public static String getTime(){
		String time = "";
		
		if(getHour() > 11 && getHour() < 24)
			time += " PM";
		else
			time += " AM";
		
		if(getMin() > 9)
			time = getMin()+time;
		else
			time = "0"+getMin()+time;
		
		if(getHour() == 0)
			time = "12:"+getMin()+":";
		else if(getHour() > 12)
			time = (getHour()-12)+":"+time;
		else
			time = getHour()+":"+time;
		
		return time;
	}
	
	// Get hour
	public static int getHour(){
		String date = Calendar.getInstance().getTime().toString();
		String hour = "";
		int spaces = 0;
		for(int i = 0; i < date.length(); i++)
			if(date.charAt(i) == ' ' || date.charAt(i) == ':')
				spaces++;
			else if(spaces == 3)
				hour += date.charAt(i);
		return Integer.parseInt(hour);
	}
	
	// Get minutes
	public static int getMin(){
		String date = Calendar.getInstance().getTime().toString();
		String min = "";
		int spaces = 0;
		for(int i = 0; i < date.length(); i++)
			if(date.charAt(i) == ' ' || date.charAt(i) == ':')
				spaces++;
			else if(spaces == 4)
				min += date.charAt(i);
		return Integer.parseInt(min);
	}
	
	// Get seconds
	public static int getSec(){
		String date = Calendar.getInstance().getTime().toString();
		String sec = "";
		int spaces = 0;
		for(int i = 0; i < date.length(); i++)
			if(date.charAt(i) == ' ' || date.charAt(i) == ':')
				spaces++;
			else if(spaces == 5)
				sec += date.charAt(i);
		return Integer.parseInt(sec);
	}
	
	// Get time in seconds
	public static int getTimeInSeconds(){
		return (int) (getHour()*3600.0+getMin()*60.0+getSec());
	}
	
	// Get percent of day
	public static double getPercent(){
		double time = getHour()*3600.0+getMin()*60.0+getSec();
		return time/86400.0;
	}
	
	// Update Weather Info
	public static void updateWeatherInfo(Map<String, Map<String, String>>newWeatherInfo){
		weather = newWeatherInfo;
	}
	
	// Get Weather Info
	public static String getWeatherInfo(String type, String info){
		if(weather == null || weather.get(type) == null)
			return "Loading...";
		
		String requestedInfo = weather.get(type).get(info);
		if(requestedInfo == null || requestedInfo.length() == 0)
			return "Loading";
		
		return requestedInfo;
	}
	
	// Get all weather info
	public static Map<String, Map<String, String>> getWeather(){
		return weather;
	}
	
	// Get predicted weather at time in seconds
	public static String predictWeatherAt(String info, int time, boolean isString){
		if(weather == null)// || weather.get("hour_1_weather") == null)
			return "Loading...";
		
		if(weather.get("hour_1_weather") == null)
			return "Loading.";
		
		String requestedInfo = weather.get("hour_"+(weather.size()-3)+"_weather").get(info);
		if(requestedInfo == null || requestedInfo.length() == 0 || requestedInfo.startsWith("Loading"))
			return "Loading";
		
		if(isString)
			return WeatherPredictor.stringAt(info, time, weather.size()-3);
		return ""+WeatherPredictor.intAt(info, time, weather.size()-3);
	}
	
	// Get time as int
	public static long getTotalTime(){
		return Calendar.getInstance().getTimeInMillis();
	}
}
