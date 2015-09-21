package com.example.clock;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

public class G {
	static String url;
	static final String weatherApiKey = "1b36fb7e6935a31178c1eae982d57";
	static double scale;
	static Context context;
	static long weatherUpdateTime;
	static double lat, lon;
	
	// Saves string under DATA_TAG
	public static void save(String DATA_TAG, String stringToSave){
		SharedPreferences mSettings = context.getSharedPreferences("Motivational Alarm Data", 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(DATA_TAG, stringToSave);
		editor.commit();
	}
	
	// Gets string under DATA_TAG
	public static String get(String DATA_TAG, String defValue){
		SharedPreferences mSettings = context.getSharedPreferences("Motivational Alarm Data", 0);
		return mSettings.getString(DATA_TAG, defValue);
	}
	
	// Gets int under DATA_TAG
	public static int get(String DATA_TAG, int defValue){
		SharedPreferences mSettings = context.getSharedPreferences("Motivational Alarm Data", 0);
		return Integer.parseInt(mSettings.getString(DATA_TAG, ""+defValue));
	}
	
	// Gets double under DATA_TAG
	public static double get(String DATA_TAG, double defValue){
		SharedPreferences mSettings = context.getSharedPreferences("Motivational Alarm Data", 0);
		return Double.parseDouble(mSettings.getString(DATA_TAG, ""+defValue));
	}
	
	// Get alarms
	public static ArrayList<Alarm> getAlarms(){
		String[] info = get("alarmInfo", "").split(" ");
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		for(int i = 0; i < info.length; i++){
			if(info[i].length() != 0){
				String[] alarmInfo = info[i].split(":");
				System.out.println(info[i]);
				Alarm alarm = new Alarm(Integer.parseInt(alarmInfo[1]), Integer.parseInt(alarmInfo[2]), Integer.parseInt(alarmInfo[3]), Integer.parseInt(alarmInfo[4]));
				alarm.setTime(Long.parseLong(alarmInfo[0]));
				alarm.setBasedOnAlarmTime();
				alarms.add(alarm);
			}
		}
		
		return alarms;		
	}
	
	// Save alarms
	public static void saveAlarms(ArrayList<Alarm> alarms){
		if(alarms.size() != 0){
			String alarmInfo = "";
			for(int i = 0; i < alarms.size(); i++){
				alarmInfo += alarms.get(i).totalTimeBasedOnLocation() + ":" +
						alarms.get(i).getRadius() + ":" +
						alarms.get(i).getGlobeRadius() + ":" +
						alarms.get(i).getWidth() + ":" +
						alarms.get(i).getHeight() + " ";
			}
			alarmInfo = alarmInfo.substring(0, alarmInfo.length()-1);
			save("alarmInfo", alarmInfo);
		}
		else
			save("alarmInfo", "");
	}
	
	// Set lat lon
	public static void updateLatLon(){
		Location loc = getLastKnownLocation();
		if(loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			save("latitude", ""+lat);
			save("longitude", ""+lon);
		}
		else{
			lat = get("latitude", 42.3619881);
			lon = get("longitude", -71.3592644);
		}
	}
	
	// Returns last known location
	private static Location getLastKnownLocation(){
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);
		Location bestLocation = null;
		for(String provider : providers){
			Location loc = lm.getLastKnownLocation(provider);
			if(loc == null)
				continue;
			if(bestLocation == null || loc.getAccuracy() < bestLocation.getAccuracy())
				bestLocation = loc;
		}
		return bestLocation;
	}
	
	// Returns latitude
	public static double getLat(){
		return lat;
	}
	
	// Returns longitude
	public static  double getLon(){
		return lon;
	}
	
	// Gets weather info
	public static void getWeatherUpdates()
	{
		url = "http://api.worldweatheronline.com/free/v2/weather.ashx?q="+lat+"%2C"+lon+"&format=tab&num_of_days=2&key="+weatherApiKey;
		weatherUpdateTime = Info.getTotalTime();
		save("weatherupdatetime", ""+Info.getTotalTime());
		try {
			new Communicator().execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
