package com.example.clock;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Paint;

public class WeatherDisplay {
	
	private static ArrayList<Cloud> clouds;
	private static Random r;
	private static String weatherType;
	
	public static void init(){
		clouds = new ArrayList<Cloud>();
		r = new Random();
		weatherType = getWeatherType();
	}
	
	public static void draw(Canvas c, Paint p){
		
		weatherType = getWeatherType();
		if(weatherType.equals("clouds"))
			drawClouds(c, p);
		else if(weatherType.equals("rain cloud") || weatherType.equals("raining") || weatherType.equals("thunder"))
			drawRainClouds(c, p);
		else if(weatherType.equals("snowing"))
			drawSnowing(c, p);
			

	}
	
	private static void drawSnowing(Canvas c, Paint p){
		
	}
	
	private static void drawRainClouds(Canvas c, Paint p){
		
	}
	
	private static void drawClouds(Canvas c, Paint p){
		// Add clouds, speed up clouds if too many
		double numOfClouds = Integer.parseInt(Info.predictWeatherAt("cloudcover", Sun.timeBasedOnLocation(), false))/10+.5;
		if(clouds.size() < numOfClouds)
//			if(r.nextInt(100)/10+.5 < numOfClouds)
//				clouds.add(new Cloud(-1, 525+r.nextInt(100), 0, c.getWidth()));
//			else 
			if(r.nextBoolean())
				clouds.add(new Cloud(-1, 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
			else
				clouds.add(new Cloud(c.getWidth(), 525+r.nextInt(100), r.nextInt(5)+1, c.getWidth()));
		else
			for(int i = 0; i < clouds.size()-numOfClouds; i++)
				clouds.get(i).speedUp();
		
		// Draw move and remove clouds
		for(int i = 0; i < clouds.size(); i++){
			clouds.get(i).draw(c, p);
			if(clouds.get(i).move()){
				clouds.remove(i);
				i--;
			}
		}
	}
	
	private static String getWeatherType(){
//		if(Integer.parseInt(Info.predictWeatherAt("chanceofthunder", Sun.timeBasedOnLocation(), false)) >= 50)
//			return "thunder";
//		if(Integer.parseInt(Info.predictWeatherAt("chanceofrain", Sun.timeBasedOnLocation(), false)) >= 50)
//			return "raining";
//		if(Integer.parseInt(Info.predictWeatherAt("chanceofrain", Sun.timeBasedOnLocation(), false)) >= 25)
//			return "rain cloud";
//		if(Integer.parseInt(Info.predictWeatherAt("chanceofsnow", Sun.timeBasedOnLocation(), false)) >= 50)
//			return "snowing";
		return "clouds";
	}

}
